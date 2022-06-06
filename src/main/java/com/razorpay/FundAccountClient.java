package com.razorpay;

import org.json.JSONObject;

import java.util.List;

public class FundAccountClient extends ApiClient{

	FundAccountClient(String auth) {
		super(auth);
	}

	public FundAccount create(JSONObject request) throws RazorpayException {
	    return post(Constants.FUND_ACCOUNT_CREATE, request);
	}

	public FundAccount fetch(String id) throws RazorpayException {
	    return get(String.format(Constants.FUND_ACCOUNT_FETCH, id), null);
	}

	public FundAccount update(String id, JSONObject request) throws RazorpayException {
		return patch(String.format(Constants. FUND_ACCOUNT_UPDATE, id), request);
	}

	/**
	 * It is wrapper of fetchAll with parameter here sending null defines fetchAll
	 * with a default values without filteration
	 * @throws RazorpayException
	 */
	public List<FundAccount> fetchAll() throws RazorpayException {
		return fetchAll(null);
	}

	/**
	 * This method get list of fund account filtered by parameters @request
	 * @throws RazorpayException
	 */
	public List<FundAccount> fetchAll(JSONObject request) throws RazorpayException {
		return getCollection(Constants.FUND_ACCOUNT_FETCH_ALL, request);
	}
}