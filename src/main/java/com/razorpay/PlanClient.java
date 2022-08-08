package com.razorpay;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class PlanClient extends ApiClient {

    PlanClient(String auth) {
        super(auth);
    }

    public Plan create(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return post(Constants.PLAN_CREATE, request);
    }

    public Plan fetch(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return get(String.format(Constants.PLAN_GET, id), null);
    }

    public List<Plan> fetchAll() throws RazorpayException, JSONException, IOException, URISyntaxException {
        return fetchAll(null);
    }

    public List<Plan> fetchAll(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return getCollection(Constants.PLAN_LIST, request);
    }
}