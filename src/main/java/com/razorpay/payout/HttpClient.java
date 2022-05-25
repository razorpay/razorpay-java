package com.razorpay.payout;

import com.razorpay.RazorpayException;
import com.razorpay.payout.handlers.ResponseHandler;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpClient {
    private static OkHttpClient client;
    private static final int READ_TIMEOUT = 60;
    private static final int WRITE_TIMEOUT = 60;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static OkHttpClient createHttpClientInstance(boolean enableLogging) throws RazorpayException {
        if (client == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            if (enableLogging) {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            } else {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            }

            try {
                client = new OkHttpClient.Builder()
                        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                        .addInterceptor(loggingInterceptor)
                        .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS))
                        .build();
            } catch (Exception e) {
                throw new RazorpayException(e);
            }
        }

        return client;
    }



    public static <T> T fire(String method, String url, String body, Map<String, String> headers, ResponseHandler<T> handler) throws RazorpayException, IOException {
        Request.Builder builder = new Request.Builder()
                .url(url);

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }

        final Response response;
        switch (method) {
            case "GET":
                response = client.newCall(builder.build()).execute();
                break;
            case "POST":
                RequestBody postRequestBody = RequestBody.create(body, JSON);
                builder.post(postRequestBody);
                response = client.newCall(builder.build()).execute();
                break;
            case "PUT":
                RequestBody putRequestBody = RequestBody.create(body, JSON);
                builder.put(putRequestBody);
                response = client.newCall(builder.build()).execute();
                break;
            case "PATCH":
                RequestBody patchRequestBody = RequestBody.create(body, JSON);
                builder.patch(patchRequestBody);
                response = client.newCall(builder.build()).execute();
                break;
            case "DELETE":
                throw new RazorpayException("We do not support delete operation");
            default:
                throw new IllegalArgumentException("Invalid HTTP Method");
        }

        return handler.parse(response);
    }
}
