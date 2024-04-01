package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class OrderClient extends ApiClient {

  OrderClient(String auth) {
    super(auth);
  }

  public Order create(JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, Constants.ORDER_CREATE, request);
  }

  public List<Order> fetchAll() throws RazorpayException {
    return fetchAll(null);
  }

  public List<Order> fetchAll(JSONObject request) throws RazorpayException {
    return getCollection(Constants.VERSION, Constants.ORDER_LIST, request);
  }

  public Order fetch(String id) throws RazorpayException {
    return get(Constants.VERSION, String.format(Constants.ORDER_GET, id), null);
  }

  public List<Payment> fetchPayments(String id) throws RazorpayException {
    return getCollection(Constants.VERSION, String.format(Constants.ORDER_PAYMENT_LIST, id), null);
  }

  public Order edit(String id, JSONObject request) throws RazorpayException {
    return patch(Constants.VERSION, String.format(Constants.ORDER_EDIT, id), request);
  }

  public Order viewRtoReview(String id) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.VIEW_RTO, id), null);
  }

  public Order editFulfillment(String id, JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.FULFILLMENT, id), request);
  }
}
