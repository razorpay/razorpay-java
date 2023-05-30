package com.razorpay;

import org.json.JSONObject;

import java.util.List;

public class ProductClient extends ApiClient {

    ProductClient(String auth) {
        super(auth);
    }

    public Account requestProductConfiguration(String id, JSONObject request) throws RazorpayException {
        return post(Constants.VERSION_V2, String.format(Constants.PRODUCT_REQUEST_CONFIGURATION, id), request);
    }

    public Account fetch(String id, String product_id) throws RazorpayException {
        return get(Constants.VERSION_V2, String.format(Constants.PRODUCT_CONFIGURATION, id, product_id), null);
    }

    public Account edit(String id, String product_id, JSONObject request) throws RazorpayException {
        return patch(Constants.VERSION_V2, String.format(Constants.PRODUCT_CONFIGURATION, id, product_id), request);
    }

    public TncMap fetchTnc(String product_name) throws RazorpayException {
        return get(Constants.VERSION_V2, String.format(Constants.TNC_FETCH, product_name), null);
    }
}
