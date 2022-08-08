package com.razorpay;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class RefundClient extends ApiClient {

    RefundClient(String auth) {
        super(auth);
    }

    public Refund create(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return post(Constants.REFUNDS, request);
    }

    public List<Refund> fetchAll(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return getCollection(Constants.REFUNDS, request);
    }

    public Refund fetch(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return get(String.format(Constants.REFUND, id), null);
    }

    public List<Refund> fetchAll() throws RazorpayException, JSONException, IOException, URISyntaxException {
        return fetchAll(null);
    }

    public List<Refund> fetchMultipleRefund(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return fetchMultipleRefund(id,null);
    }

    public List<Refund> fetchMultipleRefund(String id,JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return getCollection(String.format(Constants.REFUND_MULTIPLE, id), request);
    }

    public Refund edit(String id, JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return patch(String.format(Constants.REFUND, id), request);
    }
}