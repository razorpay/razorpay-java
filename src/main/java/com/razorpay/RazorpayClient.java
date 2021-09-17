package com.razorpay;

import java.util.Map;

import okhttp3.Credentials;

public class RazorpayClient {
  private static final int DEFAULT_TIMEOUT_DURATION_IN_SECONDS = 60;

  public PaymentClient Payments;
  public RefundClient Refunds;
  public OrderClient Orders;
  public InvoiceClient Invoices;
  public CardClient Cards;
  public CustomerClient Customers;
  public TransferClient Transfers;
  public SubscriptionClient Subscriptions;
  public AddonClient Addons;
  public PlanClient Plans;
  public VirtualAccountClient VirtualAccounts;

  public RazorpayClient(String key, String secret) throws RazorpayException {
    this(key, secret, false, DEFAULT_TIMEOUT_DURATION_IN_SECONDS);
  }

  public RazorpayClient(String key, String secret, Boolean enableLogging) throws RazorpayException {
    this(key, secret, enableLogging, DEFAULT_TIMEOUT_DURATION_IN_SECONDS);
  }

  public RazorpayClient(String key, String secret, int timeoutDurationInSeconds) throws RazorpayException {
    this(key, secret, false, timeoutDurationInSeconds);
  }

  public RazorpayClient(String key, String secret, Boolean enableLogging, Integer timeout) throws RazorpayException {
    ApiUtils.createHttpClientInstance(enableLogging, timeout);
    String auth = Credentials.basic(key, secret);
    Payments = new PaymentClient(auth);
    Refunds = new RefundClient(auth);
    Orders = new OrderClient(auth);
    Invoices = new InvoiceClient(auth);
    Cards = new CardClient(auth);
    Customers = new CustomerClient(auth);
    Transfers = new TransferClient(auth);
    Subscriptions = new SubscriptionClient(auth);
    Addons = new AddonClient(auth);
    Plans = new PlanClient(auth);
    VirtualAccounts = new VirtualAccountClient(auth);
  }

  public RazorpayClient addHeaders(Map<String, String> headers) {
    ApiUtils.addHeaders(headers);
    return this;
  }
}
