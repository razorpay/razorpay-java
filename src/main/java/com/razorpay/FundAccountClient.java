package com.razorpay;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class FundAccountClient extends ApiClient{

	FundAccountClient(String auth, ApiUtils apiUtils) {
		super(auth,apiUtils);
	}

	public FundAccount create(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
	    return post(Constants.FUND_ACCOUNT_CREATE, request);
	}

	public FundAccount fetch(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
	    return get(String.format(Constants.FUND_ACCOUNT_FETCH, id), null);
	}

	public List<FundAccount> fetchAll() throws RazorpayException, JSONException, IOException, URISyntaxException {
		return fetchAll(null);
	}

	/**
	 * This method get list of fundaccounts filtered by parameters @request
	 * @throws RazorpayException
	 */
	public List<FundAccount> fetchAll(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
		return getCollection(Constants.FUND_ACCOUNT_LIST, request);
	}
}