package com.razorpay;

import org.json.JSONObject;

import java.util.List;

public class FundAccountClient extends ApiClient{

	FundAccountClient(String auth) {
		super(auth);
	}

	public FundAccount create(JSONObject request) throws RazorpayException {
	    return post(Constants.VERSION, Constants.FUND_ACCOUNT_CREATE, request);
	}

	public FundAccount fetch(String id) throws RazorpayException {
	    return get(Constants.VERSION, String.format(Constants.FUND_ACCOUNT_FETCH, id), null);
	}

	public List<FundAccount> fetchAll() throws RazorpayException {
		return fetchAll(null);
	}

	/**
	 * This method get list of fundaccounts filtered by parameters @request
	 * @throws RazorpayException
	 */
	public List<FundAccount> fetchAll(JSONObject request) throws RazorpayException {
		return getCollection(Constants.VERSION, Constants.FUND_ACCOUNT_LIST, request);
	}
}