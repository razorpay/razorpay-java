package com.razorpay;

import java.util.List;

import org.json.JSONObject;

public class AddonClient extends ApiClient {

  AddonClient(String auth) {
    super(auth);
  }

  // To create an Addon, use the createAddon method of SubscriptionClient
  public Addon fetch(String id) throws RazorpayException {
    return get(String.format(Constants.ADDON_GET, id), null);
  }
  
  
  public List<Addon> fetchAll() throws RazorpayException {
      return fetchAll(null);
    }
  
  public List<Addon> fetchAll(JSONObject request) throws RazorpayException {
      return getCollection(Constants.ADDON_LIST, request);
    }

  public void delete(String id) throws RazorpayException {
    delete(String.format(Constants.ADDON_DELETE, id), null);
  }
}