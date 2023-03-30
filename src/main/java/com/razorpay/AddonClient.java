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
  
  /**
   * It is wrapper of fetchAll with parameter here sending null defines fetchAll
   * with a default values without filteration
   * @throws RazorpayException RazorpayException
   * @return List of AddOns
   */
  public List<Addon> fetchAll() throws RazorpayException {
      return fetchAll(null);
    }
  
  /**
   * This method get list of Addons filtered by parameters @request
   * @throws RazorpayException RazorpayException
   * @param request JSONObject request
   * @return List of AddOns
   */  
  public List<Addon> fetchAll(JSONObject request) throws RazorpayException {
      return getCollection(Constants.ADDON_LIST, request);
    }

  public List<Addon> delete(String id) throws RazorpayException {
    return delete(String.format(Constants.ADDON_DELETE, id), null);
  }
}