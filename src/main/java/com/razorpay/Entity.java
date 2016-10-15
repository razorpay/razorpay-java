package com.razorpay;

import org.json.JSONObject;
import java.util.Date;

abstract class Entity {
    private JSONObject modelJson;

    Entity(JSONObject jsonObject){
        this.modelJson = jsonObject;
    }

    public <T> T get(String key) {
        // Return Date for timestamps
        if(key.equals(Constants.KEY_CREATED_AT)){
            return (T) new Date(modelJson.getLong(key)*1000);
        }
        Object value = modelJson.get(key);
        if(value == null) {
            return null;
        }
        return (T) value.getClass().cast(value);
    }



    public String toString(){
        return modelJson.toString();
    }
}
