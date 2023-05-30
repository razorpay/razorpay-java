package com.razorpay;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

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

  private static String version = null;

  static void createHttpClientInstance(boolean enableLogging) throws RazorpayException {
    if (client == null) {
      HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
      if (enableLogging) {
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
      } else {
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
      }
      
      try {
        client = new OkHttpClient.Builder()
                                 .readTimeout(60, TimeUnit.SECONDS)
                                 .writeTimeout(60, TimeUnit.SECONDS)
                                 .addInterceptor(loggingInterceptor)
                                 .sslSocketFactory(new CustomTLSSocketFactory(), createDefaultTrustManager())
                                 .build();
      } catch (Exception e) {
        throw new RazorpayException(e);
      }
    }

    Properties properties = new Properties();
    try {
      properties.load(ApiUtils.class.getResourceAsStream("/project.properties"));
      version = (String) properties.get("version");
    } catch (IOException e) {
      throw new RazorpayException(e.getMessage());
    }
  }

  private enum Method {
    GET, POST, PUT, PATCH, DELETE
  }

  static Response postRequest(String version, String path, JSONObject requestObject, String auth)
      throws RazorpayException {

    HttpUrl.Builder builder = getBuilder(version, path);

    String requestContent = requestObject == null ? "" : requestObject.toString();
    RequestBody requestBody = RequestBody.create(Constants.MEDIA_TYPE_JSON, requestContent);
    Request request =
        createRequest(Method.POST.name(), builder.build().toString(), requestBody, auth);
    return processRequest(request);
  }

  static Response putRequest(String version, String path, JSONObject requestObject, String auth)
      throws RazorpayException {

    HttpUrl.Builder builder = getBuilder(version, path);

    String requestContent = requestObject == null ? "" : requestObject.toString();
    RequestBody requestBody = RequestBody.create(Constants.MEDIA_TYPE_JSON, requestContent);

    Request request =
        createRequest(Method.PUT.name(), builder.build().toString(), requestBody, auth);
    return processRequest(request);
  }

  static Response patchRequest(String version, String path, JSONObject requestObject, String auth)
      throws RazorpayException {

    HttpUrl.Builder builder = getBuilder(version, path);

    String requestContent = requestObject == null ? "" : requestObject.toString();
    RequestBody requestBody = RequestBody.create(Constants.MEDIA_TYPE_JSON, requestContent);

    Request request =
        createRequest(Method.PATCH.name(), builder.build().toString(), requestBody, auth);
    return processRequest(request);
  }

  static Response getRequest(String version, String path, JSONObject requestObject, String auth)
      throws RazorpayException {

    HttpUrl.Builder builder = getBuilder(version, path);
    addQueryParams(builder, requestObject);
    Request request = createRequest(Method.GET.name(), builder.build().toString(), null, auth);
    return processRequest(request);
  }

  static Response deleteRequest(String version, String path, JSONObject requestObject, String auth)
      throws RazorpayException {

    HttpUrl.Builder builder = getBuilder(version, path);
    addQueryParams(builder, requestObject);

    Request request = createRequest(Method.DELETE.name(), builder.build().toString(), null, auth);
    return processRequest(request);
  }

  private static HttpUrl.Builder getBuilder(String version, String path) {
    return new HttpUrl.Builder().scheme(Constants.SCHEME).host(Constants.HOSTNAME)
        .port(Constants.PORT).addPathSegment(version).addPathSegments(path);
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
  
  private static X509TrustManager createDefaultTrustManager() throws NoSuchAlgorithmException, KeyStoreException {
    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    trustManagerFactory.init((KeyStore) null);
    TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
    if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
      throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
    }
    X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
    return trustManager;
  }
}
