package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class ItemClient extends ApiClient{

	ItemClient(String auth) {
		super(auth);
	}

	public Item create(JSONObject request) throws RazorpayException {
	    return post(Constants.ITEMS, request);
	}

	public Item fetch(String id) throws RazorpayException {
	    return get(String.format(Constants.ITEM, id), null);
	}

	public List<Item> fetchAll() throws RazorpayException {
	    return fetchAll(null);
	}

	public Item edit(String id, JSONObject request) throws RazorpayException {
		return patch(String.format(Constants.ITEM, id), request);
	}

	public List<Item> fetchAll(JSONObject request) throws RazorpayException {
	    return getCollection(Constants.ITEMS, request);
	}

	public List<Item> delete(String id) throws RazorpayException {
	   return delete(String.format(Constants.ITEM, id), null);
	}
}