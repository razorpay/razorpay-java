package com.razorpay;

public class RazorpayException extends Exception {
    public RazorpayException(String code, String description) {
        super(code + " : " + description);
    }

    public RazorpayException(String message) {
        super(message);
    }
}
