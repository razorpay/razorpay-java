package com.razorpay;

import org.json.JSONObject;

public class Product extends Entity {
    /**
     * Product Configuration APIs
     * @see <a href="https://razorpay.com/docs/api/partners/product-configuration">Doc</a>
     * @param jsonObject
     */
    public Product(JSONObject jsonObject) {
        super(jsonObject);
    }
}
