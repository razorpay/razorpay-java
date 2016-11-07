package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class PaymentClient extends ApiClient {

  PaymentClient(String auth) {
    super(auth);
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
    return post(String.format(Constants.PAYMENT_REFUND, id), request);
  }

  public Refund fetchRefund(String id, String refundId) throws RazorpayException {
    return get(String.format(Constants.PAYMENT_REFUND_GET, id, refundId), null);
  }

  public List<Refund> fetchAllRefunds(String id, JSONObject request) throws RazorpayException {
    return getCollection(String.format(Constants.PAYMENT_REFUND_LIST, id), request);
  }

  public List<Refund> fetchAllRefunds(String id) throws RazorpayException {
    return fetchAllRefunds(id, null);
  }
}
