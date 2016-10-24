package com.razorpay;

import java.util.List;

import org.json.JSONObject;

import okhttp3.Response;

public class Payment extends Entity {

  public Payment(JSONObject jsonObject) {
    super(jsonObject);
  }

  public Payment capture(JSONObject request) throws RazorpayException {
    Response response =
        ApiUtils.postRequest(String.format(Constants.PAYMENT_CAPTURE, get(Constants.ID)), request);
    return Utils.processResponse(response);
  }

  public Refund refund(JSONObject request) throws RazorpayException {
    Response response =
        ApiUtils.postRequest(String.format(Constants.PAYMENT_REFUND, get(Constants.ID)), request);
    return Utils.processResponse(response);
  }

  public Refund fetchRefund(String refundId) throws RazorpayException {
    Response response = ApiUtils
        .getRequest(String.format(Constants.PAYMENT_REFUND_GET, get(Constants.ID), refundId), null);
    return Utils.processResponse(response);
  }

  public List<Refund> fetchAllRefunds(JSONObject request) throws RazorpayException {
    Response response = ApiUtils
        .getRequest(String.format(Constants.PAYMENT_REFUND_LIST, get(Constants.ID)), request);
    return Utils.processCollectionResponse(response);
  }

  public List<Refund> fetchAllRefunds() throws RazorpayException {
    return fetchAllRefunds(null);
  }
}
