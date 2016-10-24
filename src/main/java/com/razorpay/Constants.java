package com.razorpay;

import okhttp3.MediaType;

public class Constants {

  // Entities Keys
  static final String ID = "id";
  static final String CREATED_AT = "created_at";
  static final String CAPTURED_AT = "captured_at";

  // API constants
  static final String BASE_URL = "https://api.razorpay.com/v1";
  static final String AUTH_HEADER_KEY = "Authorization";
  static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

  // API URI
  static final String PAYMENT_GET = "/payments/%s";
  static final String PAYMENT_LIST = "/payments";
  static final String PAYMENT_CAPTURE = "/payments/%s/capture";
  static final String PAYMENT_REFUND = "/payments/%s/refund";

  static final String PAYMENT_REFUND_LIST = "/payments/%s/refunds";
  static final String PAYMENT_REFUND_GET = "/payments/%s/refunds/%s";

  static final String REFUND_GET = "/refunds/%s";
  static final String REFUND_LIST = "/refunds";

  static final String ORDER_CREATE = "/orders";
  static final String ORDER_GET = "/orders/%s";
  static final String ORDER_LIST = "/orders";
  static final String ORDER_PAYMENT_LIST = "/orders/%s/payments";

}
