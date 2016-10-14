package com.razorpay;



import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;


public class PaymentClient {

    /**
     * Make constructor package protected
     */
    PaymentClient() {
    }

    public Payment fetch(String id) throws IOException, RazorpayException {
        Response response = ApiUtils.getRequest(String.format("/payments/%s", id), null);
        return  Utils.processResponse(response);
    }

    public List<Payment> fetchAll(JSONObject options) throws IOException, RazorpayException {
        Response response = ApiUtils.getRequest("/payments", options);
        return Utils.processCollectionResponse(response);
    }

    public List<Payment> fetchAll() throws IOException, RazorpayException {
        return fetchAll(null);
    }

    public Payment capture(String id, String amount) throws IOException, RazorpayException {
        JSONObject options = new JSONObject();
        options.put("amount", amount);
        Response response = ApiUtils.postRequest(String.format("/payments/%s/capture", id), options);
        return Utils.processResponse(response);
    }

    public Refund refund(String id, JSONObject options) throws IOException, RazorpayException {
        Response response = ApiUtils.postRequest(String.format("/payments/%s/refund", id), options);
        return Utils.processResponse(response);
    }

    public Refund refund(String id) throws IOException, RazorpayException {
        return refund(id, null);
    }

    public Refund fetchRefund(String id, String refundId) throws IOException, RazorpayException {
        Response response = ApiUtils.getRequest(String.format("/payments/%s/refunds/%s", id, refundId), null);
        return Utils.processResponse(response);
    }

    public List<Refund> fetchAllRefunds(String id, JSONObject options) throws IOException, RazorpayException {
        Response response = ApiUtils.getRequest(String.format("/payments/%s/refunds", id), options);
        return Utils.processCollectionResponse(response);
    }

    public List<Refund> fetchAllRefunds(String id) throws IOException, RazorpayException {
        return fetchAllRefunds(id, null);
    }
}
