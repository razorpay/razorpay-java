package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class TransferClient extends ApiClient {

  TransferClient(String auth, String clientKey) {
    super(auth, clientKey);
  }

  public Transfer create(JSONObject request) throws RazorpayException {
    return post(Constants.TRANSFER_CREATE, request);
  }

  public Transfer edit(String id, JSONObject request) throws RazorpayException {
    return patch(String.format(Constants.TRANSFER_EDIT, id), request);
  }

  public Reversal reversal(String id, JSONObject request) throws RazorpayException {
    return post(String.format(Constants.TRANSFER_REVERSAL_CREATE, id), request);
  }

  public Transfer fetch(String id) throws RazorpayException {
    return get(String.format(Constants.TRANSFER_GET, id), null);
  }

  public List<Transfer> fetchAll() throws RazorpayException {
    return fetchAll(null);
  }

  public List<Transfer> fetchAll(JSONObject request) throws RazorpayException {
    return getCollection(Constants.TRANSFER_LIST, request);
  }
}
