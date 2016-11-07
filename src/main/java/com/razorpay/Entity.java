package com.razorpay;

import java.util.Date;

import org.json.JSONObject;

abstract class Entity {

  private JSONObject modelJson;

  private final String CREATED_AT = "created_at";
  private final String CAPTURED_AT = "captured_at";

  Entity(JSONObject jsonObject) {
    this.modelJson = jsonObject;
  }

  public <T> T get(String key) {
    // Return Date for timestamps
    if (key.equals(CREATED_AT) || key.equals(CAPTURED_AT)) {
      return (T) new Date(modelJson.getLong(key) * 1000);
    }
    Object value = modelJson.get(key);
    if (value == null) {
      return null;
    }
    return (T) value.getClass().cast(value);
  }

  public String toString() {
    return modelJson.toString();
  }
}
