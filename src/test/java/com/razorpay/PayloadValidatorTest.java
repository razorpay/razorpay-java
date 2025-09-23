package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PayloadValidatorTest {

    private PayloadValidator payloadValidator = new PayloadValidator();

    @Test
    public void testValidateMode() {
        JSONObject payload = new JSONObject();
        payload.put("mode1", "test");
        payload.put("mode2", "live");
        try {
            payloadValidator.validate(payload, Arrays.asList(
                    new ValidationConfig("mode1", Collections.singletonList(ValidationType.MODE)),
                    new ValidationConfig("mode2", Collections.singletonList(ValidationType.MODE))));
        } catch (RazorpayException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testModeValidationFailure() {
        JSONObject payload = new JSONObject();
        payload.put("mode", "testvalue");
        try {
            payloadValidator.validate(payload, Collections.singletonList(
                    new ValidationConfig("mode", Collections.singletonList(ValidationType.MODE))));
        } catch (RazorpayException e) {
            assertEquals("Invalid value provided for field mode", e.getMessage());
        }
    }

    @Test
    public void testUrlValidationFailure() {
        JSONObject payload = new JSONObject();
        payload.put("redirect_uri", "test.com");
        try {
            payloadValidator.validate(payload, Collections.singletonList(
                    new ValidationConfig("redirect_uri", Collections.singletonList(ValidationType.URL))));
        } catch (RazorpayException e) {
            assertEquals("Field redirect_uri is not a valid URL", e.getMessage());
        }
    }

    @Test
    public void testNonNullValidationFailure() {
        JSONObject payload = new JSONObject();
        try {
            payloadValidator.validate(payload, Collections.singletonList(
                    new ValidationConfig("redirect_uri", Collections.singletonList(ValidationType.NON_NULL))));
        } catch (RazorpayException e) {
            assertEquals("Field redirect_uri cannot be null", e.getMessage());
        }
    }

    @Test
    public void testIDValidationFailure() {
        JSONObject payload = new JSONObject();
        payload.put("client_id", "fjidhf");
        try {
            payloadValidator.validate(payload, Collections.singletonList(
                    new ValidationConfig("client_id", Collections.singletonList(ValidationType.ID))));
        } catch (RazorpayException e) {
            assertEquals("Invalid value provided for field client_id", e.getMessage());
        }
    }
}
