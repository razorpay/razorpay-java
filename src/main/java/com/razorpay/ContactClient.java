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
        return get(String.format(Constants.CONTACT_GET, id), null);
    }

    public Contact edit(String id, JSONObject request) throws RazorpayException {
        return put(String.format(Constants. CONTACT_EDIT, id), request);
    }
    public Contact update(String id, JSONObject request) throws RazorpayException {
        return put(String.format(Constants. CONTACT_UPDATE, id), request);
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
        return getCollection(Constants.CONTACT_LIST, request);
    }

    public List<Token> fetchTokens(String id) throws RazorpayException {
        return getCollection(String.format(Constants.TOKEN_LIST, id), null);
    }

    public Token fetchToken(String id, String tokenId) throws RazorpayException {
        return get(String.format(Constants.TOKEN_GET, id, tokenId), null);
    }

    public void deleteToken(String id, String tokenId) throws RazorpayException {
        delete(String.format(Constants.TOKEN_DELETE, id, tokenId), null);
    }
}
