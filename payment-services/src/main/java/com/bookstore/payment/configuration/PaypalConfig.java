package com.bookstore.payment.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PaypalConfig {
    @Value("${paypal.clientId}")
    private String clientId;
    @Value("${paypal.secret}")
    private String secret;

}
