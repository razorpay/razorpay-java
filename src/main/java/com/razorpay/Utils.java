package com.razorpay;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
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

  public static String generateOnboardingSignature(JSONObject attributes, String secret) throws RazorpayException {
    String jsonString = attributes.toString();
    return encrypt(jsonString, secret);
  }

  public static String encrypt(String dataToEncrypt, String secret) throws RazorpayException {
    byte[] key = secret.getBytes(StandardCharsets.UTF_8);
    byte[] iv = secret.getBytes(StandardCharsets.UTF_8);
    SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
    IvParameterSpec ivParameterSpec = new IvParameterSpec(iv, 0, 16);
    try {
      // Initialize the Cipher for encryptio
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
      byte[] encryptedBytes = cipher.doFinal(dataToEncrypt.getBytes(StandardCharsets.UTF_8));
      return bytesToHex(encryptedBytes);
    }
    catch (Exception e) {
      throw new RazorpayException(e.getMessage());
    }
  }

  public static String bytesToHex(byte[] bytes) {
    StringBuilder hexBuilder = new StringBuilder();
    for (byte b : bytes) {
      // Convert the byte to an int (considering the byte to be unsigned)
      int unsignedByte = b & 0xFF;

      // Convert the int to a hex string and append to the builder
      // The format "%02x" ensures the hex string is zero-padded to two characters
      hexBuilder.append(String.format("%02x", unsignedByte));
    }
    return hexBuilder.toString();
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
