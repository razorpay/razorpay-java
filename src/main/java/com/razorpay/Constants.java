package com.razorpay;


import okhttp3.MediaType;

public class Constants {

    static final int STATUS_OK = 200;

    // JSON response keys
    static final String KEY_ENTITY= "entity";
    static final String KEY_ERROR = "error";

    // Entities
    static final String ENTITY_PAYMENT = "payment";
    static final String ENTITY_ORDER = "order";
    static final String ENTITY_REFUND = "refund";
    static final String ENTITY_COLLECTION = "collection";

    // API constants
    static final String BASE_URL = "https://api.razorpay.com/v1";
    static final String AUTH_HEADER_KEY = "Authorization";
    static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

}
