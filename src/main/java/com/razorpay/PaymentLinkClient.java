package com.razorpay;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentLinkClient extends ApiClient {

    PaymentLinkClient(String auth) {
        super(auth);
    }

    public PaymentLink create(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return post(Constants.PAYMENTLINK_CREATE, request);
    }

    public PaymentLink fetch(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return get(String.format(Constants.PAYMENTLINK_GET, id), null);
    }

    public List<PaymentLink> fetchAll() throws RazorpayException, JSONException, IOException, URISyntaxException {
        return fetchAll(null);
    }

    public List<PaymentLink> fetchAll(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return getCollection(Constants.PAYMENTLINK_LIST, request);
    }

    public PaymentLink cancel(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return post(String.format(Constants.PAYMENTLINK_CANCEL, id), null);
    }

    public PaymentLink edit(String id, JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return patch(String.format(Constants.PAYMENTLINK_EDIT, id), request);
    }

    public PaymentLink notifyBy(String id, String medium) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return post(String.format(Constants.PAYMENTLINK_NOTIFYBY, id, medium), null);
    }
}