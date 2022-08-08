package com.razorpay;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderClient extends ApiClient {

    OrderClient(String auth) {
        super(auth);
    }

    public Order create(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return post(Constants.ORDER_CREATE, request);
    }

    public List<Order> fetchAll() throws RazorpayException, JSONException, IOException, URISyntaxException {
        return fetchAll(null);
    }

    public List<Order> fetchAll(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return getCollection(Constants.ORDER_LIST, request);
    }

    public Order fetch(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return get(String.format(Constants.ORDER_GET, id), null);
    }

    public List<Payment> fetchPayments(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return getCollection(String.format(Constants.ORDER_PAYMENT_LIST, id), null);
    }

    public Order edit(String id, JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return patch(String.format(Constants.ORDER_EDIT, id), request);
    }
}