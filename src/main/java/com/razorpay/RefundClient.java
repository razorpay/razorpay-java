package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class RefundClient extends ApiClient {

  RefundClient(String auth) {
    super(auth);
  }

  public Refunc create(JSONObject request) throws RazorpayException {
    return post(Constants.REFUND_CREATE, request);
  }

  public List<Refund> fetchAll(JSONObject request) throws RazorpayException {
    return getCollection(Constants.REFUND_LIST, request);
  }

  public Refund fetch(String id) throws RazorpayException {
    return get(String.format(Constants.REFUND_GET, id), null);
  }

  public List<Refund> fetchAll() throws RazorpayException {
    return fetchAll(null);
  }
}
