package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class SubscriptionClient extends ApiClient {

  SubscriptionClient(String auth) {
    super(auth);
  }

  public Subscription create(JSONObject request) throws RazorpayException {
    return post(Constants.SUBSCRIPTION_CREATE, request);
  }

  public Subscription fetch(String id) throws RazorpayException {
    return get(String.format(Constants.SUBSCRIPTION, id), null);
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

  public Subscription update(String id, JSONObject request) throws RazorpayException {
    return patch(String.format(Constants.SUBSCRIPTION, id), request);
  }

  public Subscription fetchPendingUpdate(String id) throws RazorpayException {
    return get(String.format(Constants.SUBSCRIPTION_PENDING_UPDATE, id), null);
  }

  public Subscription cancelPendingUpdate(String id) throws RazorpayException {
    return post(String.format(Constants.SUBSCRIPTION_CANCEL_SCHEDULED_UPDATE, id), null);
  }

  public Subscription pause(String id, JSONObject request) throws RazorpayException {
    return post(String.format(Constants.PAUSE_SUBSCRIPTION, id), request);
  }

  public Subscription resume(String id, JSONObject request) throws RazorpayException {
    return post(String.format(Constants.RESUME_SUBSCRIPTION, id), request);
  }

  public Subscription deleteSubscriptionOffer(String subId, String offerId) throws RazorpayException {
    return delete(String.format(Constants.SUBSCRIPTION_OFFER, subId, offerId), null);
  }
}
