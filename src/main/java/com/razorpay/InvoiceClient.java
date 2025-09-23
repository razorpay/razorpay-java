package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class InvoiceClient extends ApiClient {

  InvoiceClient(String auth) {
    super(auth);
  }

  public Invoice create(JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, Constants.INVOICE_CREATE, request);
  }

  public List<Invoice> fetchAll() throws RazorpayException {
    return fetchAll(null);
  }

  public List<Invoice> fetchAll(JSONObject request) throws RazorpayException {
    return getCollection(Constants.VERSION, Constants.INVOICE_LIST, request);
  }

  public Invoice fetch(String id) throws RazorpayException {
    return get(Constants.VERSION, String.format(Constants.INVOICE_GET, id), null);
  }

  public Invoice cancel(String id) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.INVOICE_CANCEL, id), null);
  }

  public Invoice notifyBy(String id, String medium) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.INVOICE_NOTIFY, id, medium), null);
  }

  public Invoice createRegistrationLink(JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, Constants.SUBSCRIPTION_REGISTRATION_LINK, request);
  }

  public Invoice issue(String id) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.INVOICE_ISSUE, id), null);
  }

  public Invoice edit(String id, JSONObject request) throws RazorpayException {
    return patch(Constants.VERSION, String.format(Constants.INVOICE_GET, id), request);
  }

  public List<Invoice> delete(String id) throws RazorpayException {
    return delete(Constants.VERSION, String.format(Constants.INVOICE_GET, id), null);
  }
}
