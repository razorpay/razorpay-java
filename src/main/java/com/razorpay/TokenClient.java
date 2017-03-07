package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class TokenClient extends ApiClient {

  TokenClient(String auth) {
    super(auth);
  }

  public List<Token> fetchAll(String customerId) throws RazorpayException {
    return getCollection(String.format(Constants.TOKEN_LIST, customerId), null);
  }

  public Token fetch(String id, String customerId) throws RazorpayException {
    return get(String.format(Constants.TOKEN_GET, customerId, id), null);
  }
}
