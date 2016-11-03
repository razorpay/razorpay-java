package com.razorpay;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class Razorpay {

  public PaymentClient Payments;
  public RefundClient Refunds;
  public OrderClient Orders;

  private static Razorpay razorpayClient = null;

  public static Razorpay getClient(String key, String secret) {
    if (razorpayClient == null) {
      razorpayClient = new Razorpay(key, secret, false);
    }
    return razorpayClient;
  }

  public static Razorpay getClient(String key, String secret, Boolean enableLogging) {
    if (razorpayClient == null) {
      razorpayClient = new Razorpay(key, secret, enableLogging);
    }
    return razorpayClient;
  }

  private Razorpay(String key, String secret, Boolean enableLogging) {
    ApiUtils.setAuthCredentials(key, secret);

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    if (enableLogging) {
      logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
    } else {
      logging.setLevel(HttpLoggingInterceptor.Level.NONE);
    }

    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();
    ApiUtils.setClient(client);

    Payments = PaymentClient.getInstance();
    Refunds = RefundClient.getInstance();
    Orders = OrderClient.getInstance();
  }
}
