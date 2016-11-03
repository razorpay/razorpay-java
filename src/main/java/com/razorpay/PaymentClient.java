package com.razorpay;

import java.util.List;

import org.json.JSONObject;

import okhttp3.Response;

public class PaymentClient {

  private static PaymentClient paymentClient = null;

  static PaymentClient getInstance() {
    if (paymentClient == null) {
      paymentClient = new PaymentClient();
    }
    return paymentClient;
  }

  private PaymentClient() {

  };

  public Payment fetch(String id) throws RazorpayException {
    Response response = ApiUtils.getRequest(String.format(Constants.PAYMENT_GET, id), null);
    return Utils.processResponse(response);
  }

  public List<Payment> fetchAll(JSONObject request) throws RazorpayException {
    Response response = ApiUtils.getRequest(Constants.PAYMENT_LIST, request);
    return Utils.processCollectionResponse(response);
  }

  public List<Payment> fetchAll() throws RazorpayException {
    return fetchAll(null);
  }

  public Payment capture(String id, JSONObject request) throws RazorpayException {
    Response response = ApiUtils.postRequest(String.format(Constants.PAYMENT_CAPTURE, id), request);
    return Utils.processResponse(response);
  }

  public Refund refund(String id, JSONObject request) throws RazorpayException {
    Response response = ApiUtils.postRequest(String.format(Constants.PAYMENT_REFUND, id), request);
    return Utils.processResponse(response);
  }

  public Refund fetchRefund(String id, String refundId) throws RazorpayException {
    Response response =
        ApiUtils.getRequest(String.format(Constants.PAYMENT_REFUND_GET, id, refundId), null);
    return Utils.processResponse(response);
  }

  public List<Refund> fetchAllRefunds(String id, JSONObject request) throws RazorpayException {
    Response response =
        ApiUtils.getRequest(String.format(Constants.PAYMENT_REFUND_LIST, id), request);
    return Utils.processCollectionResponse(response);
  }

  public List<Refund> fetchAllRefunds(String id) throws RazorpayException {
    return fetchAllRefunds(id, null);
  }
}
