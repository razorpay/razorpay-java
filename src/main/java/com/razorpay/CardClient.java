package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class CardClient extends ApiClient {

  CardClient(String auth) {
    super(auth);
  }

  public Card fetch(String id) throws RazorpayException {
    return get(String.format(Constants.CARD_GET, id), null);
  }
}
