package com.razorpay;

import org.json.JSONObject;

public class DocumentClient extends ApiClient {

    DocumentClient(String auth) {super(auth);}

    public Document create(JSONObject request) throws RazorpayException {
        return post(Constants.VERSION, Constants.DOCUMENTS, request);
    }

    public Document fetch(String id) throws RazorpayException {
        return get(Constants.VERSION, String.format(Constants.DOCUMENT_FETCH, id), null);
    }
}
