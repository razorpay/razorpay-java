package com.razorpay;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class RazorpayClient {

  public PaymentClient Payments;
  public RefundClient Refunds;
  public OrderClient Orders;


  public RazorpayClient(String key, String secret){
    this(key, secret, false);
  }

  public RazorpayClient(String key, String secret, Boolean enableLogging) {
    ApiUtils.createHttpClientInstance(enableLogging);
    String auth = Credentials.basic(key, secret);
    Payments = new PaymentClient(auth);
    Refunds = new RefundClient(auth);
    Orders = new OrderClient(auth);
  }
}
