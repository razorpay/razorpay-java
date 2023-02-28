package com.razorpay;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class SubscriptionRegistrationClient extends ApiClient {

    SubscriptionRegistrationClient(String auth, ApiUtils apiUtils) {
        super(auth,apiUtils);
    }

    public Invoice create(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return post(Constants.SUBSCRIPTION_REGISTRATION_LINK, request);
    }
}
