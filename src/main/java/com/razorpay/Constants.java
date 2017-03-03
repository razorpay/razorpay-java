package com.razorpay;

import okhttp3.MediaType;

public class Constants {

  // API constants
  static final String SCHEME = "https";
  static final String HOSTNAME = "api.razorpay.com";
  static final Integer PORT = 443;
  static final String VERSION = "v1";

  static final String AUTH_HEADER_KEY = "Authorization";
  static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

  // API URI
  static final String PAYMENT_GET = "payments/%s";
  static final String PAYMENT_LIST = "payments";
  static final String PAYMENT_CAPTURE = "payments/%s/capture";
  static final String PAYMENT_REFUND = "payments/%s/refund";

  static final String PAYMENT_REFUND_LIST = "payments/%s/refunds";
  static final String PAYMENT_REFUND_GET = "payments/%s/refunds/%s";

  static final String REFUND_GET = "refunds/%s";
  static final String REFUND_LIST = "refunds";
  static final String REFUND_CREATE = "refunds";

  static final String ORDER_CREATE = "orders";
  static final String ORDER_GET = "orders/%s";
  static final String ORDER_LIST = "orders";
  static final String ORDER_PAYMENT_LIST = "orders/%s/payments";

  static final String INVOICE_CREATE = "invoices";
  static final String INVOICE_GET = "invoices/%s";
  static final String INVOICE_LIST = "invoices";

  static final String CARD_GET = "cards/%s";
}
