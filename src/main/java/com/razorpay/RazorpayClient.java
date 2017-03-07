package com.razorpay;

import okhttp3.Credentials;

public class RazorpayClient {

  public PaymentClient Payments;
  public RefundClient Refunds;
  public OrderClient Orders;
  public InvoiceClient Invoices;
  public TokenClient Tokens;
  public CardClient Cards;
  public CustomerClient Customers;
  public Utils utility;

  public RazorpayClient(String key, String secret) {
    this(key, secret, false);
  }

  public RazorpayClient(String key, String secret, Boolean enableLogging) {
    ApiUtils.createHttpClientInstance(enableLogging);
    String auth = Credentials.basic(key, secret);
    Payments = new PaymentClient(auth);
    Refunds = new RefundClient(auth);
    Orders = new OrderClient(auth);
    Invoices = new InvoiceClient(auth);
    Tokens = new TokenClient(auth);
    Cards = new CardClient(auth);
    Customers = new CustomerClient(auth);
    utility = new Utils(key, secret);
  }
}
