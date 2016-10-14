package com.razorpay;


import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Utils {

    static <T extends Entity> T parseResponse(JSONObject jsonObject) throws RazorpayException {
        if(jsonObject.has(Constants.KEY_ENTITY)){
            Class<T> cls = getClass(jsonObject.getString(Constants.KEY_ENTITY));
            try {
                return cls.getConstructor(JSONObject.class).newInstance(jsonObject);
            } catch (Exception e) {
                throw new RazorpayException("Unable to parse response");
            }
        }
        else {
            throw new RazorpayException("Unable to parse response");
        }
    }

    static <T extends Entity> ArrayList<T> parseCollectionResponse(JSONObject jsonObject) throws RazorpayException {
        ArrayList<T> modelList = new ArrayList<T>();
        if(jsonObject.getString(Constants.KEY_ENTITY).equals(Constants.ENTITY_COLLECTION)){
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject refundJson = jsonArray.getJSONObject(i);
                T t = parseResponse(refundJson);
                if(t != null){
                    modelList.add(t);
                }
            }
        }
        else {
            throw new RazorpayException("Unable to parse response");
        }
        return modelList;
    }

    static <T extends Entity> T processResponse(Response response) throws IOException, RazorpayException {
        String responseBody = response.body().string();
        int statusCode = response.code();
        if (statusCode == Constants.STATUS_OK) {
            JSONObject jsonObject = new JSONObject(responseBody);
            return Utils.<T>parseResponse(jsonObject);
        } else  {
            processError(statusCode, responseBody);
        }
        return null;
    }

    static <T extends Entity> ArrayList<T> processCollectionResponse(Response response) throws IOException, RazorpayException {
        String responseBody = response.body().string();
        int statusCode = response.code();
        if (statusCode == Constants.STATUS_OK) {
            JSONObject jsonObject = new JSONObject(responseBody);
            return Utils.<T>parseCollectionResponse(jsonObject);
        } else {
            processError(statusCode, responseBody);
        }
        return null;
    }

    private static void processError(int statusCode, String responseBody) throws RazorpayException, IOException {
        try {
            JSONObject responseJson = new JSONObject(responseBody);
            throwException(statusCode, responseJson);
        } catch (JSONException e){
            throwServerException(statusCode, responseBody);
        }
    }

    private static void throwException(int statusCode, JSONObject responseJson) throws RazorpayException {
        if(responseJson.has(Constants.KEY_ERROR) == true) {
            String code = responseJson.getJSONObject(Constants.KEY_ERROR).getString("code");
            String description = responseJson.getJSONObject(Constants.KEY_ERROR).getString("description");
            throw new RazorpayException(code, description);
        }
        else {
            throwServerException(statusCode, responseJson.toString());
        }
    }

    private static void throwServerException(int statusCode, String responseBody) throws RazorpayException {
        StringBuilder sb = new StringBuilder();
        sb.append("The server did not send back a well-formed response.\n");
        sb.append("Server response: ");
        sb.append(responseBody);
        throw new RazorpayException(String.valueOf(statusCode), sb.toString());
    }

    static Class getClass(String entity){
        try {
            String entityClass = "com.razorpay." + Character.toUpperCase(entity.charAt(0)) + entity.substring(1);
            return Class.forName(entityClass);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

}
