package com.razorpay;

import org.json.JSONObject;

abstract class Model {
    private JSONObject modelJson;
    static final String ENTITY_PAYMENT = "payment";
    static final String ENTITY_ORDER = "order";
    static final String ENTITY_REFUND = "refund";
    static final String ENTITY_COLLECTION = "collection";



    Model(JSONObject jsonObject){
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
