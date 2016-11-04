package com.razorpay;

import okhttp3.Response;
import org.json.JSONObject;

import java.util.ArrayList;

class ApiClient {
    String auth;

    ApiClient(String auth){
        this.auth = auth;
    }

    <T extends Entity> T get(String path, JSONObject requestObject) throws RazorpayException {
        Response response = ApiUtils.getRequest(path, requestObject, auth);
        return Utils.processResponse(response);
    }

    <T extends Entity> T post(String path, JSONObject requestObject) throws RazorpayException{
        Response response = ApiUtils.postRequest(path, requestObject, auth);
        return Utils.processResponse(response);
    }

    <T extends Entity> ArrayList<T> getCollection(String path, JSONObject requestObject) throws RazorpayException{
        Response response = ApiUtils.getRequest(path, requestObject, auth);
        return Utils.processCollectionResponse(response);
    }

    <T extends Entity> ArrayList<T> postCollection(String path, JSONObject requestObject) throws RazorpayException{
        Response response = ApiUtils.postRequest(path, requestObject, auth);
        return Utils.processCollectionResponse(response);
    }
}
