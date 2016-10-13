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
        return modelJson.getString(key);
    }

    public String toString(){
        return modelJson.toString();
    }
}
