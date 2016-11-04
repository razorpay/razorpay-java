package com.razorpay;

import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

class ApiClient {
    private String auth;

    ApiClient(String auth){
        this.auth = auth;
    }

    <T extends Entity> T get(String path, JSONObject requestObject) throws RazorpayException {
        Response response = ApiUtils.getRequest(path, requestObject, auth);
        return processResponse(response);
    }

    <T extends Entity> T post(String path, JSONObject requestObject) throws RazorpayException{
        Response response = ApiUtils.postRequest(path, requestObject, auth);
        return processResponse(response);
    }

    <T extends Entity> ArrayList<T> getCollection(String path, JSONObject requestObject) throws RazorpayException{
        Response response = ApiUtils.getRequest(path, requestObject, auth);
        return processCollectionResponse(response);
    }

    <T extends Entity> ArrayList<T> postCollection(String path, JSONObject requestObject) throws RazorpayException{
        Response response = ApiUtils.postRequest(path, requestObject, auth);
        return processCollectionResponse(response);
    }

    private static <T extends Entity> T parseResponse(JSONObject jsonObject)
        throws RazorpayException {
        if (jsonObject.has(Constants.ENTITY)) {
            Class<T> cls = getClass(jsonObject.getString(Constants.ENTITY));
            try {
                return cls.getConstructor(JSONObject.class).newInstance(jsonObject);
            } catch (Exception e) {
                throw new RazorpayException("Unable to parse response because of " + e.getMessage());
            }
        }

        throw new RazorpayException("Unable to parse response");
    }

    private static <T extends Entity> ArrayList<T> parseCollectionResponse(JSONObject jsonObject)
        throws RazorpayException {

        ArrayList<T> modelList = new ArrayList<T>();
        if (jsonObject.has(Constants.ENTITY) && Constants.COLLECTION.equals(jsonObject.getString(Constants.ENTITY))) {
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject refundJson = jsonArray.getJSONObject(i);
                    T t = parseResponse(refundJson);
                    modelList.add(t);
                }
                return modelList;
            } catch (RazorpayException e) {
                throw e;
            }
        }

        throw new RazorpayException("Unable to parse response");
    }

    private static <T extends Entity> T processResponse(Response response) throws RazorpayException {
        if (response == null) {
            throw new RazorpayException("Invalid Response from server");
        }

        int statusCode = response.code();
        String responseBody = null;
        JSONObject responseJson = null;

        try {
            responseBody = response.body().string();
            responseJson = new JSONObject(responseBody);
        } catch (IOException e) {
            throw new RazorpayException(e.getMessage());
        }

        if (statusCode == Constants.STATUS_OK) {
            return ApiClient.<T>parseResponse(responseJson);
        }

        throwException(statusCode, responseJson);
        return null;
    }

    private static <T extends Entity> ArrayList<T> processCollectionResponse(Response response)
        throws RazorpayException {
        if (response == null) {
            throw new RazorpayException("Invalid Response from server");
        }

        int statusCode = response.code();
        String responseBody = null;
        JSONObject responseJson = null;

        try {
            responseBody = response.body().string();
            responseJson = new JSONObject(responseBody);
        } catch (IOException e) {
            throw new RazorpayException(e.getMessage());
        }


        if (statusCode == Constants.STATUS_OK) {
            return ApiClient.<T>parseCollectionResponse(responseJson);
        }

        throwException(statusCode, responseJson);
        return null;
    }

    private static void throwException(int statusCode, JSONObject responseJson)
        throws RazorpayException {
        if (responseJson.has(Constants.ERROR)) {
            JSONObject errorResponse = responseJson.getJSONObject(Constants.ERROR);
            String code = errorResponse.getString(Constants.STATUS_CODE);
            String description = errorResponse.getString(Constants.DESCRIPTION);
            throw new RazorpayException(code + ":" + description);
        }
        throwServerException(statusCode, responseJson.toString());
    }

    private static void throwServerException(int statusCode, String responseBody)
        throws RazorpayException {
        StringBuilder sb = new StringBuilder();
        sb.append("Status Code: ").append(statusCode).append("\n");
        sb.append("Server response: ").append(responseBody);
        throw new RazorpayException(sb.toString());
    }

    private static Class getClass(String entity) {
        try {
            String entityClass =
                "com.razorpay." + Character.toUpperCase(entity.charAt(0)) + entity.substring(1);
            return Class.forName(entityClass);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
