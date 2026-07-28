package com.razorpay;

import org.json.JSONObject;

public class RazorpayException extends Exception {

  private transient JSONObject errorResponse;
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

  public String getCode() {
    return errorResponse != null ? errorResponse.optString("code", null) : null;
  }

  public String getDescription() {
    return errorResponse != null ? errorResponse.optString("description", null) : null;
  }

  public String getField() {
    return errorResponse != null ? errorResponse.optString("field", null) : null;
  }

  public String getReason() {
    return errorResponse != null ? errorResponse.optString("reason", null) : null;
  }

  public String getSource() {
    return errorResponse != null ? errorResponse.optString("source", null) : null;
  }

  public String getStep() {
    return errorResponse != null ? errorResponse.optString("step", null) : null;
  }
}
