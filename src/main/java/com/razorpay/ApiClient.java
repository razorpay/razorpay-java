package com.razorpay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.HttpUrl;
import okhttp3.Response;

class ApiClient {

  String auth;

  private static final String ENTITY = "entity";
  private static final String COLLECTION = "collection";
  private static final String ERROR = "error";
  private static final String DESCRIPTION = "description";
  private static final String STATUS_CODE = "code";
  private static final String PAYMENT_LINKS = "payment_links";
  private static final String ITEMS = "items";

  private static final int STATUS_OK = 200;
  private static final int STATUS_MULTIPLE_CHOICE = 300;

  ApiClient() { }

  ApiClient(String auth) {
    this.auth = auth;
  }

  public <T extends Entity> T get(String version, String path, JSONObject requestObject)
          throws RazorpayException {
    return get(version, path, requestObject, Constants.API);
  }

  public <T extends Entity> T get(String version, String path,
                                 JSONObject requestObject, String host)
          throws RazorpayException {
    Response response = ApiUtils.getRequest(version, path, requestObject, auth, host);
    return processResponse(response);
  }

  public <T> T post(String version, String path, JSONObject requestObject)
          throws RazorpayException {
    return post(version, path, requestObject, Constants.API);
  }

  public <T> T post(String version, String path,
                    JSONObject requestObject, String host)
          throws RazorpayException {
    Response response = ApiUtils.postRequest(version, path, requestObject, auth, host);
    return processResponse(response);
  }

  public <T extends Entity> T put(String version, String path, JSONObject requestObject)
          throws RazorpayException {
    return put(version, path, requestObject, Constants.API);
  }

  public <T extends Entity> T put(String version, String path,
                                 JSONObject requestObject, String host)
          throws RazorpayException {
    Response response = ApiUtils.putRequest(version, path, requestObject, auth, host);
    return processResponse(response);
  }

  public <T extends Entity> T patch(String version, String path, JSONObject requestObject)
          throws RazorpayException {
    return patch(version, path, requestObject, Constants.API);
  }

  public <T extends Entity> T patch(String version, String path,
                                   JSONObject requestObject, String host)
          throws RazorpayException {
    Response response = ApiUtils.patchRequest(version, path, requestObject, auth, host);
    return processResponse(response);
  }

  <T extends Entity> ArrayList<T> getCollection(String version, String path,
                                                JSONObject requestObject)
          throws RazorpayException {
    Response response = ApiUtils.getRequest(version, path, requestObject, auth);
    return processCollectionResponse(response);
  }

  public <T> T delete(String version, String path, JSONObject requestObject)
          throws RazorpayException {
    Response response = ApiUtils.deleteRequest(version, path, requestObject, auth);
    return processDeleteResponse(response);
  }

  private <T> T processDeleteResponse(Response response) throws RazorpayException {
    validateResponse(response);

    int statusCode = response.code();
    String responseBody = safeReadResponseBody(response);

    if ("[]".equals(responseBody)) {
      return (T) Collections.emptyList();
    }

    if (statusCode == 204) {
      return null;
    }

    JSONObject responseJson = new JSONObject(responseBody);

    if (statusCode < STATUS_OK || statusCode >= STATUS_MULTIPLE_CHOICE) {
      throwException(statusCode, responseJson);
    }

    return (T) parseResponse(responseJson,
            getEntity(responseJson, response.request().url()));
  }

  private <T extends Entity> T parseResponse(JSONObject jsonObject, String entity)
          throws RazorpayException {
    if (entity == null) {
      throw new RazorpayException("Unable to parse response");
    }

    Class<? extends Entity> cls = getClass(entity);
    if (cls == null) {
      throw new RazorpayException("Unknown entity: " + entity);
    }

    try {
      return (T) cls.getConstructor(JSONObject.class).newInstance(jsonObject);
    } catch (Exception e) {
      throw new RazorpayException("Unable to parse response: " + e.getMessage(), e);
    }
  }

  private <T extends Entity> ArrayList<T> parseCollectionResponse(
          JSONArray jsonArray, HttpUrl requestUrl) throws RazorpayException {

    ArrayList<T> modelList = new ArrayList<>();

    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject jsonObj = jsonArray.getJSONObject(i);
      T entity = parseResponse(jsonObj, getEntity(jsonObj, requestUrl));
      modelList.add(entity);
    }
    return modelList;
  }

  private String getEntityNameFromURL(HttpUrl url) {
    String param = url.pathSegments().get(1);
    return EntityNameURLMapping.getEntityName(param);
  }

  <T> T processResponse(Response response) throws RazorpayException {
    validateResponse(response);

    int statusCode = response.code();
    String responseBody = safeReadResponseBody(response);

    if ("[]".equals(responseBody)) {
      return (T) Collections.emptyList();
    }

    JSONObject responseJson = new JSONObject(responseBody);

    if (statusCode >= STATUS_OK && statusCode < STATUS_MULTIPLE_CHOICE) {
      return (T) parseResponse(responseJson,
              getEntity(responseJson, response.request().url()));
    }

    throwException(statusCode, responseJson);
    return null;
  }

  <T extends Entity> ArrayList<T> processCollectionResponse(Response response)
          throws RazorpayException {
    validateResponse(response);

    int statusCode = response.code();
    JSONObject responseJson =
            new JSONObject(safeReadResponseBody(response));

    String collectionName =
            responseJson.has(PAYMENT_LINKS) ? PAYMENT_LINKS : ITEMS;

    if (statusCode >= STATUS_OK && statusCode < STATUS_MULTIPLE_CHOICE) {
      return parseCollectionResponse(
              responseJson.getJSONArray(collectionName),
              response.request().url());
    }

    throwException(statusCode, responseJson);
    return null;
  }

  private String getEntity(JSONObject jsonObj, HttpUrl url) {
    if (!jsonObj.has(ENTITY)) {
      return getEntityNameFromURL(url);
    }

    String entity = jsonObj.getString(ENTITY);
    return getClass(entity) == null ? getEntityNameFromURL(url) : entity;
  }

  private void throwException(int statusCode, JSONObject responseJson)
          throws RazorpayException {
    if (responseJson.has(ERROR)) {
      JSONObject errorResponse = responseJson.getJSONObject(ERROR);
      String code = errorResponse.optString(STATUS_CODE);
      String description = errorResponse.optString(DESCRIPTION);
      throw new RazorpayException(code + ": " + description);
    }
    throwServerException(statusCode, responseJson.toString());
  }

  private void throwServerException(int statusCode, String responseBody)
          throws RazorpayException {
    throw new RazorpayException(
            "Status Code: " + statusCode + "\nServer response: " + responseBody);
  }

  private Class<? extends Entity> getClass(String entity) {
    try {
      String entityClass =
              "com.razorpay." +
              WordUtils.capitalize(entity, '_').replace("_", "");
      return (Class<? extends Entity>) Class.forName(entityClass);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }

  private void validateResponse(Response response) throws RazorpayException {
    if (response == null) {
      throw new RazorpayException("Invalid response received from server");
    }
  }

  private String safeReadResponseBody(Response response)
          throws RazorpayException {
    if (response.body() == null) {
      throw new RazorpayException("Empty response body received from server");
    }
    try {
      return response.body().string();
    } catch (IOException e) {
      throw new RazorpayException("Failed to read response body", e);
    }
  }
}
