package com.razorpay;

import java.io.File;
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
import okhttp3.MultipartBody;
import okhttp3.MediaType;

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
    return postRequest(version, path, requestObject, auth, Constants.API);
  }

  static Response postRequest(String version, String path, JSONObject requestObject, String auth, String host)
      throws RazorpayException {

    HttpUrl.Builder builder = getBuilder(version, path, host);

    RequestBody requestBody;

    if(requestObject != null && requestObject.has("file")){
       requestBody = fileRequestBody(requestObject);
    }else{
      String requestContent = requestObject == null ? "" : requestObject.toString();
      requestBody = RequestBody.create(Constants.MEDIA_TYPE_JSON, requestContent);
    }

    Request request =
        createRequest(Method.POST.name(), builder.build().toString(), requestBody, auth);
    return processRequest(request);
  }

  static Response putRequest(String version, String path, JSONObject requestObject, String auth)
          throws RazorpayException {
    return putRequest(version, path, requestObject, auth, Constants.API);
  }

  static Response putRequest(String version, String path, JSONObject requestObject, String auth, String host)
      throws RazorpayException {

    HttpUrl.Builder builder = getBuilder(version, path, host);

    String requestContent = requestObject == null ? "" : requestObject.toString();
    RequestBody requestBody = RequestBody.create(Constants.MEDIA_TYPE_JSON, requestContent);

    Request request =
        createRequest(Method.PUT.name(), builder.build().toString(), requestBody, auth);
    return processRequest(request);
  }

  static Response patchRequest(String version, String path, JSONObject requestObject, String auth)
          throws RazorpayException {
    return patchRequest(version, path, requestObject, auth, Constants.API);
  }

  static Response patchRequest(String version, String path, JSONObject requestObject, String auth, String host)
      throws RazorpayException {

    HttpUrl.Builder builder = getBuilder(version, path, host);

    String requestContent = requestObject == null ? "" : requestObject.toString();
    RequestBody requestBody = RequestBody.create(Constants.MEDIA_TYPE_JSON, requestContent);

    Request request =
        createRequest(Method.PATCH.name(), builder.build().toString(), requestBody, auth);
    return processRequest(request);
  }

  static Response getRequest(String version, String path, JSONObject requestObject, String auth)
          throws RazorpayException {
    return getRequest(version, path, requestObject, auth, Constants.API);
  }

  static Response getRequest(String version, String path, JSONObject requestObject, String auth, String host)
      throws RazorpayException {

    HttpUrl.Builder builder = getBuilder(version, path, host);
    addQueryParams(builder, requestObject);
    Request request = createRequest(Method.GET.name(), builder.build().toString(), null, auth);
    return processRequest(request);
  }

  static Response deleteRequest(String version, String path, JSONObject requestObject, String auth)
          throws RazorpayException {
    return deleteRequest(version, path, requestObject, auth, Constants.API);
  }

  static Response deleteRequest(String version, String path, JSONObject requestObject, String auth, String host)
      throws RazorpayException {

    HttpUrl.Builder builder = getBuilder(version, path, host);
    addQueryParams(builder, requestObject);

    Request request = createRequest(Method.DELETE.name(), builder.build().toString(), null, auth);
    return processRequest(request);
  }

  private static HttpUrl.Builder getBuilder(String version, String path, String host) {
    HttpUrl.Builder builder;
    switch (host)
    {
      case Constants.API:
        builder = getAPIBuilder(version, path);
        break;
      case Constants.AUTH:
        builder = getOAuthBuilder(path);
        break;
      default:
        builder = getAPIBuilder(version, path);
    }
    return builder;
  }

  private static HttpUrl.Builder getAPIBuilder(String version, String path) {
    return new HttpUrl.Builder().scheme(Constants.SCHEME).host(Constants.HOSTNAME)
            .port(Constants.PORT).addPathSegment(version).addPathSegments(path);
  }

  private static HttpUrl.Builder getOAuthBuilder(String path) {
    return new HttpUrl.Builder().scheme(Constants.SCHEME).host(Constants.AUTH_HOSTNAME)
            .port(Constants.PORT).addPathSegments(path);
  }

  private static Request createRequest(String method, String url, RequestBody requestBody,
      String auth) {
    Request.Builder builder =
        new Request.Builder().url(url);

    if (auth != null) {
      builder.addHeader(Constants.AUTH_HEADER_KEY, auth);
    }

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

  private static String getMediaType(String fileName){
    int extensionIndex = fileName.lastIndexOf('.');
    String extenionName = fileName.substring(extensionIndex + 1);
    if(extenionName == "jpg" | extenionName == "jpeg" | extenionName == "png" | extenionName == "jfif"){
      return "image/jpg";
    }
      return "image/pdf";
  }

  private static RequestBody fileRequestBody(JSONObject requestObject){
    File fileToUpload = new File((String) requestObject.get("file"));
    String fileName = fileToUpload.getName();

    MediaType mediaType = MediaType.parse(getMediaType(fileName));
    RequestBody fileBody = RequestBody.create(mediaType, fileToUpload);

    MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
    multipartBodyBuilder.addFormDataPart("file",fileName, fileBody);

    Iterator<?> iterator = requestObject.keys();
    while (iterator.hasNext()) {
      Object key = iterator.next();
      Object value = requestObject.get(key.toString());
      multipartBodyBuilder.addFormDataPart((String) key, (String) value);
    }

    MultipartBody requestBody = multipartBodyBuilder.build();
    return requestBody;
  }
}
