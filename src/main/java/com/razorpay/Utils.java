package com.razorpay;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;

public class Utils {

  private String key;

  private String secret;

  public Utils(String key, String secret) {
    this.key = key;
    this.secret = secret;
  }

  public boolean verifyPaymentSignature(JSONObject attributes) throws RazorpayException {
    String expectedSignature = attributes.getString("razorpay_signature");
    String orderId = attributes.getString("razorpay_order_id");
    String paymentId = attributes.getString("razorpay_payment_id");
    String payload = orderId + '|' + paymentId;

    return verifySignature(payload, expectedSignature);
  }

  public boolean verifyWebhookSignature(String payload, String expectedSignature)
      throws RazorpayException {
    return verifySignature(payload, expectedSignature);
  }

  public boolean verifySignature(String payload, String expectedSignature)
      throws RazorpayException {
    String actualSignature = this.getHash(payload);

    return isEqual(actualSignature.getBytes(), expectedSignature.getBytes());
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

  public String getHash(String payload) throws RazorpayException {
    Mac sha256_HMAC;
    try {
      sha256_HMAC = Mac.getInstance("HmacSHA256");
      SecretKeySpec secret_key = new SecretKeySpec(this.secret.getBytes("UTF-8"), "HmacSHA256");
      sha256_HMAC.init(secret_key);

      byte[] hash = sha256_HMAC.doFinal(payload.getBytes());

      return new String(Hex.encodeHex(hash));
    } catch (Exception e) {

      throw new RazorpayException(e.getMessage());
    }
  }
}
