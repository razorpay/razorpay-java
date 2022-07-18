package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class PayoutClient extends ApiClient {

    PayoutClient(String auth) {
        super(auth);
    }

    public Payout create(JSONObject request) throws RazorpayException {
        return post(Constants.PAYOUT_CREATE, request);
    }

    public Payout fetch(String id) throws RazorpayException {
        return get(String.format(Constants.PAYOUT_FETCH, id), null);
    }

    public Payout cancel(String id, JSONObject request) throws RazorpayException {
        return post(String.format(Constants.PAYOUT_CANCEL_QUEUED, id), request);
    }

    /**
     * It is wrapper of fetchAll with parameter here sending null defines fetchAll
     * with a default values without filteration
     * @throws RazorpayException
     */
    public List<Payout> fetchAll() throws RazorpayException {
        return fetchAll(null);
    }

    /**
     * This method get list of payouts filtered by parameters @request
     * @throws RazorpayException
     */
    public List<Payout> fetchAll(JSONObject request) throws RazorpayException {
        return getCollection(Constants.PAYOUT_FETCH_ALL, request);
    }
}
