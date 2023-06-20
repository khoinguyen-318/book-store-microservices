package com.bookstore.payment.command.controller;

import com.bookstore.payment.command.model.OrderDTO;
import com.bookstore.payment.command.services.IPaymentServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final IPaymentServices paymentServices;

    @PostMapping("/{orderId}")
    public Object createPayment(@RequestBody OrderDTO orderDTO, @PathVariable String orderId){
        return this.paymentServices.createPayment(orderId,orderDTO);
    }

    @GetMapping("/{orderId}/success")
    public Object capturePayment(HttpServletRequest request, @PathVariable String orderId){
        final String token = request.getParameter("token");
        return this.paymentServices.capturePayment(orderId,token);
    }
    @GetMapping("/{orderId}/cancel")
    public Object cancelPayment(HttpServletRequest request, @PathVariable String orderId){
        final String token = request.getParameter("token");
        return this.paymentServices.cancelPayment(orderId);
    }
    @PatchMapping("/{orderId}")
    public Object completeCodPayment(@PathVariable String orderId,
                                     @RequestParam String status){
        return this.paymentServices.updateStatus(orderId,status);
    }
}
