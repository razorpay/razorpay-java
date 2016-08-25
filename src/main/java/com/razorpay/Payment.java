package com.razorpay;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class Payment extends Model{

    public Payment(JSONObject jsonObject) {
        super(jsonObject);
    }

    public Payment capture(String amount) throws IOException, RazorpayException {
        JSONObject options = new JSONObject();
        options.put("amount", amount);
        Response response = ApiUtils.postRequest(String.format("/payments/%s", get("id")), options);
        return Utils.processResponse(response, ENTITY_PAYMENT);
    }

    public Refund refund(JSONObject options) throws IOException, RazorpayException {
        Response response = ApiUtils.postRequest(String.format("/payments/%s", get("id")), options);
        return Utils.processResponse(response, ENTITY_REFUND);
    }

    public Refund refund() throws IOException, RazorpayException {
        return refund(null);
    }

    public Refund fetchRefund(String refundId) throws IOException, RazorpayException {
        Response response = ApiUtils.getRequest(String.format("/payments/%s/refunds/%s", get("id"), refundId), null);
        return Utils.processResponse(response, ENTITY_REFUND);
    }

    public List<Refund> fetchAllRefunds(JSONObject options) throws IOException, RazorpayException {
        Response response = ApiUtils.getRequest(String.format("/payments/%s/refunds", get("id")), options);
        return Utils.processCollectionResponse(response, ENTITY_REFUND);
    }

    public List<Refund> fetchAllRefunds() throws IOException, RazorpayException {
        return fetchAllRefunds(null);
    }
}
