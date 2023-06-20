package com.bookstore.payment.command.services;

import com.bookstore.coreapis.command.ProcessPaymentCommand;
import com.bookstore.coreapis.common.StoreToken;
import com.bookstore.coreapis.enumaration.PaymentState;
import com.bookstore.payment.command.model.OrderDTO;
import com.bookstore.payment.command.model.PayPalAppContextDTO;
import com.bookstore.payment.configuration.PaypalConfig;
import com.bookstore.payment.entities.Payment;
import com.bookstore.payment.entities.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServices implements IPaymentServices{
    private final PaypalConfig paypalConfig;
    private final ObjectMapper objectMapper;
    private final String BASE_URL = "https://api-m.sandbox.paypal.com";
    private final PaymentRepository repository;
    private final CommandGateway commandGateway;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();
    private String getAuth(String client_id, String app_secret) {
        String auth = client_id + ":" + app_secret;
        return Base64.getEncoder().encodeToString(auth.getBytes());
    }
    private String generateAccessToken() {
        String auth = this.getAuth(
                paypalConfig.getClientId(),
                paypalConfig.getSecret()
        );
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + auth);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        HttpEntity<?> request = new HttpEntity<>(requestBody, headers);
        requestBody.add("grant_type", "client_credentials");

        ResponseEntity<String> response = restTemplate.postForEntity(
                BASE_URL +"/v1/oauth2/token",
                request,
                String.class
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("GET TOKEN: SUCCESSFUL!");
            return new JSONObject(response.getBody()).getString("access_token");
        } else {
            log.error("GET TOKEN: FAILED!");
            return "Unavailable to get ACCESS TOKEN, STATUS CODE " + response.getStatusCode();
        }
    }
    @Override
    @SneakyThrows
    public Object createPayment(String orderId, OrderDTO orderDTO){
        String accessToken = generateAccessToken();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = setHeader(accessToken);
        PayPalAppContextDTO context = new PayPalAppContextDTO();
        context.setReturnUrl("http://localhost:8888/api/v1/payment/"+orderId+"/success");
        context.setCancelUrl("http://localhost:8888/api/v1/payment/"+orderId+"/cancel");
        orderDTO.setApplicationContext(context);
        //JSON String
        String requestJson = this.objectMapper.writeValueAsString(orderDTO);
        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

        ResponseEntity<Object> response = restTemplate.exchange(
                BASE_URL + "/v2/checkout/orders",
                HttpMethod.POST,
                entity,
                Object.class
        );
        if (response.getStatusCode() == HttpStatus.CREATED) {
            log.info("ORDER CREATED");
            return response.getBody();
        } else {
            log.error("FAILED CREATE ORDER");
            return "Unavailable to get CREATE ORDER, STATUS CODE " + response.getStatusCode();
        }
    }
    @Override
    public Object capturePayment(String orderId, String token){
        String accessToken = generateAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = setHeader(accessToken);

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/v2/checkout/orders/" + token + "/capture",
                HttpMethod.POST,
                entity,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.CREATED) {
            log.info("PAYMENT SUCCESSFUL");
            final Optional<Payment> payment = this.repository.findByOrderId(orderId);
            payment.ifPresent(e->{
                this.commandGateway.send(new ProcessPaymentCommand(
                   e.getId(),
                   orderId,
                   PaymentState.COMPLETED
                ));
            });
        } else {
            log.error("PAYMENT FAILED");
            final Optional<Payment> payment = this.repository.findByOrderId(orderId);
            payment.ifPresent(e->{
                this.commandGateway.send(new ProcessPaymentCommand(
                        e.getId(),
                        orderId,
                        PaymentState.FAILED
                ));
            });
        }
        return "Payment has been updated";
    }
    @Override
    public Object cancelPayment(String orderId){
        log.error("PAYMENT FAILED");
        final Optional<Payment> payment = this.repository.findByOrderId(orderId);
        payment.ifPresent(e->{
            this.commandGateway.send(new ProcessPaymentCommand(
                    e.getId(),
                    orderId,
                    PaymentState.FAILED
            ));
        });
        return "Payment has been updated";
    }

    @Override
    public Object updateStatus(String orderId, String status) {
        final Optional<Payment> payment = this.repository.findByOrderId(orderId);
        payment.ifPresent(e->{
            e.setState(PaymentState.valueOf(status));
            this.repository.save(e);
        });
        return "Payment has been updated";
    }

    private HttpHeaders setHeader(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
