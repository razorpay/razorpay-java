package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class PaymentClient extends ApiClient {

  private String auth;

  PaymentClient(String auth) {
	super(auth);
	this.auth = auth;
  }

  public Payment fetch(String id) throws RazorpayException {
    return get(String.format(Constants.PAYMENT_GET, id), null);
  }

  public List<Payment> fetchAll(JSONObject request) throws RazorpayException {
    return getCollection(Constants.PAYMENT_LIST, request);
  }

  public List<Payment> fetchAll() throws RazorpayException {
    return fetchAll(null);
  }

  public Payment capture(String id, JSONObject request) throws RazorpayException {
    return post(String.format(Constants.PAYMENT_CAPTURE, id), request);
  }

  public Refund refund(String id) throws RazorpayException {
    return refund(id, null);
  }

  public Refund refund(String id, JSONObject request) throws RazorpayException {
    RefundClient refundClient = new RefundClient(auth);
    if (request == null) {
      request = new JSONObject();
    }
    request.put("payment_id", id);

    return refundClient.create(request);
  }

  public Refund fetchRefund(String id, String refundId) throws RazorpayException {
    return get(String.format(Constants.PAYMENT_REFUND_GET, id, refundId), null);
  }

  public List<Refund> fetchAllRefunds(String id, JSONObject request) throws RazorpayException {
    RefundClient refundClient = new RefundClient(this.auth);
    if (request == null) {
      request = new JSONObject();
    }
    request.put("payment_id", id);

    return refundClient.fetchAll(request);
  }

  public List<Refund> fetchAllRefunds(String id) throws RazorpayException {
    return fetchAllRefunds(id, null);
  }
}
