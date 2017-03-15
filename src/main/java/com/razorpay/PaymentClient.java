package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class PaymentClient extends ApiClient {

  private RefundClient refundClient;

  PaymentClient(String auth) {
    super(auth);
    refundClient = new RefundClient(auth);
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

  /**
   * @deprecated As of release 1.2.0, replaced by {@link #refund(JSONObject)}
   */
  @Deprecated
  public Refund refund(String id) throws RazorpayException {
    return refund(id, null);
  }

  /**
   * @deprecated As of release 1.2.0, replaced by {@link #refund(JSONObject)}
   */
  @Deprecated
  public Refund refund(String id, JSONObject request) throws RazorpayException {
    return post(String.format(Constants.PAYMENT_REFUND, id), request);
  }

  public Refund refund(JSONObject request) throws RazorpayException {
    return refundClient.create(request);
  }

  /**
   * @deprecated As of release 1.2.0, replaced by {@link #fetchRefund(String)}
   */
  @Deprecated
  public Refund fetchRefund(String id, String refundId) throws RazorpayException {
    return get(String.format(Constants.PAYMENT_REFUND_GET, id, refundId), null);
  }

  public Refund fetchRefund(String refundId) throws RazorpayException {
    return refundClient.fetch(refundId);
  }

  /**
   * @deprecated As of release 1.2.0, replaced by {@link #fetchAllRefunds(JSONObject)}
   */
  @Deprecated
  public List<Refund> fetchAllRefunds(String id, JSONObject request) throws RazorpayException {
    return getCollection(String.format(Constants.PAYMENT_REFUND_LIST, id), request);
  }

  /**
   * @deprecated As of release 1.2.0, replaced by {@link #fetchAllRefunds(JSONObject)}
   */
  @Deprecated
  public List<Refund> fetchAllRefunds(String id) throws RazorpayException {
    return fetchAllRefunds(id, null);
  }

  public List<Refund> fetchAllRefunds(JSONObject request) throws RazorpayException {
    return refundClient.fetchAll(request);
  }
}
