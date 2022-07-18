package com.razorpay;

import org.json.JSONObject;

import java.util.List;

public class TransactionsClient extends ApiClient {

    TransactionsClient(String auth) {
        super(auth);
    }

    public PayoutLink fetch(String id) throws RazorpayException {
        return get(String.format(Constants.TRANSACTIONS_FETCH, id), null);
    }

    /**
     * It is wrapper of fetchAll with parameter here sending null defines fetchAll
     * with a default values without filteration
     * @throws RazorpayException
     */
    public List<Transactions> fetchAll() throws RazorpayException {
        return fetchAll(null);
    }

    /**
     * This method get list of transactions filtered by parameters @request
     * @throws RazorpayException
     */
    public List<Transactions> fetchAll(JSONObject request) throws RazorpayException {
        return getCollection(Constants.TRANSACTIONS_FETCH_ALL, request);
    }
}
