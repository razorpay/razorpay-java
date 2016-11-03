package com.razorpay;

import java.util.List;

import org.json.JSONObject;

import okhttp3.Response;

public class RefundClient {

  private static RefundClient refundClient = null;

  static RefundClient getInstance() {
    if (refundClient == null) {
      refundClient = new RefundClient();
    }
    return refundClient;
  }

  private RefundClient() {

  };

  public List<Refund> fetchAll(JSONObject request) throws RazorpayException {
    Response response = ApiUtils.getRequest(Constants.REFUND_LIST, request);
    return Utils.processCollectionResponse(response);
  }

  public Refund fetch(String id) throws RazorpayException {
    Response response = ApiUtils.getRequest(String.format(Constants.REFUND_GET, id), null);
    return Utils.processResponse(response);
  }

  public List<Refund> fetchAll() throws RazorpayException {
    return fetchAll(null);
  }
}
