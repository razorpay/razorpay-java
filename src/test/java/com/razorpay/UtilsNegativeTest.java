package com.razorpay;

import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static org.junit.Assert.*;

public class UtilsNegativeTest {

    private static final String WEBHOOK_PAYLOAD = "{\"event\":\"payment.authorized\"}";
    private static final String WEBHOOK_SECRET = "test_webhook_secret";

    private String computeSignature(String payload, String secret) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secretKey);
        byte[] hash = sha256_HMAC.doFinal(payload.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Test
    public void testEmptySignatureRejected() throws RazorpayException {
        boolean result = Utils.verifyWebhookSignature(WEBHOOK_PAYLOAD, "", WEBHOOK_SECRET);
        assertFalse("Empty signature should be rejected", result);
    }

    @Test
    public void testWrongLengthSignatureRejected() throws RazorpayException {
        boolean result = Utils.verifyWebhookSignature(WEBHOOK_PAYLOAD, "abc123", WEBHOOK_SECRET);
        assertFalse("Wrong length signature should be rejected", result);
    }

    @Test
    public void testNonHexSignatureRejected() throws RazorpayException {
        String nonHexSig = new String(new char[64]).replace('\0', 'z');
        boolean result = Utils.verifyWebhookSignature(WEBHOOK_PAYLOAD, nonHexSig, WEBHOOK_SECRET);
        assertFalse("Non-hex signature should be rejected", result);
    }

    @Test
    public void testTamperedValidHexSignatureRejected() throws RazorpayException {
        String tamperedSig = new String(new char[64]).replace('\0', 'a');
        boolean result = Utils.verifyWebhookSignature(WEBHOOK_PAYLOAD, tamperedSig, WEBHOOK_SECRET);
        assertFalse("Tampered signature should be rejected", result);
    }

    @Test
    public void testValidDynamicSignatureAccepted() throws Exception {
        String validSig = computeSignature(WEBHOOK_PAYLOAD, WEBHOOK_SECRET);
        boolean result = Utils.verifyWebhookSignature(WEBHOOK_PAYLOAD, validSig, WEBHOOK_SECRET);
        assertTrue("Valid signature should be accepted", result);
    }

    @Test
    public void testSpecialCharsInPayload() throws Exception {
        String specialPayload = "{\"event\":\"payment\",\"data\":{\"notes\":\"Test & <script>alert(1)</script>\"}}";
        String validSig = computeSignature(specialPayload, WEBHOOK_SECRET);
        boolean result = Utils.verifyWebhookSignature(specialPayload, validSig, WEBHOOK_SECRET);
        assertTrue("Special chars payload should verify", result);
    }

    @Test
    public void testUnicodeInPayload() throws Exception {
        String unicodePayload = "{\"event\":\"payment\",\"data\":{\"name\":\"日本語テスト\"}}";
        String validSig = computeSignature(unicodePayload, WEBHOOK_SECRET);
        boolean result = Utils.verifyWebhookSignature(unicodePayload, validSig, WEBHOOK_SECRET);
        assertTrue("Unicode payload should verify", result);
    }
}
