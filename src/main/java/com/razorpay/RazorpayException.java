package com.razorpay;

public class RazorpayException extends Exception {

  public RazorpayException(String message) {
    super(message);
  }

  public RazorpayException(String message, Throwable cause) {
    super(message, cause);
  }

  public RazorpayException(Throwable cause) {
    super(cause);
  }

  public RazorpayException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
