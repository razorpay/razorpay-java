package com.razorpay;

import com.sun.javafx.sg.prism.NGShape;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Utils {

    static <T extends Model> T parseResponse(JSONObject jsonObject, String entity){
        if(jsonObject.getString("entity").equals(entity)){
            Class<T> cls = getClass(entity);

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

    static <T extends Model> ArrayList<T> parseCollectionResponse(JSONObject jsonObject, String entity){
        ArrayList<T> modelList = new ArrayList<T>();
        if(jsonObject.getString("entity").equals(Model.ENTITY_COLLECTION)){
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject refundJson = jsonArray.getJSONObject(i);
                T t = parseResponse(refundJson, entity);
                if(t != null){
                    modelList.add(t);
                }
            }
        }
        return modelList;
    }

    static <T extends Model> T processResponse(Response response, String entity) throws IOException, RazorpayException {
        int code = response.code();
        JSONObject jsonObject = new JSONObject(response.body().string());
        if (code == 200) {
            return Utils.<T>parseResponse(jsonObject, entity);
        } else if (code == 400 || code == 401) {
            throw new RazorpayException(jsonObject.getJSONObject("error").getString("code"), jsonObject.getJSONObject("error").getString("description"));
        }
        return null;
    }

    static <T extends Model> ArrayList<T> processCollectionResponse(Response response, String entity) throws IOException, RazorpayException {
        int code = response.code();
        JSONObject jsonObject = new JSONObject(response.body().string());
        if (code == 200) {
            return Utils.<T>parseCollectionResponse(jsonObject, entity);
        } else if (code == 400 || code == 401) {
            throw new RazorpayException(jsonObject.getJSONObject("error").getString("code"), jsonObject.getJSONObject("error").getString("description"));
        }
        return null;
    }

    static Class getClass(String entitiy){
        if(entitiy.equals(Model.ENTITY_ORDER)){
            return Order.class;
        }
        if(entitiy.equals(Model.ENTITY_PAYMENT)){
            return Payment.class;
        }
        if(entitiy.equals(Model.ENTITY_REFUND)){
            return Refund.class;
        }
        return null;
    }
}
