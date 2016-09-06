package com.razorpay;

public class RazorpayException extends Exception {
    public RazorpayException(int code, String description) {
        super(code + " - " + description);
    }
}
