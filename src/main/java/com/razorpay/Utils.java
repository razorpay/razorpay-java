package com.razorpay;

import java.util.List;

import org.json.JSONObject;

import javax.crypto.Mac;

import javax.crypto.spec.SecretKeySpec;

public class Utils {

  protected String key;

  protected String secret;

  public Utils(String key, String secret) {
    this.key = key;
    this.secret = secret;
  }

  public boolean verifyPaymentSignature(JSONObject attributes)
  {
    String expectedSignature = attributes.get('razorpay_signature');
    String orderId = attributes.get('razorpay_order_id');
    String paymentId = attributes.get('razorpay_payment_id');
    String payload = orderId + '|' + paymentId;

    return this.verifySignature(payload, expectedSignature);
  }

  public boolean verifyWebhookSignature(String payload, String expectedSignature)
  {
    return this.verifySignature(payload, expectedSignature);
  }

  public boolean verifySignature(String payload, String expectedSignature)
  {
    String actualSignature = this.getHash(payload);
        
    return this.isEqual(actualSignature.getBytes(), expectedSignature.getBytes());
  }

  public boolean isEqual(byte[] a, byte[] b) {
    if (a.length != b.length) {
        return false;
    }

    int result = 0;
    for (int i = 0; i < a.length; i++) {
      result |= a[i] ^ b[i];
    }

    return result == 0;
  } 

  public String getHash(String payload) {
    Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
    SecretKeySpec secret_key = new SecretKeySpec(this.secret.getBytes(), "HmacSHA256");
    sha256_HMAC.init(secret_key);

    return sha256_HMAC.doFinal(payload.getBytes());
  }  
}