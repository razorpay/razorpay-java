package com.razorpay;

import org.json.JSONObject;

import java.util.List;

public class TokenClient extends ApiClient{
    TokenClient(String auth) {
        super(auth);
    }

    public Token create(JSONObject request) throws RazorpayException {
        return post(Constants.VERSION, Constants.CREATE_TOKEN, request);
    }

    public Token fetch(JSONObject request) throws RazorpayException {
        return post(Constants.VERSION, Constants.FETCH_TOKEN, request);
    }

    public List<Token> delete(JSONObject request) throws RazorpayException {
        return post(Constants.VERSION, Constants.DELETE_TOKEN, request);
    }

    public Token processPaymentOnAlternatePAorPG(JSONObject request) throws RazorpayException {
        return post(Constants.VERSION, Constants.TOKEN_SERVICE_PROVIDER, request);
    }

}
