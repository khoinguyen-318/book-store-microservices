package com.bookstore.payment.command.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
public class OrderDTO implements Serializable {
    @NotBlank
    private OrderIntent intent;

    @NotBlank
    @JsonProperty("purchase_units")
    private List<PurchaseUnits> purchaseUnits;
    @JsonProperty("application_context")
    private PayPalAppContextDTO applicationContext;
}

