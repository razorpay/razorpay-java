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
  public TokenClient token;
  public IinClient iin;
  public AccountClient account;
  public StakeholderClient stakeholder;
  public ProductClient product;
  public WebhookClient webhook;

  public DisputeClient dispute;
  public TncMap tncMap;
  public DocumentClient document;

  public BankAccountClient bankAccount;

  public RazorpayClient(String key, String secret) throws RazorpayException {
    this(key, secret, false);
  }

  public RazorpayClient(String accessToken) throws RazorpayException {
    String auth = "Bearer " + accessToken;
    initializeResources(auth, false);
  }

  public RazorpayClient(String key, String secret, Boolean enableLogging) throws RazorpayException {
    String auth = Credentials.basic(key, secret);
    initializeResources(auth, enableLogging);
  }
  private void initializeResources(String auth, Boolean enableLogging) throws RazorpayException {
    ApiUtils.createHttpClientInstance(enableLogging);
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
    token = new TokenClient(auth);
    iin = new IinClient(auth);
    account = new AccountClient(auth);
    stakeholder = new StakeholderClient(auth);
    product = new ProductClient(auth);
    webhook = new WebhookClient(auth);
    document = new DocumentClient(auth);
    dispute = new DisputeClient(auth);
    bankAccount = new BankAccountClient(auth);
  }

  public RazorpayClient addHeaders(Map<String, String> headers) {
    ApiUtils.addHeaders(headers);
    return this;
  }
}
