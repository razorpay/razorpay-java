package com.razorpay;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class SettlementClient extends ApiClient {

    SettlementClient(String auth, ApiUtils apiUtils) {
        super(auth,apiUtils);
    }

    /**
     * It is wrapper of fetchAll with parameter here sending null defines fetchAll
     * with a default values without filteration
     * @throws RazorpayException
     */
    public List<Settlement> fetchAll() throws RazorpayException, JSONException, IOException, URISyntaxException {
        return fetchAll(null);
    }

    /**
     * This method get list of Settlements filtered by parameters @request
     * @throws RazorpayException
     */
    public List<Settlement> fetchAll(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return getCollection(Constants.SETTLEMENTS, request);
    }

    public Settlement fetch(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return get(String.format(Constants.SETTLEMENT, id), null);
    }

    public List<Settlement> reports(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return getCollection(Constants.SETTLEMENTS_REPORTS, request);
    }

    public List<Settlement> reports() throws RazorpayException, JSONException, IOException, URISyntaxException {
        return reports(null);
    }

    public Settlement create(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return post(Constants.SETTLEMENTS_INSTANT, request);
    }

    /**
     * It is wrapper of fetchAllDemand with parameter here sending null defines fetchAllDemand
     * with a default values without filteration
     * @throws RazorpayException
     */
    public List<Settlement> fetchAllDemand() throws RazorpayException, JSONException, IOException, URISyntaxException {
        return fetchAllDemand(null);
    }

    /**
     * This method get list of demand Settlements filtered by parameters @request
     * @throws RazorpayException
     */
    public List<Settlement> fetchAllDemand(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return getCollection(Constants.SETTLEMENTS_INSTANT, request);
    }

    public Settlement fetchDemandSettlement(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return get(String.format(Constants.SETTLEMENT_INSTANT, id), null);
    }
}
