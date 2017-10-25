package com.razorpay;

import org.json.JSONObject;

import java.util.List;

public class SubscriptionClient extends ApiClient {

  SubscriptionClient(String auth) {
    super(auth);
  }

  public Subscription create(JSONObject request) throws RazorpayException {
    return post(Constants.SUBSCRIPTION_CREATE, request);
  }

  public Subscription fetch(String id) throws RazorpayException {
    return get(String.format(Constants.SUBSCRIPTION_GET, id), null);
  }

  public List<Subscription> fetchAll() throws RazorpayException {
    return fetchAll(null);
  }

  public List<Subscription> fetchAll(JSONObject request) throws RazorpayException {
    return getCollection(Constants.SUBSCRIPTION_LIST, request);
  }

  public Subscription cancel(String id) throws RazorpayException {
    return post(String.format(Constants.SUBSCRIPTION_CANCEL, id), null);
  }

  public Addon createAddon(String id, JSONObject request) throws RazorpayException {
    return post(String.format(Constants.SUBSCRIPTION_ADDON_CREATE, id), request);
  }
}
