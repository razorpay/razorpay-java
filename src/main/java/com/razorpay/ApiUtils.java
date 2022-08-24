package com.razorpay;


import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


class ApiUtils implements IAppUtils {
  private static Map<String, String> headers = new HashMap<String, String>();

  private static String version = null;

  private enum Method {
    GET, POST, PUT, PATCH, DELETE
  }


  @Override
  public String processGetRequest(String path, String requestObject, String auth) throws RazorpayException, IOException, URISyntaxException {
    HttpsURLConnection httpconn =  createRequest(Method.GET.name(), new URL(path), null, auth);
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(
              httpconn.getInputStream()));
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {

        sb.append(line + "\n");
      }
      br.close();

      return sb.toString();
    } catch (IOException e) {
      InputStream errorStream = httpconn.getErrorStream();
      String getMessage = HttpException(errorStream);
      throw new RazorpayException(getMessage);
    }
  }

  @Override
  public String processPostRequest(String path, String requestObject, String auth) throws RazorpayException, IOException, URISyntaxException {

    HttpsURLConnection httpconn =  createRequest(Method.POST.name(), new URL(path), requestObject, auth);
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(
              httpconn.getInputStream()));
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {

        sb.append(line + "\n");
      }
      br.close();

      return sb.toString();
    } catch (IOException e) {
      InputStream errorStream = httpconn.getErrorStream();
      String getMessage = HttpException(errorStream);
      throw new RazorpayException(getMessage);
    }
  }

  @Override
  public String processDeleteRequest(String path, String requestObject, String auth) throws RazorpayException, IOException, URISyntaxException {

    HttpsURLConnection httpconn = createRequest(Method.DELETE.name(), new URL(path), requestObject, auth);
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(
              httpconn.getInputStream()));
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {

        sb.append(line + "\n");
      }
      br.close();

      return sb.toString();
    } catch (IOException e) {
      InputStream errorStream = httpconn.getErrorStream();
      String getMessage = HttpException(errorStream);
      throw new RazorpayException(getMessage);
    }
  }

  @Override
  public String processPutRequest(String path, String requestObject, String auth) throws RazorpayException, IOException, URISyntaxException {

    HttpsURLConnection httpconn = createRequest(Method.PUT.name(), new URL(path), requestObject, auth);
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(
              httpconn.getInputStream()));
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {

        sb.append(line + "\n");
      }
      br.close();

      return sb.toString();
    } catch (IOException e) {
      InputStream errorStream = httpconn.getErrorStream();
      String getMessage = HttpException(errorStream);
      throw new RazorpayException(getMessage);
    }
  }

  @Override
  public String processPatchRequest(String path, String requestObject, String auth) throws RazorpayException, IOException, URISyntaxException {

    HttpsURLConnection httpconn = createRequest(Method.PATCH.name(), new URL(path), requestObject, auth);
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(
              httpconn.getInputStream()));
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {

        sb.append(line + "\n");
      }
      br.close();

      return sb.toString();
    } catch (IOException e) {
      InputStream errorStream = httpconn.getErrorStream();
      String getMessage = HttpException(errorStream);
      throw new RazorpayException(getMessage);
    }
  }

  private static HttpsURLConnection createRequest(String method, URL url, String requestBody,
                                                  String auth) throws IOException {
    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
    conn.setConnectTimeout(60*1000);
    conn.setReadTimeout(60*1000);
    conn.setSSLSocketFactory(new TLSSocketConnectionFactory());
    /* Checking headers value */
    if(headers.size() > 0){
      for(Map.Entry<String, String> header: headers.entrySet()){
        conn.setRequestProperty(header.getKey(), header.getValue());
      }
    }

    conn.setRequestProperty("Authorization", "Basic " + auth);
    conn.setRequestProperty(Constants.USER_AGENT, "Razorpay/v1 JAVASDK/" + version + " Java/" + System.getProperty("java.version"));

    if (method == Method.PATCH.name()) {
      conn.setRequestMethod("POST");
      conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
    }else{
      conn.setRequestMethod(method);
    }

    conn.setUseCaches(false);
    if (conn.getRequestMethod() == Method.POST.name() || conn.getRequestMethod() == Method.PUT.name()) {
      conn.setRequestProperty("Content-Type", "application/json");
      if(requestBody != null ) {
        byte[] out = requestBody.getBytes("UTF-8");
        conn.setDoOutput(true);
        OutputStream stream = conn.getOutputStream();
        stream.write(out);
      }
    }

    return conn;
  }

  static void addHeaders(Map<String, String> header) {
    headers.putAll(header);
  }

  private static String HttpException(InputStream errorStream) throws IOException {
    String responseString = null;
    BufferedInputStream bis = null;
    try {
      StringBuilder sb = new StringBuilder();
      bis = new BufferedInputStream(errorStream);
      byte[] byteContents = new byte[4096];
      int bytesRead;
      String strContents;
      while ((bytesRead = bis.read(byteContents)) != -1) {
        strContents = new String(byteContents, 0, bytesRead, "UTF-8"); // You might need to replace the charSet as per the responseEncoding returned by httpurlconnection above
        sb.append(strContents);
      }
      return sb.toString();
    } finally {
      if (bis != null) {
        bis.close();
      }
    }
  }
}
