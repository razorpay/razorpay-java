package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class TransferClient extends ApiClient {

  TransferClient(String auth) {
    super(auth);
  }

  public Transfer create(JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, Constants.TRANSFER_CREATE, request);
  }

  public Transfer edit(String id, JSONObject request) throws RazorpayException {
    return patch(Constants.VERSION, String.format(Constants.TRANSFER_EDIT, id), request);
  }

  public Reversal reversal(String id, JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.TRANSFER_REVERSAL_CREATE, id), request);
  }

  public Transfer fetch(String id) throws RazorpayException {
    return get(Constants.VERSION, String.format(Constants.TRANSFER_GET, id), null);
  }

  public List<Reversal> fetchReversal(String id) throws RazorpayException {
    return getCollection(Constants.VERSION, String.format(Constants.TRANSFER_REVERSAL_CREATE, id), null);
  }

  public List<Transfer> fetchAll() throws RazorpayException {
    return fetchAll(null);
  }

  public List<Transfer> fetchAll(JSONObject request) throws RazorpayException {
    return getCollection(Constants.VERSION, Constants.TRANSFER_LIST, request);
  }
}
