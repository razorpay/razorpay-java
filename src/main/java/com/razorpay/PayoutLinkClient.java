package com.razorpay;

import org.json.JSONObject;

import java.util.List;

public class PayoutLinkClient extends ApiClient {

    PayoutLinkClient(String auth) {
        super(auth);
    }

    public PayoutLink create(JSONObject request) throws RazorpayException {
        return post(Constants.PAYOUT_LINK_CREATE, request);
    }

    public PayoutLink fetch(String id) throws RazorpayException {
        return get(String.format(Constants.PAYOUT_LINK_FETCH, id), null);
    }

    public PayoutLink cancel(String id, JSONObject request) throws RazorpayException {
        return post(String.format(Constants.PAYOUT_LINK_CANCEL, id), request);
    }

    /**
     * It is wrapper of fetchAll with parameter here sending null defines fetchAll
     * with a default values without filteration
     * @throws RazorpayException
     */
    public List<PayoutLink> fetchAll() throws RazorpayException {
        return fetchAll(null);
    }

    /**
     * This method get list of payout link filtered by parameters @request
     * @throws RazorpayException
     */
    public List<PayoutLink> fetchAll(JSONObject request) throws RazorpayException {
        return getCollection(Constants.PAYOUT_LINK_FETCH_ALL, request);
    }
}
