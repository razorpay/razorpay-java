package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class RefundClient extends ApiClient {

  public RefundClient(String auth) {
    super(auth);
  }

  public Refund create(JSONObject request) throws RazorpayException {
    return post(Constants.REFUNDS, request);
  }

  public List<Refund> fetchAll(JSONObject request) throws RazorpayException {
    return getCollection(Constants.REFUNDS, request);
  }

  public Refund fetch(String id) throws RazorpayException {
    return get(String.format(Constants.REFUND, id), null);
  }

  public List<Refund> fetchAll() throws RazorpayException {
    return fetchAll(null);
  }

  public List<Refund> fetchMultipleRefund(String id) throws RazorpayException {
    return fetchMultipleRefund(id,null);
  }

  public List<Refund> fetchMultipleRefund(String id,JSONObject request) throws RazorpayException {
    return getCollection(String.format(Constants.REFUND_MULTIPLE, id), request);
  }

  public Refund edit(String id, JSONObject request) throws RazorpayException {
    return patch(String.format(Constants.REFUND, id), request);
  }
}
