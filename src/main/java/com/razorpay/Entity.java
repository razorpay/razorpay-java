package com.razorpay;

import org.json.JSONObject;

abstract class Entity {
    private JSONObject modelJson;

    Entity(JSONObject jsonObject){
        this.modelJson = jsonObject;
    }

    public String get(String key){
        Object value = modelJson.get(key);
        return String.valueOf(value);
    }

    public String toString(){
        return modelJson.toString();
    }
}
