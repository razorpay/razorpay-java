package com.razorpay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

class ApiUtils {

  private static OkHttpClient client;
  private static Map<String, String> headers = new HashMap<String, String>();
  private static final String version = "1.2.0";

  static void createHttpClientInstance(boolean enableLogging) {
    if (client == null) {
      client = new OkHttpClient.Builder().build();
    }
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    if (enableLogging) {
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
    } else {
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
    }
    client = client.newBuilder().addInterceptor(loggingInterceptor).build();
  }

  private enum Method {
    GET, POST, PUT
  }

  static Response postRequest(String path, JSONObject requestObject, String auth)
      throws RazorpayException {
    HttpUrl.Builder builder = getBuilder(path);

    String requestContent = requestObject == null ? "" : requestObject.toString();
    RequestBody requestBody = RequestBody.create(Constants.MEDIA_TYPE_JSON, requestContent);

    Request request =
        createRequest(Method.POST.name(), builder.build().toString(), requestBody, auth);
    return processRequest(request);
  }

  static Response putRequest(String path, JSONObject requestObject, String auth)
      throws RazorpayException {
    HttpUrl.Builder builder = getBuilder(path);

    String requestContent = requestObject == null ? "" : requestObject.toString();
    RequestBody requestBody = RequestBody.create(Constants.MEDIA_TYPE_JSON, requestContent);

    Request request =
        createRequest(Method.PUT.name(), builder.build().toString(), requestBody, auth);
    return processRequest(request);
  }

  static Response getRequest(String path, JSONObject requestObject, String auth)
      throws RazorpayException {
    HttpUrl.Builder builder = getBuilder(path);

    addQueryParams(builder, requestObject);

    Request request = createRequest(Method.GET.name(), builder.build().toString(), null, auth);

    return processRequest(request);
  }

  private static HttpUrl.Builder getBuilder(String path) {
    return new HttpUrl.Builder().scheme(Constants.SCHEME).host(Constants.HOSTNAME)
        .port(Constants.PORT).addPathSegment(Constants.VERSION).addPathSegments(path);
  }

  private static Request createRequest(String method, String url, RequestBody requestBody,
      String auth) {
    Request.Builder builder =
        new Request.Builder().url(url).addHeader(Constants.AUTH_HEADER_KEY, auth);

    builder.addHeader(Constants.USER_AGENT,
        "Razorpay/v1 JAVASDK/" + version + " Java/" + System.getProperty("java.version"));

    for (Map.Entry<String, String> header : headers.entrySet()) {
      builder.addHeader(header.getKey(), header.getValue());
    }

    return builder.method(method, requestBody).build();
  }

  private static void addQueryParams(HttpUrl.Builder builder, JSONObject request) {
    if (request == null)
      return;

    Iterator<?> keys = request.keys();
    while (keys.hasNext()) {
      String key = (String) keys.next();
      builder.addQueryParameter(key, request.get(key).toString());
    }
  }

  private static Response processRequest(Request request) throws RazorpayException {
    try {
      return client.newCall(request).execute();
    } catch (IOException e) {
      throw new RazorpayException(e.getMessage());
    }
  }

  static void addHeaders(Map<String, String> header) {
    headers.putAll(header);
  }
}
