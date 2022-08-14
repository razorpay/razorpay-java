package com.razorpay;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class TransferClient extends ApiClient {

  TransferClient(String auth, ApiUtils apiUtils) {
    super(auth,apiUtils);
  }

  public Transfer create(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
    return post(Constants.TRANSFER_CREATE, request);
  }

  public Transfer edit(String id, JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
    return patch(String.format(Constants.TRANSFER_EDIT, id), request);
  }

  public Reversal reversal(String id, JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
    return post(String.format(Constants.TRANSFER_REVERSAL_CREATE, id), request);
  }

  public Transfer fetch(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
    return get(String.format(Constants.TRANSFER_GET, id), null);
  }

  public List<Transfer> fetchAll() throws RazorpayException, JSONException, IOException, URISyntaxException {
    return fetchAll(null);
  }

  public List<Transfer> fetchAll(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
    return getCollection(Constants.TRANSFER_LIST, request);
  }
}
