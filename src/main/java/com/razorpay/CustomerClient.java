package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class CustomerClient extends ApiClient {

  CustomerClient(String auth) {
    super(auth);
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

  /**
   * It is wrapper of fetchAll with parameter here sending null defines fetchAll
   * with a default values without filteration
   * @throws RazorpayException RazorpayException
   * @return list of customers
   */
  public List<Customer> fetchAll() throws RazorpayException {
    return fetchAll(null);
  }

  /**
   * This method get list of customers filtered by parameters @request
   * @throws RazorpayException RazorpayException
   * @param request JSONObject request
   * @return list of customers
   */
  public List<Customer> fetchAll(JSONObject request) throws RazorpayException {
    return getCollection(Constants.CUSTOMER_LIST, request);
  }

  public List<Token> fetchTokens(String id) throws RazorpayException {
    return getCollection(String.format(Constants.TOKEN_LIST, id), null);
  }

  public Token fetchToken(String id, String tokenId) throws RazorpayException {
    return get(String.format(Constants.TOKEN_GET, id, tokenId), null);
  }

  public Customer deleteToken(String id, String tokenId) throws RazorpayException {
    return delete(String.format(Constants.TOKEN_DELETE, id, tokenId), null);
  }
}
