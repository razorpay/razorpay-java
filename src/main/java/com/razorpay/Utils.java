package com.razorpay;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
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
    try {
      byte[] keyBytes = secret.substring(0, 16).getBytes(StandardCharsets.UTF_8);
      SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

      // Generate a fresh random 12-byte nonce per call (fixes AES-GCM nonce reuse).
      // A static IV derived from the key allows keystream recovery and tag forgery
      // (NIST SP 800-38D §8.3 Forbidden Attack) using only two captured ciphertexts.
      byte[] iv = new byte[12];
      new SecureRandom().nextBytes(iv);

      Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
      GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);
      // In Java, doFinal() appends the 16-byte GCM auth tag to the ciphertext.
      byte[] encryptedData = cipher.doFinal(dataToEncrypt.getBytes(StandardCharsets.UTF_8));

      // Output format: iv (12 bytes) || ciphertext || tag (16 bytes), hex-encoded.
      // Receiver must read the first 24 hex chars as the IV before decrypting.
      byte[] combined = new byte[iv.length + encryptedData.length];
      System.arraycopy(iv, 0, combined, 0, iv.length);
      System.arraycopy(encryptedData, 0, combined, iv.length, encryptedData.length);
      return bytesToHex(combined);
    }
    catch (Exception e) {
      throw new RazorpayException(e.getMessage());
    }
  }

  public static String bytesToHex(byte[] bytes) {
    StringBuilder hexString = new StringBuilder();
    for (byte b : bytes) {
      String hex = Integer.toHexString(0xff & b);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
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
