package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class ContactClient extends ApiClient {

    ContactClient(String auth) {
        super(auth);
    }

    public Contact create(JSONObject request) throws RazorpayException {
        return post(Constants.CONTACT_CREATE, request);
    }

    public Contact fetch(String id) throws RazorpayException {
        return get(String.format(Constants.CONTACT_FETCH, id), null);
    }

    public Contact edit(String id, JSONObject request) throws RazorpayException {
        return patch(String.format(Constants. CONTACT_EDIT, id), request);
    }
    public Contact update(String id, JSONObject request) throws RazorpayException {
        return patch(String.format(Constants. CONTACT_UPDATE, id), request);
    }

    /**
     * It is wrapper of fetchAll with parameter here sending null defines fetchAll
     * with a default values without filteration
     * @throws RazorpayException
     */
    public List<Contact> fetchAll() throws RazorpayException {
        return fetchAll(null);
    }

    /**
     * This method get list of contacts filtered by parameters @request
     * @throws RazorpayException
     */
    public List<Contact> fetchAll(JSONObject request) throws RazorpayException {
        return getCollection(Constants.CONTACT_FETCH_ALL, request);
    }
}
