package com.razorpay;

import org.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;

public class CardClient extends ApiClient {

  CardClient(String auth, ApiUtils apiUtils) {
    super(auth,apiUtils);
  }

  public Card fetch(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
    return get(String.format(Constants.CARD_GET, id), null);
  }

  public Card fetchCardDetails(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
    return get(String.format(Constants.FETCH_CARD_DETAILS, id), null);
  }
}
