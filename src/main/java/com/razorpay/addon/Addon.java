package com.razorpay.addon;

import com.google.gson.Gson;
import com.razorpay.Entity;
import lombok.Builder;
import org.json.JSONObject;

@Builder
public class Addon extends Entity {

  public Addon(JSONObject jsonObject) {
    super(jsonObject);
  }

  AddOnItem items;
  Integer quantity;

  public JSONObject toJson(){

    JSONObject addonRequest = new JSONObject();
    JSONObject itemsJson = new JSONObject();
    itemsJson.put("name",items.getName());
    itemsJson.put("amount",items.getAmount());
    itemsJson.put("currency",items.getCurrency());
    itemsJson.put("description",items.getDescription());
    addonRequest.put("item",itemsJson);
    addonRequest.put("quantity",this.quantity);

    return addonRequest;
  }

  public static Addon getAddOnFromJson(JSONObject jsonObject) throws Exception {
    Gson gson = new Gson();
    try {
      Addon addon= gson.fromJson(String.valueOf(jsonObject), Addon.class);
      return addon;
    } catch (Exception e){
      throw new Exception("Error parsing. Please check the input string");
    }
  }

  public boolean validateAddOn(){
    if(this.items.getName() == null || this.items.getName().isEmpty()) {
      return false;
    }
    return true;
  }
}
