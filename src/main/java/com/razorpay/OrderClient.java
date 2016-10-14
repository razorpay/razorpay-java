package com.razorpay;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class OrderClient {

    /**
     * Make constructor package protected
     */
    OrderClient(){}


    public Order create(JSONObject options) throws IOException, RazorpayException {
        Response response = ApiUtils.postRequest("/orders", options);
        return Utils.processResponse(response);
    }

    public List<Order> fetchAll(JSONObject options) throws IOException, RazorpayException {
        Response response = ApiUtils.getRequest("/orders", options);
        return Utils.processCollectionResponse(response);
    }

    public Order fetch(String id) throws IOException, RazorpayException {
        Response response = ApiUtils.getRequest(String.format("/orders/%s", id), null);
        return Utils.processResponse(response);
    }

    public List<Payment> fetchPayments(String id) throws IOException, RazorpayException {
        Response response = ApiUtils.getRequest(String.format("/orders/%s/payments", id), null);
        return Utils.processCollectionResponse(response);
    }
}
