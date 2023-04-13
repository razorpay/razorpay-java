package com.razorpay;

import java.util.Map;

import okhttp3.Credentials;

public class RazorpayClient {

  @Override
  public String toString() {
    return "RazorpayClient{" +
            "payments=" + payments +
            ", refunds=" + refunds +
            ", orders=" + orders +
            ", invoices=" + invoices +
            ", cards=" + cards +
            ", customers=" + customers +
            ", transfers=" + transfers +
            ", subscriptions=" + subscriptions +
            ", addons=" + addons +
            ", plans=" + plans +
            ", settlement=" + settlement +
            ", qrCode=" + qrCode +
            ", paymentLink=" + paymentLink +
            ", items=" + items +
            ", fundAccount=" + fundAccount +
            ", virtualAccounts=" + virtualAccounts +
            '}';
  }

  private PaymentClient payments;
  private RefundClient refunds;
  private OrderClient orders;
  private InvoiceClient invoices;
  private CardClient cards;
  private CustomerClient customers;
  private TransferClient transfers;
  private SubscriptionClient subscriptions;
  private AddonClient addons;
  private PlanClient plans;
  private SettlementClient settlement;
  private QrCodeClient qrCode;
  private PaymentLinkClient paymentLink;
  private ItemClient items;
  private FundAccountClient fundAccount;
  private VirtualAccountClient virtualAccounts;

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
  }

  public RazorpayClient addHeaders(Map<String, String> headers) {
    ApiUtils.addHeaders(headers);
    return this;
  }
}
