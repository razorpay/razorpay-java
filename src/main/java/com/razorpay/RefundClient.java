package com.razorpay;


import com.razorpay.ApiUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class RefundClient {

    /**
     * Make constructor package protected
     */
    RefundClient(){}

    public List<Refund> fetchAll(JSONObject options) throws IOException, RazorpayException {
        Response response = ApiUtils.getRequest("/refunds", options);
        return Utils.processCollectionResponse(response, Model.ENTITY_REFUND);
    }

    public Refund fetch(String id) throws IOException, RazorpayException {
        Response response = ApiUtils.getRequest(String.format("/refunds/%s", id), null);
        return Utils.processResponse(response, Model.ENTITY_REFUND);
    }

    public List<Refund> fetchAll() throws IOException, RazorpayException {
        return fetchAll(null);
    }
}
