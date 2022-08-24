package com.razorpay;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

interface IAppUtils {

    String processGetRequest(String path, String request, String auth) throws RazorpayException, IOException, URISyntaxException;

    String processPostRequest(String path, String request, String auth) throws RazorpayException, IOException, URISyntaxException;
    String processDeleteRequest(String path, String request, String auth) throws RazorpayException, IOException, URISyntaxException;

    String processPatchRequest(String path, String request, String auth) throws RazorpayException, IOException, URISyntaxException;

    String processPutRequest(String path, String request, String auth) throws RazorpayException, IOException, URISyntaxException;
}