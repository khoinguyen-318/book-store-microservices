package com.bookstore.payment.command.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PurchaseUnits {
    private Amount amount;
}
class Amount{
    @JsonProperty("currency_code")
    private String currency;
    @JsonProperty("value")
    private String total;
}
