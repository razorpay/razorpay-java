package com.razorpay;

import org.json.JSONObject;

public class IinClient extends ApiClient{

    IinClient(String auth) {
        super(auth);
    }

    public Iin fetch(String id) throws RazorpayException {
        return get(Constants.VERSION, String.format(Constants.IIN_FETCH, id), null);
    }

    public Iin fetchList(JSONObject request) throws RazorpayException {
        return get(Constants.VERSION, String.format(Constants.IIN_FETCH_LIST), request);
    }
}
