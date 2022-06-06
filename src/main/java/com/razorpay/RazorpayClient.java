package com.razorpay;

import java.util.Map;

import okhttp3.Credentials;

public class RazorpayClient {

  public PaymentClient payments;
  public RefundClient refunds;
  public OrderClient orders;
  public InvoiceClient invoices;
  public CardClient cards;
  public CustomerClient customers;
  public TransferClient transfers;
  public SubscriptionClient subscriptions;
  public AddonClient addons;
  public PlanClient plans;
  public SettlementClient settlement;
  public QrCodeClient qrCode;
  public PaymentLinkClient paymentLink;
  public ItemClient items;
  public FundAccountClient fundAccount;
  public VirtualAccountClient virtualAccounts;
  public ContactClient contact;

  public RazorpayClient(String key, String secret) throws RazorpayException {
    this(key, secret, false);
  }

  public RazorpayClient(String key, String secret, Boolean enableLogging) throws RazorpayException {
    ApiUtils.createHttpClientInstance(enableLogging);
    String auth = Credentials.basic(key, secret);
    payments = new PaymentClient(auth);
    refunds = new RefundClient(auth);
    orders = new OrderClient(auth);
    invoices = new InvoiceClient(auth);
    cards = new CardClient(auth);
    customers = new CustomerClient(auth);
    transfers = new TransferClient(auth);
    subscriptions = new SubscriptionClient(auth);
    addons = new AddonClient(auth);
    plans = new PlanClient(auth);
    settlement = new SettlementClient(auth);
    qrCode = new QrCodeClient(auth);
    paymentLink = new PaymentLinkClient(auth);
    items = new ItemClient(auth);
    fundAccount = new FundAccountClient(auth);
    virtualAccounts = new VirtualAccountClient(auth);
    contact = new ContactClient(auth);

  }

  public RazorpayClient addHeaders(Map<String, String> headers) {
    ApiUtils.addHeaders(headers);
    return this;
  }
}
