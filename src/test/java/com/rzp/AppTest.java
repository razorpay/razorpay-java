package com.rzp;

import com.razorpay.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

class RzpTest {
    static String API_KEY = "rzp_test_1DP5mmOlF5G5ag";
    static String API_SECRET = "thisissupersecret";
    public static void main(String[] args) throws IOException, RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(API_KEY, API_SECRET);
        List<Payment> payments = razorpayClient.Payments.fetchAll();
        for(Payment payment : payments){
            System.out.println(payment.toString());
            System.out.println(payment.get("notes").toString());
            System.out.println(payment.get("created_at").toString());
            int a = payment.get("amount");
            System.out.println(a);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", 100);
        jsonObject.put("currency", "INR");
        jsonObject.put("receipt", "yelo_boi");
        Order order = razorpayClient.Orders.create(jsonObject);
        System.out.println(order.toString());
    }
}
