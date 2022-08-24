package com.razorpay;


import java.io.UnsupportedEncodingException;
import java.util.Map;


public class RazorpayClient {

    public PaymentClient payments;
    public InvoiceClient invoices;
    public CustomerClient customers;
    public CardClient cards;
    public FundAccountClient fundAccount;
    public ItemClient items;
    public OrderClient orders;
    public AddonClient addons;
    public RefundClient refunds;
    public TransferClient transfers;
    public SubscriptionRegistrationClient subscriptionRegistrations;
    public SubscriptionClient subscriptions;
    public PlanClient plans;
    public SettlementClient settlement;
    public QrCodeClient qrCode;
    public PaymentLinkClient paymentLink;
    public VirtualAccountClient virtualAccounts;

    public RazorpayClient(String key, String secret) throws RazorpayException, UnsupportedEncodingException {
        this(key, secret, false);
    }

    /**
     * Initializes Razorpay client instance
     * @param key
     * @param secret
     * @param enableLogging
     * @throws RazorpayException
     * @throws UnsupportedEncodingException
     */
    public RazorpayClient(String key, String secret, Boolean enableLogging) throws RazorpayException, UnsupportedEncodingException {
        byte[] message = (key + ":" + secret).getBytes("UTF-8");
        String auth = javax.xml.bind.DatatypeConverter.printBase64Binary(message);
        ApiUtils apiUtils = new ApiUtils();

        cards = new CardClient(auth,apiUtils);
        items = new ItemClient(auth,apiUtils);
        plans = new PlanClient(auth,apiUtils);
        orders = new OrderClient(auth,apiUtils);
        addons = new AddonClient(auth,apiUtils);
        qrCode = new QrCodeClient(auth,apiUtils);
        refunds = new RefundClient(auth,apiUtils);
        payments = new PaymentClient(auth,apiUtils);
        invoices = new InvoiceClient(auth,apiUtils);
        customers = new CustomerClient(auth,apiUtils);
        transfers = new TransferClient(auth,apiUtils);
        settlement = new SettlementClient(auth,apiUtils);
        fundAccount = new FundAccountClient(auth,apiUtils);
        paymentLink = new PaymentLinkClient(auth,apiUtils);
        subscriptions = new SubscriptionClient(auth,apiUtils);
        virtualAccounts = new VirtualAccountClient(auth,apiUtils);
        subscriptionRegistrations = new SubscriptionRegistrationClient(auth,apiUtils);
    }

    /**
     * Add headers
     * ex: X-Razorpay-Account
     * @param headers
     * @return
     */
    public RazorpayClient addHeaders(Map<String, String> headers) {
        ApiUtils.addHeaders(headers);
        return this;
    }
}