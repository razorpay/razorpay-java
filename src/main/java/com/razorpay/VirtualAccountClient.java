package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class VirtualAccountClient extends ApiClient {

  VirtualAccountClient(String auth) {
    super(auth);
  }

  public VirtualAccount create(JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, Constants.VIRTUAL_ACCOUNT_CREATE, request);
  }

  public VirtualAccount fetch(String id) throws RazorpayException {
    return get(Constants.VERSION, String.format(Constants.VIRTUAL_ACCOUNT_GET, id), null);
  }

  public List<VirtualAccount> fetchAll() throws RazorpayException {
    return fetchAll(null);
  }

  public List<VirtualAccount> fetchAll(JSONObject request) throws RazorpayException {
    return getCollection(Constants.VERSION, Constants.VIRTUAL_ACCOUNT_LIST, request);
  }

  public VirtualAccount edit(String id, JSONObject request) throws RazorpayException {
    return patch(Constants.VERSION, String.format(Constants.VIRTUAL_ACCOUNT_EDIT, id), request);
  }

  public VirtualAccount close(String id) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.VIRTUAL_ACCOUNT_CLOSE, id), null);
  }

  public List<Payment> fetchPayments(String id) throws RazorpayException {
    return fetchPayments(id, null);
  }

  public List<Payment> fetchPayments(String id, JSONObject request) throws RazorpayException {
    return getCollection(Constants.VERSION, String.format(Constants.VIRTUAL_ACCOUNT_PAYMENTS, id), request);
  }

  public VirtualAccount addReceiver(String id, JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.VIRTUAL_ACCOUNT_RECEIVERS, id), request);
  }

  public VirtualAccount addAllowedPayers(String id, JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.VIRTUAL_ACCOUNT_ALLOWEDPAYERS, id), request);
  }

  public VirtualAccount deleteAllowedPayer(String virtual_id, String payer_id) throws RazorpayException {
      return delete(Constants.VERSION, String.format(Constants.VIRTUAL_ACCOUNT_DELETE_ALLOWEDPAYERS, virtual_id, payer_id), null);
  }
}
