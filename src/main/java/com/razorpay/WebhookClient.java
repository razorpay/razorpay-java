package com.razorpay;

import org.json.JSONObject;

import java.util.List;

public class WebhookClient extends ApiClient {

    WebhookClient(String auth) {
        super(auth);
    }

    public Webhook create(String id, JSONObject request) throws RazorpayException {
        return post(Constants.VERSION_V2, String.format(Constants.WEBHOOK_CREATE, id), request);
    }

    public Webhook fetch(String id, String webhook_id) throws RazorpayException {
        return get(Constants.VERSION_V2, String.format(Constants.WEBHOOK_FETCH, id, webhook_id), null);
    }

    public List<Webhook> fetchAll(String id) throws RazorpayException {
        return fetchAll(id,null);
    }

    public List<Webhook> fetchAll(String id, JSONObject request) throws RazorpayException {
        return getCollection(Constants.VERSION_V2, String.format(Constants.WEBHOOK_FETCH_ALL, id), request);
    }

    public Webhook edit(String id, String webhook_id, JSONObject request) throws RazorpayException {
        return patch(Constants.VERSION_V2, String.format(Constants.WEBHOOK_EDIT, id, webhook_id), request);
    }

    public List<Webhook> delete(String id, String webhook_id) throws RazorpayException {
        return delete(Constants.VERSION_V2, String.format(Constants.WEBHOOK_EDIT, id, webhook_id), null);
    }
}
