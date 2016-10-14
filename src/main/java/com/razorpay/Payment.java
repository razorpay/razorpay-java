package com.razorpay;

import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class Payment extends Entity {

    public Payment(JSONObject jsonObject) {
        super(jsonObject);
    }

    public Payment capture(JSONObject options) throws IOException, RazorpayException {
        Response response = ApiUtils.postRequest(String.format("/payments/%s/capture", get("id")), options);
        return Utils.processResponse(response);
    }

    public Refund refund(JSONObject options) throws IOException, RazorpayException {
        Response response = ApiUtils.postRequest(String.format("/payments/%s/refund", get("id")), options);
        return Utils.processResponse(response);
    }

    public Refund refund() throws IOException, RazorpayException {
        return refund(null);
    }

    public Refund fetchRefund(String refundId) throws IOException, RazorpayException {
        Response response = ApiUtils.getRequest(String.format("/payments/%s/refunds/%s", get("id"), refundId), null);
        return Utils.processResponse(response);
    }

    public List<Refund> fetchAllRefunds(JSONObject options) throws IOException, RazorpayException {
        Response response = ApiUtils.getRequest(String.format("/payments/%s/refunds", get("id")), options);
        return Utils.processCollectionResponse(response);
    }

    public List<Refund> fetchAllRefunds() throws IOException, RazorpayException {
        return fetchAllRefunds(null);
    }
}
