package com.razorpay;

import org.json.JSONObject;

public class AccountClient extends ApiClient {

    private AccountClient accountClient;
    AccountClient(String auth) {super(auth);}

    public Account create(JSONObject request) throws RazorpayException {
        return post(Constants.VERSION_V2, Constants.ACCOUNT_CREATE, request);
    }

    public Account fetch(String id) throws RazorpayException {
        return get(Constants.VERSION_V2, String.format(Constants.ACCOUNT_FETCH, id), null);
    }

    public Account edit(String id, JSONObject request) throws RazorpayException {
        return patch(Constants.VERSION_V2, String.format(Constants.ACCOUNT_EDIT, id), request);
    }

    public Account delete(String id) throws RazorpayException {
        return delete(Constants.VERSION_V2, String.format(Constants.ACCOUNT_DELETE, id), null);
    }

    public Account uploadAccountDoc(String id, JSONObject request) throws RazorpayException {
        return post(Constants.VERSION_V2, String.format(Constants.UPLOAD_ACCOUNT_DOCUMENT, id), request);
    }
    public Account fetchAccountDoc(String id) throws RazorpayException {
        return get(Constants.VERSION_V2, String.format(Constants.UPLOAD_ACCOUNT_DOCUMENT, id), null);
    }
}
