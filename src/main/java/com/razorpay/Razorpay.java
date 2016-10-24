package com.razorpay;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class Razorpay {

  public PaymentClient paymentClient;
  public RefundClient refundClient;
  public OrderClient orderClient;

  private static Razorpay razorpayClient = null;

  public static Razorpay getClient(String key, String secret) {
    if (razorpayClient == null) {
      razorpayClient = new Razorpay(key, secret);
    }
    return razorpayClient;
  }

  public static Razorpay getClient(String key, String secret, Boolean enableLogging) {
    if (razorpayClient == null) {
      razorpayClient = new Razorpay(key, secret, enableLogging);
    }
    return razorpayClient;
  }

  private Razorpay(String key, String secret) {
    this(key, secret, false);
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

    paymentClient = PaymentClient.getInstance();
    refundClient = RefundClient.getInstance();
    orderClient = OrderClient.getInstance();
  }
}
