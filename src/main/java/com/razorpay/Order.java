package com.razorpay;

import java.util.List;

import org.json.JSONObject;

import okhttp3.Response;

public class Order extends Entity {

  public Order(JSONObject jsonObject) {
    super(jsonObject);
  }

  public List<Payment> fetchPayments() throws RazorpayException {
    Response response =
        ApiUtils.getRequest(String.format(Constants.ORDER_PAYMENT_LIST, get(Constants.ID)), null);
    return Utils.processCollectionResponse(response);
  }
}
