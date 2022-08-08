package com.razorpay;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemClient extends ApiClient{

    ItemClient(String auth) {
        super(auth);
    }

    public Item create(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return post(Constants.ITEMS, request);
    }

    public Item fetch(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return get(String.format(Constants.ITEM, id), null);
    }

    public List<Item> fetchAll() throws RazorpayException, JSONException, IOException, URISyntaxException {
        return fetchAll(null);
    }

    public Item edit(String id, JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return patch(String.format(Constants.ITEM, id), request);
    }

    public List<Item> fetchAll(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return getCollection(Constants.ITEMS, request);
    }

    public List<Item> delete(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return delete(String.format(Constants.ITEM, id), null);
    }
}