package com.razorpay;

import java.util.Map;
import java.util.UUID;

import okhttp3.Credentials;

public class RazorpayClient {

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

  private String clientKey = UUID.randomUUID().toString();

  public RazorpayClient(String key, String secret) throws RazorpayException {
    this(key, secret, false);
  }

  public RazorpayClient(String key, String secret, Boolean enableLogging) throws RazorpayException {
    ApiUtils.createHttpClientInstance(enableLogging);
    String auth = Credentials.basic(key, secret);
    Payments = new PaymentClient(auth, clientKey);
    Refunds = new RefundClient(auth, clientKey);
    Orders = new OrderClient(auth, clientKey);
    Invoices = new InvoiceClient(auth, clientKey);
    Cards = new CardClient(auth, clientKey);
    Customers = new CustomerClient(auth, clientKey);
    Transfers = new TransferClient(auth, clientKey);
    Subscriptions = new SubscriptionClient(auth, clientKey);
    Addons = new AddonClient(auth, clientKey);
    Plans = new PlanClient(auth, clientKey);
    VirtualAccounts = new VirtualAccountClient(auth, clientKey);
  }

  public RazorpayClient addHeaders(Map<String, String> headers) {
    ApiUtils.addHeaders(clientKey, headers);
    return this;
  }
}
