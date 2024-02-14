package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class UtilsTest {

    /**
     * Verify razorpay payment signature
     * @throws RazorpayException
     */
    @Test
    public void verifyPaymentSignature() throws RazorpayException{
        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", "order_IEIaMR65cu6nz3");
        options.put("razorpay_payment_id", "pay_IH4NVgf4Dreq1l");
        options.put("razorpay_signature", "0d4e745a1838664ad6c9c9902212a32d627d68e917290b0ad5f08ff4561bc50f");

        assertTrue(Utils.verifyPaymentSignature(options, "EnLs21M47BllR3X8PSFtjtbd"));
    }

    /**
     * Verify razorpay subscription
     * @throws RazorpayException
     */
    @Test
    public void verifySubscription() throws RazorpayException{
        JSONObject options = new JSONObject();
        options.put("razorpay_subscription_id", "sub_ID6MOhgkcoHj9I");
        options.put("razorpay_payment_id", "pay_IDZNwZZFtnjyym");
        options.put("razorpay_signature", "601f383334975c714c91a7d97dd723eb56520318355863dcf3821c0d07a17693");

        assertTrue(Utils.verifySubscription(options, "EnLs21M47BllR3X8PSFtjtbd"));
    }

    /**
     * Verify razorpay payment link signature
     * @throws RazorpayException
     */
    @Test
    public void verifyPaymentLink() throws RazorpayException{
        JSONObject options = new JSONObject();
        options.put("payment_link_reference_id", "TSsd1989");
        options.put("razorpay_payment_id", "pay_IH3d0ara9bSsjQ");
        options.put("payment_link_status", "paid");
        options.put("payment_link_id", "plink_IH3cNucfVEgV68");
        options.put("razorpay_signature", "07ae18789e35093e51d0a491eb9922646f3f82773547e5b0f67ee3f2d3bf7d5b");

        assertTrue(Utils.verifyPaymentLink(options, "EnLs21M47BllR3X8PSFtjtbd"));
    }

    /**
     * Verify razorpay webhook signature
     * @throws RazorpayException
     */
    @Test
    public void verifyWebhookSignature() throws RazorpayException{
        String signature = "2fe04e22977002e6c7cb553adab8b460cb9e2a4970d5953cb27a8472752e3bbc";
        String payload = "{\"a\":1,\"b\":2,\"c\":{\"d\":3}}";
        String secret = "123456";
        assertTrue(Utils.verifyWebhookSignature(payload,signature, secret));
    }

    @Test
    public void testGenerateOnboardingSignature() throws RazorpayException {
        long timestamp = System.currentTimeMillis();
        JSONObject options = new JSONObject();
        options.put("submerchant_id", "NSgKfYIR2f9v2y");
        options.put("timestamp", timestamp);
        String secret = "EnLs21M47BllR3X8PSFtjtbd";
        String encryptedHexData = Utils.generateOnboardingSignature(options, secret);
        try {
            String decryptedData = decryptData(encryptedHexData, secret);
            JSONObject data = new JSONObject(decryptedData);
            assertEquals("NSgKfYIR2f9v2y", data.getString("submerchant_id"));
            assertEquals(timestamp, data.get("timestamp"));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    private String decryptData(String encryptedHexData, String secret) throws Exception {
        byte[] encryptedData = hexStringToByteArray(encryptedHexData);
        return decrypt(encryptedData, secret);
    }
    public static String decrypt(byte[] encryptedData, String secret) throws Exception {
        byte[] keyBytes = secret.substring(0, 16).getBytes(StandardCharsets.UTF_8);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        byte[] iv = new byte[12];
        System.arraycopy(keyBytes, 0, iv, 0, 12);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedData);
        return new String(decryptedBytes);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}