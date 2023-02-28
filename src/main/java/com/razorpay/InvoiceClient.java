package com.razorpay;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class InvoiceClient extends ApiClient {

  InvoiceClient(String auth, ApiUtils apiUtils) {
    super(auth,apiUtils);
  }

  public Invoice create(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
    return post(Constants.INVOICE_CREATE, request);
  }

  public List<Invoice> fetchAll() throws RazorpayException, JSONException, IOException, URISyntaxException {
    return fetchAll(null);
  }

  public List<Invoice> fetchAll(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
    return getCollection(Constants.INVOICE_LIST, request);
  }

  public Invoice fetch(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
    return get(String.format(Constants.INVOICE_GET, id), null);
  }

  public Invoice cancel(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
    return post(String.format(Constants.INVOICE_CANCEL, id), null);
  }

  public Invoice notifyBy(String id, String medium) throws RazorpayException, JSONException, IOException, URISyntaxException {
    return post(String.format(Constants.INVOICE_NOTIFY, id, medium), null);
  }
  public Invoice issue(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
    return post(String.format(Constants.INVOICE_ISSUE, id), null);
  }

  public Invoice edit(String id, JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
    return patch(String.format(Constants.INVOICE_GET, id), request);
  }

  public List<Invoice> delete(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
    return delete(String.format(Constants.INVOICE_GET, id), null);
  }
}
