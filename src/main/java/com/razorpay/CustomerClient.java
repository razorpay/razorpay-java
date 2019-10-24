package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class CustomerClient extends ApiClient {

  CustomerClient(String auth, String clientKey) {
    super(auth, clientKey);
  }

  public Customer create(JSONObject request) throws RazorpayException {
    return post(Constants.CUSTOMER_CREATE, request);
  }

  public Customer fetch(String id) throws RazorpayException {
    return get(String.format(Constants.CUSTOMER_GET, id), null);
  }

  public Customer edit(String id, JSONObject request) throws RazorpayException {
    return put(String.format(Constants.CUSTOMER_EDIT, id), request);
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
