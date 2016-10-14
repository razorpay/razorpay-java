package com.razorpay;


import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

class ApiUtils {
    private static String auth;
    private static OkHttpClient client;

    static void setAuthCredentials(String key, String secret){
        auth = Credentials.basic(key, secret);
    }


    private static Request createRequest(String method, String path, JSONObject options){
        Request request;
        Request.Builder builder = new Request.Builder()
                .url(Constants.BASE_URL + path)
                .addHeader(Constants.AUTH_HEADER_KEY, auth);

        if(options != null){
            request = builder.method(method, RequestBody.create(Constants.MEDIA_TYPE_JSON, options.toString()))
                    .build();
        }
        else{
            request = builder.build();
        }
        return request;
    }

    static Response postRequest(String path, JSONObject options) throws IOException {
        Request request = createRequest("POST", path, options);
        return processRequest(request);
    }

    private static Response processRequest(Request request) throws IOException {
       return client.newCall(request).execute();
    }

    static Response getRequest(String path, JSONObject options) throws IOException {
        Request request = createRequest("GET", path, options);
        return processRequest(request);
    }

    static void setClient(OkHttpClient client) {
        ApiUtils.client = client;
    }
}
