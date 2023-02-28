package com.razorpay;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsTest {


    /**
     * Verify razorpay payment signature
     * @throws RazorpayException
     */
    @Test
    public void verifyPaymentSignature() throws RazorpayException, JSONException {
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
    public void verifySubscription() throws RazorpayException, JSONException {
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
    public void verifyPaymentLink() throws RazorpayException, JSONException {
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

}