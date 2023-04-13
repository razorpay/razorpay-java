package com.razorpay;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import static com.razorpay.Constants.RAZORPAY_PAYMENT_ID;
import static com.razorpay.Constants.RAZORPAY_SIGNATURE;

public class Utils {

  private Utils(){

  }

  public static boolean verifyPaymentSignature(JSONObject attributes, String apiSecret)
      throws RazorpayException {
    String expectedSignature = attributes.getString(RAZORPAY_SIGNATURE);
    String orderId = attributes.getString("razorpay_order_id");
    String paymentId = attributes.getString(RAZORPAY_PAYMENT_ID);
    String payload = orderId + '|' + paymentId;
    return verifySignature(payload, expectedSignature, apiSecret);
  }

  public static boolean verifySubscription(JSONObject attributes, String apiSecret)
      throws RazorpayException {
    String expectedSignature = attributes.getString(RAZORPAY_SIGNATURE);
    String subscriptionId = attributes.getString("razorpay_subscription_id");
    String paymentId = attributes.getString(RAZORPAY_PAYMENT_ID);
    String payload = paymentId + '|' + subscriptionId;
    return verifySignature(payload, expectedSignature, apiSecret);
  }
  
  public static boolean verifyPaymentLink(JSONObject attributes, String apiSecret)
	      throws RazorpayException {
	    String expectedSignature = attributes.getString(RAZORPAY_SIGNATURE);
	    String paymentLinkStatus = attributes.getString("payment_link_status");
	    String paymentLinkId = attributes.getString("payment_link_id");
	    String paymentLinkRefId = attributes.getString("payment_link_reference_id");
	    String paymentId = attributes.getString(RAZORPAY_PAYMENT_ID);
	    String payload = paymentLinkId + '|' + paymentLinkRefId + '|' + paymentLinkStatus + '|' + paymentId;
	    return verifySignature(payload, expectedSignature, apiSecret);
  }

  public static boolean verifyWebhookSignature(String payload, String expectedSignature,
      String webhookSecret) throws RazorpayException {
    return verifySignature(payload, expectedSignature, webhookSecret);
  }

  public static boolean verifySignature(String payload, String expectedSignature, String secret)
      throws RazorpayException {
    String actualSignature = getHash(payload, secret);
    return isEqual(actualSignature.getBytes(), expectedSignature.getBytes());
  }

  public static String getHash(String payload, String secret) throws RazorpayException {
    Mac sha256HMAC;
    try {
      sha256HMAC = Mac.getInstance("HmacSHA256");
      SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
      sha256HMAC.init(secretKey);
      byte[] hash = sha256HMAC.doFinal(payload.getBytes());
      return new String(Hex.encodeHex(hash));
    } catch (Exception e) {
      throw new RazorpayException(e.getMessage());
    }
  }

  /**
   * We are not using String.equals() method because of security issue mentioned in
   * <a href="http://security.stackexchange.com/a/83670">StackOverflow</a>
   * 
   * @param a
   * @param b
   * @return boolean
   */
  private static boolean isEqual(byte[] a, byte[] b) {
    if (a.length != b.length) {
      return false;
    }
    int result = 0;
    for (int i = 0; i < a.length; i++) {
      result |= a[i] ^ b[i];
    }
    return result == 0;
  }
}
