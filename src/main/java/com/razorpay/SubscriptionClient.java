package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class SubscriptionClient extends ApiClient {

  SubscriptionClient(String auth) {
    super(auth);
  }

  public Subscription create(JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, Constants.SUBSCRIPTION_CREATE, request);
  }

  public Subscription fetch(String id) throws RazorpayException {
    return get(Constants.VERSION, String.format(Constants.SUBSCRIPTION, id), null);
  }

  public List<Subscription> fetchAll() throws RazorpayException {
    return fetchAll(null);
  }

  public List<Subscription> fetchAll(JSONObject request) throws RazorpayException {
    return getCollection(Constants.VERSION, Constants.SUBSCRIPTION_LIST, request);
  }

  public Subscription cancel(String id) throws RazorpayException {
    return cancel(id, null);
  }

  public Subscription cancel(String id, JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.SUBSCRIPTION_CANCEL, id), request);
  }

  public Addon createAddon(String id, JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.SUBSCRIPTION_ADDON_CREATE, id), request);
  }

  public Subscription update(String id, JSONObject request) throws RazorpayException {
    return patch(Constants.VERSION, String.format(Constants.SUBSCRIPTION, id), request);
  }

  public Subscription fetchPendingUpdate(String id) throws RazorpayException {
    return get(Constants.VERSION, String.format(Constants.SUBSCRIPTION_PENDING_UPDATE, id), null);
  }

  public Subscription cancelPendingUpdate(String id) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.SUBSCRIPTION_CANCEL_SCHEDULED_UPDATE, id), null);
  }

  public Subscription pause(String id, JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.PAUSE_SUBSCRIPTION, id), request);
  }

  public Subscription resume(String id, JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.RESUME_SUBSCRIPTION, id), request);
  }

  public Subscription deleteSubscriptionOffer(String subId, String offerId) throws RazorpayException {
    return delete(Constants.VERSION, String.format(Constants.SUBSCRIPTION_OFFER, subId, offerId), null);
  }
}
