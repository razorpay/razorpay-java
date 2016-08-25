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
        return  Utils.processResponse(response, Model.ENTITY_PAYMENT);
    }

    public List<Payment> fetchAll(JSONObject options) throws IOException, RazorpayException {
        Response response = ApiUtils.getRequest("/payments", options);
        return Utils.processCollectionResponse(response, Model.ENTITY_PAYMENT);
    }

    public List<Payment> fetchAll() throws IOException, RazorpayException {
        return fetchAll(null);
    }
}
