package com.razorpay;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

interface IApiUtils {

    public String processGetRequest(String path, JSONObject requestObject, String auth) throws RazorpayException, IOException, URISyntaxException, JSONException;

    public String processPostRequest(String path, JSONObject requestObject, String auth) throws RazorpayException, IOException, URISyntaxException, JSONException;
    public String processDeleteRequest(String path, JSONObject requestObject, String auth) throws RazorpayException, IOException, URISyntaxException, JSONException;

    public String processPatchRequest(String path, JSONObject requestObject, String auth) throws RazorpayException, IOException, URISyntaxException, JSONException;

    public String processPutRequest(String path, JSONObject requestObject, String auth) throws RazorpayException, IOException, URISyntaxException, JSONException;
}
