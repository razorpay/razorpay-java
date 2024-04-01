package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class DisputeClient extends ApiClient {

    DisputeClient(String auth) {
        super(auth);
    }

    public Dispute fetch(String id) throws RazorpayException {
        return get(Constants.VERSION, String.format(Constants.DISPUTE_FETCH, id), null);
    }

    /**
     * It is wrapper of fetchAll with parameter here sending null defines fetchAll
     * with a default values without filteration
     * @throws RazorpayException
     */
    public List<Dispute> fetchAll() throws RazorpayException {
        return fetchAll(null);
    }

    /**
     * This method get list of disputes filtered by parameters @request
     * @throws RazorpayException
     */
    public List<Dispute> fetchAll(JSONObject request) throws RazorpayException {
        return getCollection(Constants.VERSION, Constants.DISPUTE, request);
    }

    public Dispute accept(String id) throws RazorpayException {
        return post(Constants.VERSION, String.format(Constants.DISPUTE_ACCEPT, id), null);
    }

    public Dispute contest(String id, JSONObject request) throws RazorpayException {
        return patch(Constants.VERSION, String.format(Constants.DISPUTE_CONTEST, id), request);
    }
}
