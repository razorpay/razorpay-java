package com.razorpay.addon;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddOnItem {
    String name;
    Integer amount;
    String currency;
    String description;

}
