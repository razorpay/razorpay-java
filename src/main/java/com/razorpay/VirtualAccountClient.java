package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class VirtualAccountClient extends ApiClient {

  VirtualAccountClient(String auth) {
    super(auth);
  }

  public VirtualAccount create(JSONObject request) throws RazorpayException {
    return post(Constants.VIRTUAL_ACCOUNT_CREATE, request);
  }

  public VirtualAccount fetch(String id) throws RazorpayException {
    return get(String.format(Constants.VIRTUAL_ACCOUNT_GET, id), null);
  }

  public List<VirtualAccount> fetchAll() throws RazorpayException {
    return fetchAll(null);
  }

  public List<VirtualAccount> fetchAll(JSONObject request) throws RazorpayException {
    return getCollection(Constants.VIRTUAL_ACCOUNT_LIST, request);
  }

  public VirtualAccount edit(String id, JSONObject request) throws RazorpayException {
    return patch(String.format(Constants.VIRTUAL_ACCOUNT_EDIT, id), request);
  }

  public VirtualAccount close(String id) throws RazorpayException {
    return post(String.format(Constants.VIRTUAL_ACCOUNT_CLOSE, id));
  }

  public List<Payment> fetchPayments(String id) throws RazorpayException {
    return fetchPayments(id, null);
  }

  public List<Payment> fetchPayments(String id, JSONObject request) throws RazorpayException {
    return getCollection(String.format(Constants.VIRTUAL_ACCOUNT_PAYMENTS, id), request);
  }
}
