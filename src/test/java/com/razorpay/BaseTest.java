package com.razorpay;


import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

import static org.mockito.Mockito.when;

public class BaseTest{

    protected void mockGetRequest(ApiUtils apiUtils, URL url, String request, String response) throws JSONException, IOException, URISyntaxException, RazorpayException {
        when(apiUtils.processGetRequest(url.toString(),request,"test")).thenReturn(response);
    }
    protected void mockPostRequest(ApiUtils apiUtils, URL url, String request, String response) throws JSONException, IOException, URISyntaxException, RazorpayException {
        when(apiUtils.processPostRequest(url.toString(),request,"test")).thenReturn(response);

    }
    protected void mockPutRequest(ApiUtils apiUtils, URL url, String request, String response) throws JSONException, IOException, URISyntaxException, RazorpayException {
        when(apiUtils.processPutRequest(url.toString(),request,"test")).thenReturn(response);
    }
    protected void mockDeleteRequest(ApiUtils apiUtils, URL url, String request, String response) throws JSONException, IOException, URISyntaxException, RazorpayException {
        when(apiUtils.processDeleteRequest(url.toString(),request,"test")).thenReturn(response);
    }
    protected void mockPatchRequest(ApiUtils apiUtils, URL url, String request, String response) throws JSONException, IOException, URISyntaxException, RazorpayException {
        when(apiUtils.processPatchRequest(url.toString(),request,"test")).thenReturn(response);
    }



}