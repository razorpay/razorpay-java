package com.razorpay;

public class RazorpayException extends Exception {

  private static final long serialVersionUID = 1L;

  private final Integer statusCode;
  private final String code;
  private final String description;

  public RazorpayException(String message) {
    super(message);
    this.statusCode = null;
    this.code = null;
    this.description = null;
  }

  public RazorpayException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
    this.code = null;
    this.description = null;
  }

  public RazorpayException(String message, int statusCode, String code, String description) {
    super(message);
    this.statusCode = statusCode;
    this.code = code;
    this.description = description;
  }

  public RazorpayException(String message, Throwable cause) {
    super(message, cause);
    this.statusCode = null;
    this.code = null;
    this.description = null;
  }

  public RazorpayException(Throwable cause) {
    super(cause);
    this.statusCode = null;
    this.code = null;
    this.description = null;
  }

  public RazorpayException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.statusCode = null;
    this.code = null;
    this.description = null;
  }

  /**
   * Returns the HTTP status code when the exception was caused by an API error response.
   *
   * @return the HTTP status code, or null if not available
   */
  public Integer getStatusCode() {
    return statusCode;
  }

  /**
   * Returns the API error code (e.g. BAD_REQUEST_ERROR) when present in the error response.
   *
   * @return the error code, or null if not available
   */
  public String getCode() {
    return code;
  }

  /**
   * Returns the API error description when present in the error response.
   *
   * @return the error description, or null if not available
   */
  public String getDescription() {
    return description;
  }
}
