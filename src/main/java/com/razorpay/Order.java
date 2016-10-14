package com.razorpay;


import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class Order extends Entity {

    public Order(JSONObject jsonObject) {
        super(jsonObject);
    }

    public List<Payment> fetchPayments() throws IOException, RazorpayException {
        Response response = ApiUtils.getRequest(String.format("/orders/%s/payments", get("id")), null);
        return Utils.processCollectionResponse(response);
    }
}
