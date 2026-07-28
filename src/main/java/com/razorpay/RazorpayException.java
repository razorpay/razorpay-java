package com.razorpay;

import org.json.JSONObject;

public class RazorpayException extends Exception {

  private JSONObject errorResponse;
  private int statusCode;

  public RazorpayException(String message) {
    super(message);
    this.statusCode = 0;
    this.errorResponse = null;
  }

  public RazorpayException(String message, Throwable cause) {
    super(message, cause);
    this.statusCode = 0;
    this.errorResponse = null;
  }

  public RazorpayException(Throwable cause) {
    super(cause);
    this.statusCode = 0;
    this.errorResponse = null;
  }

  public RazorpayException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.statusCode = 0;
    this.errorResponse = null;
  }

  public RazorpayException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
    this.errorResponse = null;
  }

  public RazorpayException(String message, JSONObject errorResponse, int statusCode) {
    super(message);
    this.errorResponse = errorResponse;
    this.statusCode = statusCode;
  }

  public JSONObject getErrorResponse() {
    return errorResponse;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
