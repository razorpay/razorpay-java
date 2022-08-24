package com.razorpay;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class AddonClient extends ApiClient {

  AddonClient(String auth, ApiUtils apiUtils) {
    super(auth,apiUtils);
  }

  // To create an Addon, use the createAddon method of SubscriptionClient
  public Addon fetch(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
    return get(String.format(Constants.ADDON_GET, id), null);
  }
  
  /**
   * It is wrapper of fetchAll with parameter here sending null defines fetchAll
   * with a default values without filteration
   * @throws RazorpayException
   */
  public List<Addon> fetchAll() throws RazorpayException, JSONException, IOException, URISyntaxException {
      return fetchAll(null);
    }
  
  /**
   * This method get list of Addons filtered by parameters @request
   * @throws RazorpayException
   */  
  public List<Addon> fetchAll(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
      return getCollection(Constants.ADDON_LIST, request);
    }

  public List<Addon> delete(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
    return delete(String.format(Constants.ADDON_DELETE, id), null);
  }
}