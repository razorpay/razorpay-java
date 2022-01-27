package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class InvoiceClient extends ApiClient {

  InvoiceClient(String auth, String clientKey) {
    super(auth, clientKey);
  }

  public Invoice create(JSONObject request) throws RazorpayException {
    return post(Constants.INVOICE_CREATE, request);
  }

  public List<Invoice> fetchAll() throws RazorpayException {
    return fetchAll(null);
  }

  public List<Invoice> fetchAll(JSONObject request) throws RazorpayException {
    return getCollection(Constants.INVOICE_LIST, request);
  }

  public Invoice fetch(String id) throws RazorpayException {
    return get(String.format(Constants.INVOICE_GET, id), null);
  }

  public Invoice cancel(String id) throws RazorpayException {
    return post(String.format(Constants.INVOICE_CANCEL, id), null);
  }
}
