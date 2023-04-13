package com.razorpay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.HttpUrl;
import okhttp3.Response;

import static com.razorpay.Constants.INVALID_RESPONSE_FROM_SERVER;

class ApiClient {

  String auth;

  private static final String ENTITY = "entity";

  private static final String ERROR = "error";

  private static final String DESCRIPTION = "description";

  private static final String STATUS_CODE = "code";

  private static final int STATUS_OK = 200;

  private static final int STATUS_MULTIPLE_CHOICE = 300;

  ApiClient(String auth) {
    this.auth = auth;
  }

  public <T extends Entity> T get(String path, JSONObject requestObject) throws RazorpayException {
    Response response = ApiUtils.getRequest(path, requestObject, auth);
    return processResponse(response);
  }

  public <T extends Entity> T post(String path, JSONObject requestObject) throws RazorpayException {
    Response response = ApiUtils.postRequest(path, requestObject, auth);
    return processResponse(response);
  }

  public <T extends Entity> T put(String path, JSONObject requestObject) throws RazorpayException {
    Response response = ApiUtils.putRequest(path, requestObject, auth);
    return processResponse(response);
  }

  public <T extends Entity> T patch(String path, JSONObject requestObject) throws RazorpayException {
    Response response = ApiUtils.patchRequest(path, requestObject, auth);
    return processResponse(response);
  }


  <T extends Entity> ArrayList<T> getCollection(String path, JSONObject requestObject)
          throws RazorpayException {
    Response response = ApiUtils.getRequest(path, requestObject, auth);
    return processCollectionResponse(response);
  }

  public <T> T delete(String path, JSONObject requestObject) throws RazorpayException {
    Response response = ApiUtils.deleteRequest(path, requestObject, auth);
    return processDeleteResponse(response);
  }

  private <T extends Object> T processDeleteResponse(Response response) throws RazorpayException {
    if (response == null) {
      throw new RazorpayException(INVALID_RESPONSE_FROM_SERVER);
    }

    int statusCode = response.code();
    String responseBody = null;
    JSONObject responseJson = null;

    try {
      responseBody = response.body().string();
      if(responseBody.equals("[]")){
        return (T) Collections.emptyList();
      }
      else if(response.code()==204){
        return null;
      }
      else{
        responseJson = new JSONObject(responseBody);
      }
    } catch (IOException e) {
      throw new RazorpayException(e.getMessage());
    }

    if (statusCode < STATUS_OK || statusCode >= STATUS_MULTIPLE_CHOICE) {
      throwException(statusCode, responseJson);
    }
    return (T) parseResponse(responseJson, getEntity(responseJson, response.request().url()));
  }

  private <T extends Entity> T parseResponse(JSONObject jsonObject, String entity) throws RazorpayException {
    if (entity != null) {
      Class<T> cls = (Class<T>) getClass(entity);
      if (cls != null) {
        try {
          return cls.getConstructor(JSONObject.class).newInstance(jsonObject);
        } catch (Exception e) {
          throw new RazorpayException("Unable to parse response because of " + e.getMessage());
        }
      }
    }
    throw new RazorpayException("Unable to parse response");
  }

  private <T extends Entity> ArrayList<T> parseCollectionResponse(JSONArray jsonArray, HttpUrl requestUrl)
      throws RazorpayException {

   ArrayList<T> modelList = new ArrayList<>();
    try {
      for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jsonObj = jsonArray.getJSONObject(i);
        T t = parseResponse(jsonObj, getEntity(jsonObj,requestUrl));
        modelList.add(t);
      }
      return modelList;
    } catch (RazorpayException e) {
      throw e;
    }
  }

  /*
   * this method will take http url as : https://api.razorpay.com/v1/invocies
   * and will return entity name with the help of @EntityNameURLMapping class
   */
  private String getEntityNameFromURL(HttpUrl url) {
    String param = url.pathSegments().get(1);
    return EntityNameURLMapping.getEntityName(param);
  }


  <T extends Entity> T processResponse(Response response) throws RazorpayException {
    if (response == null) {
      throw new RazorpayException(INVALID_RESPONSE_FROM_SERVER);
    }

    int statusCode = response.code();
    String responseBody = null;
    JSONObject responseJson = null;
    try {
      responseBody = response.body().string();
      responseJson = new JSONObject(responseBody);
    } catch (IOException e) {
      throw new RazorpayException(e.getMessage());
    }

    if (statusCode >= STATUS_OK && statusCode < STATUS_MULTIPLE_CHOICE) {
      return parseResponse(responseJson, getEntity(responseJson, response.request().url()));
    }

    throwException(statusCode, responseJson);
    return null;
  }

  <T extends Entity> ArrayList<T> processCollectionResponse(Response response)
          throws RazorpayException {
    if (response == null) {
      throw new RazorpayException(INVALID_RESPONSE_FROM_SERVER);
    }

    int statusCode = response.code();
    String responseBody = null;
    JSONObject responseJson = null;

    try {
      responseBody = response.body().string();
      responseJson = new JSONObject(responseBody);
    } catch (IOException e) {
      throw new RazorpayException(e.getMessage());
    }

    String collectionName  = null;
    collectionName = responseJson.has("payment_links")?
            "payment_links": "items";

    if (statusCode >= STATUS_OK && statusCode < STATUS_MULTIPLE_CHOICE) {
      return parseCollectionResponse(responseJson.getJSONArray(collectionName), response.request().url());
    }

    throwException(statusCode, responseJson);
    return (ArrayList)Collections.emptyList();
  }

  private String getEntity(JSONObject jsonObj, HttpUrl url) {
    if(!jsonObj.has(ENTITY)) {
      return getEntityNameFromURL(url);
    }else if(jsonObj.get(ENTITY).toString().equals("settlement.ondemand")){
      return "settlement";
    }else{
      return jsonObj.getString(ENTITY);
    }
  }

  private void throwException(int statusCode, JSONObject responseJson) throws RazorpayException {
    if (responseJson.has(ERROR)) {
      JSONObject errorResponse = responseJson.getJSONObject(ERROR);
      String code = errorResponse.getString(STATUS_CODE);
      String description = errorResponse.getString(DESCRIPTION);
      throw new RazorpayException(code + ":" + description);
    }
    throwServerException(statusCode, responseJson.toString());
  }

  private void throwServerException(int statusCode, String responseBody) throws RazorpayException {
    StringBuilder sb = new StringBuilder();
    sb.append("Status Code: ").append(statusCode).append("\n");
    sb.append("Server response: ").append(responseBody);
    throw new RazorpayException(sb.toString());
  }

  private Class<Entity> getClass(String entity) {
    try {
      String entityClass = "com.razorpay." + WordUtils.capitalize(entity, '_').replace("_", "");
      return (Class<Entity>) Class.forName(entityClass);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }
}