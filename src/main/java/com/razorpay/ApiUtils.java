package com.razorpay;

import java.io.IOException;
import java.util.Iterator;

import org.json.JSONObject;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class ApiUtils {

  private static String auth;
  private static OkHttpClient client;

  private enum Method {
    GET, POST
  }

  static void setAuthCredentials(String key, String secret) {
    auth = Credentials.basic(key, secret);
  }

  static void setClient(OkHttpClient client) {
    ApiUtils.client = client;
  }

  static Response postRequest(String path, JSONObject requestObject) throws RazorpayException {
    StringBuilder urlBuilder = new StringBuilder(Constants.BASE_URL).append(path);
    RequestBody requestBody =
        RequestBody.create(Constants.MEDIA_TYPE_JSON, requestObject.toString());

    Request request = createRequest(Method.POST.name(), urlBuilder.toString(), requestBody);
    return processRequest(request);
  }

  static Response getRequest(String path, JSONObject requestObject) throws RazorpayException {
    StringBuilder urlBuilder = new StringBuilder(Constants.BASE_URL).append(path);
    urlBuilder.append(addQueryParams(requestObject));

    Request request = createRequest(Method.GET.name(), urlBuilder.toString(), null);
    return processRequest(request);
  }

  private static Request createRequest(String method, String url, RequestBody requestBody) {
    Request.Builder builder =
        new Request.Builder().url(url).addHeader(Constants.AUTH_HEADER_KEY, auth);

    return builder.method(method, requestBody).build();
  }

  private static String addQueryParams(JSONObject request) {
    StringBuilder urlBuilder = new StringBuilder();

    if (request == null)
      return urlBuilder.toString();

    urlBuilder.append("?");

    Iterator<?> keys = request.keys();
    while (keys.hasNext()) {
      String key = (String) keys.next();
      urlBuilder.append(key + "=" + request.get(key).toString());
    }
    return urlBuilder.toString();
  }

  private static Response processRequest(Request request) throws RazorpayException {
    try {
      return client.newCall(request).execute();
    } catch (IOException e) {
      throw new RazorpayException(e.getMessage());
    }
  }
}
