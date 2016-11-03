package com.razorpay;

import java.util.List;

import org.json.JSONObject;

import okhttp3.Response;

public class OrderClient {

  private static OrderClient orderClient = null;

  static OrderClient getInstance() {
    if (orderClient == null) {
      orderClient = new OrderClient();
    }
    return orderClient;
  }

  private OrderClient() {

  };

  public Order create(JSONObject request) throws RazorpayException {
    Response response = ApiUtils.postRequest(Constants.ORDER_CREATE, request);
    return Utils.processResponse(response);
  }

  public List<Order> fetchAll(JSONObject request) throws RazorpayException {
    Response response = ApiUtils.getRequest(Constants.ORDER_LIST, request);
    return Utils.processCollectionResponse(response);
  }

  public Order fetch(String id) throws RazorpayException {
    Response response = ApiUtils.getRequest(String.format(Constants.ORDER_GET, id), null);
    return Utils.processResponse(response);
  }

  public List<Payment> fetchPayments(String id) throws RazorpayException {
    Response response = ApiUtils.getRequest(String.format(Constants.ORDER_PAYMENT_LIST, id), null);
    return Utils.processCollectionResponse(response);
  }
}
