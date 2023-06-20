package com.bookstore.coreapis.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StoreToken {
    public static String paymentId;
}
