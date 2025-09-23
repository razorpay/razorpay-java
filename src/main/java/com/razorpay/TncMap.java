package com.razorpay;

import org.json.JSONObject;

public class TncMap extends Entity {
    /**
     * Terms and Conditions APIs
     * @see <a href="https://razorpay.com/docs/api/partners/terms-conditions/#accept-terms-and-conditions-for-a-sub-merchant">Doc</a>
     * @param jsonObject
     */
    public TncMap(JSONObject jsonObject) {
        super(jsonObject);
    }
}
