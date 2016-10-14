package com.razorpay;


import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Utils {

    static <T extends Entity> T parseResponse(JSONObject jsonObject){
        if(jsonObject.has("entity")){
            Class<T> cls = getClass(jsonObject.getString("entity"));

            try {
                return cls.getConstructor(JSONObject.class).newInstance(jsonObject);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    static <T extends Entity> ArrayList<T> parseCollectionResponse(JSONObject jsonObject){
        ArrayList<T> modelList = new ArrayList<T>();
        if(jsonObject.getString("entity").equals(Entity.ENTITY_COLLECTION)){
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject refundJson = jsonArray.getJSONObject(i);
                T t = parseResponse(refundJson);
                if(t != null){
                    modelList.add(t);
                }
            }
        }
        return modelList;
    }

    static <T extends Entity> T processResponse(Response response) throws IOException, RazorpayException {
        String responseBody = response.body().string();
        int statusCode = response.code();
        if (statusCode == 200) {
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
        if (statusCode == 200) {
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
        if(responseJson.has("error") == true) {
            String code = responseJson.getJSONObject("error").getString("code");
            String description = responseJson.getJSONObject("error").getString("description");
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
