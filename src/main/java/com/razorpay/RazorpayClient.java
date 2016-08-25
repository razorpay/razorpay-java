package com.razorpay;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class RazorpayClient {

    public PaymentClient Payment;
    public RefundClient Refund;
    public OrderClient Order;

    /**
     *
     * @param key Razorpay API key
     * @param secret Razorpay API secret
     */
    public RazorpayClient(String key, String secret){
        ApiUtils.setAuthCredentials(key, secret);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        ApiUtils.setClient(client);
        Payment = new PaymentClient();
        Refund = new RefundClient();
        Order = new OrderClient();
    }

}
