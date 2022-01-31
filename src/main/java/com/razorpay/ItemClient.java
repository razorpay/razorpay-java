package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class ItemClient extends ApiClient{

	ItemClient(String auth) {
		super(auth);
	}

	public Item create(JSONObject request) throws RazorpayException {
	    return post(Constants.ITEM_CREATE, request);
	}

	public Item fetch(String id) throws RazorpayException {
	    return get(String.format(Constants.ITEM_GET, id), null);
	}
	
	public List<Item> fetchAll() throws RazorpayException {
	    return fetchAll(null);
	}

	public List<Item> fetchAll(JSONObject request) throws RazorpayException {
	    return getCollection(Constants.ITEM_LIST, request);
	}
	
	public void delete(String id) throws RazorpayException {
	   delete(String.format(Constants.ITEM_DELETE, id), null);
	}
}