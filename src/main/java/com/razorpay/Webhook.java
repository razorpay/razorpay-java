package com.razorpay;

import org.json.JSONObject;

public class Webhook extends Entity {
    /**
     * Webhook APIs
     * @see <a href="https://razorpay.com/docs/api/partners/webhooks">Doc</a>
     * @param jsonObject
     */
    public Webhook(JSONObject jsonObject) {
        super(jsonObject);
    }
}
