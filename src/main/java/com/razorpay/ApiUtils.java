package com.razorpay;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.logging.HttpLoggingInterceptor;
import org.json.JSONObject;

import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class ApiUtils {

  private static OkHttpClient client;

  static void createHttpClientInstance(boolean enableLogging) {
    if(client == null){
      client = new OkHttpClient.Builder().build();
    }
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    if(enableLogging == true){
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
    }
    else{
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
    }
    client = client.newBuilder().addInterceptor(loggingInterceptor).build();
  }

  private enum Method {
    GET, POST
  }

  static Response postRequest(String path, JSONObject requestObject, String auth) throws RazorpayException {
    HttpUrl.Builder builder =
        new HttpUrl.Builder().scheme(Constants.SCHEME).host(Constants.HOSTNAME).port(Constants.PORT)
            .addPathSegment(Constants.VERSION).addPathSegments(path);

    RequestBody requestBody = null;
    if (requestObject == null) {
      requestBody = RequestBody.create(Constants.MEDIA_TYPE_JSON, "");
    } else {
      requestBody = RequestBody.create(Constants.MEDIA_TYPE_JSON, requestObject.toString());
    }

    Request request = createRequest(Method.POST.name(), builder.build().toString(), requestBody, auth);
    return processRequest(request);
  }

  static Response getRequest(String path, JSONObject requestObject, String auth) throws RazorpayException {
    HttpUrl.Builder builder =
        new HttpUrl.Builder().scheme(Constants.SCHEME).host(Constants.HOSTNAME).port(Constants.PORT)
            .addPathSegment(Constants.VERSION).addPathSegments(path);

    addQueryParams(builder, requestObject);

    Request request = createRequest(Method.GET.name(), builder.build().toString(), null, auth);
    return processRequest(request);
  }

  private static Request createRequest(String method, String url, RequestBody requestBody, String auth) {
    Request.Builder builder =
        new Request.Builder().url(url).addHeader(Constants.AUTH_HEADER_KEY, auth);

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
}
