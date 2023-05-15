package com.razorpay;

import org.json.JSONObject;

public class CardClient extends ApiClient {

  CardClient(String auth) {
    super(auth);
  }

  public Card fetch(String id) throws RazorpayException {
    return get(Constants.VERSION, String.format(Constants.CARD_GET, id), null);
  }

  public Card fetchCardDetails(String id) throws RazorpayException{
    return get(Constants.VERSION, String.format(Constants.FETCH_CARD_DETAILS, id), null);
  }

  public Card requestCardReference(JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, Constants.CARD_REQUEST_REFERENCE,request);
  }
}
