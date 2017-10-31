package com.razorpay;

import java.util.HashMap;
import java.util.List;

public class MyClass {

    public static void main(String[] args){

        try {
            RazorpayClient razorpayClient = new RazorpayClient("rzp_test_1DP5mmOlF5G5ag", "thisissupersecret");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("Content-type", "application/json");
            razorpayClient.addHeaders(map);

            List<Payment> payments = razorpayClient.Payments.fetchAll();

            System.out.println(payments);

        } catch (RazorpayException e) {
            e.printStackTrace();
        }
    }

}
