package com.razorpay;

import org.json.JSONObject;

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
}
