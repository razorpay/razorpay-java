package com.razorpay;

import org.json.JSONObject;

import java.util.List;

public class StakeholderClient extends ApiClient {

    StakeholderClient(String auth) {
        super(auth);
    }

    public Stakeholder create(String id, JSONObject request) throws RazorpayException {
        return post(Constants.VERSION_V2, String.format(Constants.STAKEHOLDER_CREATE, id), request);
    }

    public Stakeholder fetch(String id, String stakeholder_id) throws RazorpayException {
        return get(Constants.VERSION_V2, String.format(Constants.STAKEHOLDER_FETCH, id, stakeholder_id), null);
    }

    public List<Stakeholder> fetchAll(String id) throws RazorpayException {
        return getCollection(Constants.VERSION_V2, String.format(Constants.STAKEHOLDER_FETCH_ALL, id), null);
    }

    public Stakeholder edit(String id, String stakeholder_id, JSONObject request) throws RazorpayException {
        return patch(Constants.VERSION_V2, String.format(Constants.STAKEHOLDER_FETCH, id, stakeholder_id), request);
    }

    public Account uploadStakeholderDoc(String id, String stakeholder_id, JSONObject request) throws RazorpayException {
        return post(Constants.VERSION_V2, String.format(Constants.UPLOAD_STAKEHOLDER_DOCUMENT, id, stakeholder_id), request);
    }

    public Account fetchStakeholderDoc(String id, String stakeholder_id) throws RazorpayException {
        return get(Constants.VERSION_V2, String.format(Constants.UPLOAD_STAKEHOLDER_DOCUMENT, id, stakeholder_id), null);
    }
}
