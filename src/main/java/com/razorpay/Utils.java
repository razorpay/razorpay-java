package com.razorpay;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;

public class Utils {

  public static boolean verifyPaymentSignature(JSONObject attributes, String apiSecret)
      throws RazorpayException {
    String expectedSignature = attributes.getString("razorpay_signature");
    String orderId = attributes.getString("razorpay_order_id");
    String paymentId = attributes.getString("razorpay_payment_id");
    String payload = orderId + '|' + paymentId;
    return verifySignature(payload, expectedSignature, apiSecret);
  }

  public static boolean verifySubscription(JSONObject attributes, String apiSecret)
      throws RazorpayException {
    String expectedSignature = attributes.getString("razorpay_signature");
    String subscriptionId = attributes.getString("razorpay_subscription_id");
    String paymentId = attributes.getString("razorpay_payment_id");
    String payload = paymentId + '|' + subscriptionId;
    return verifySignature(payload, expectedSignature, apiSecret);
  }
  
  public static boolean verifyPaymentLink(JSONObject attributes, String apiSecret)
	      throws RazorpayException {
	    String expectedSignature = attributes.getString("razorpay_signature");
	    String paymentLinkStatus = attributes.getString("payment_link_status");
	    String paymentLinkId = attributes.getString("payment_link_id");
	    String paymentLinkRefId = attributes.getString("payment_link_reference_id");
	    String paymentId = attributes.getString("razorpay_payment_id");
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

  public static String generateOnboardingSignature(JSONObject attributes, String secret) {
    long expirationTime = System.currentTimeMillis() + (24 * 60 * 60 * 1000); // 24 hours in milliseconds

    String partnerId = attributes.getString("partner_id");
    String submerchantId = attributes.getString("submerchant_id");

    // Create and sign the token
    return JWT.create()
            .withClaim("partner_id", partnerId)
            .withClaim("submerchant_id", submerchantId)
            .withExpiresAt(new Date(expirationTime))
            .sign(Algorithm.HMAC256(secret));
  }

  public static String getHash(String payload, String secret) throws RazorpayException {
    Mac sha256_HMAC;
    try {
      sha256_HMAC = Mac.getInstance("HmacSHA256");
      SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
      sha256_HMAC.init(secret_key);
      byte[] hash = sha256_HMAC.doFinal(payload.getBytes());
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
