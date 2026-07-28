package com.razorpay;

import okhttp3.ResponseBody;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RazorpayExceptionTest extends BaseTest {

    @InjectMocks
    protected OrderClient orderClient = new OrderClient(TEST_SECRET_KEY);

    private static final String ERROR_RESPONSE_JSON = "{" +
            "\"error\":{" +
            "\"code\":\"BAD_REQUEST_ERROR\"," +
            "\"description\":\"The amount must be at least 100\"," +
            "\"source\":\"business\"," +
            "\"step\":\"payment_initiation\"," +
            "\"reason\":\"input_validation_failed\"," +
            "\"metadata\":{" +
            "\"payment_id\":\"pay_EDNBKIP31Y4jl8\"," +
            "\"order_id\":\"order_DBJKIP31Y4jl8\"" +
            "}," +
            "\"field\":\"amount\"" +
            "}}";

    private void mockNonJsonResponse(String rawBody) {
        ResponseBody rb = mock(ResponseBody.class);
        try {
            when(rb.string()).thenReturn(rawBody);
        } catch (IOException e) {
            fail("Mock setup failed");
        }
        when(mockedResponse.body()).thenReturn(rb);
    }

    @Test
    public void testStructuredErrorResponseIsAvailable() {
        try {
            mockResponseFromExternalClient(ERROR_RESPONSE_JSON);
            mockResponseHTTPCodeFromExternalClient(400);
            mockURL(Collections.singletonList("orders"));
            orderClient.create(new JSONObject("{\"amount\":50,\"currency\":\"INR\"}"));
            fail("Expected RazorpayException");
        } catch (RazorpayException e) {
            assertNotNull(e.getErrorResponse());
            JSONObject error = e.getErrorResponse();
            assertEquals("BAD_REQUEST_ERROR", error.getString("code"));
            assertEquals("The amount must be at least 100", error.getString("description"));
            assertEquals("business", error.getString("source"));
            assertEquals("payment_initiation", error.getString("step"));
            assertEquals("input_validation_failed", error.getString("reason"));
            assertEquals("amount", error.getString("field"));
            assertTrue(error.has("metadata"));
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testStatusCodeIsAvailable() {
        try {
            mockResponseFromExternalClient(ERROR_RESPONSE_JSON);
            mockResponseHTTPCodeFromExternalClient(400);
            mockURL(Collections.singletonList("orders"));
            orderClient.create(new JSONObject("{\"amount\":50,\"currency\":\"INR\"}"));
            fail("Expected RazorpayException");
        } catch (RazorpayException e) {
            assertEquals(400, e.getStatusCode());
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testMessageIsBackwardCompatible() {
        try {
            mockResponseFromExternalClient(ERROR_RESPONSE_JSON);
            mockResponseHTTPCodeFromExternalClient(400);
            mockURL(Collections.singletonList("orders"));
            orderClient.create(new JSONObject("{\"amount\":50,\"currency\":\"INR\"}"));
            fail("Expected RazorpayException");
        } catch (RazorpayException e) {
            assertEquals("BAD_REQUEST_ERROR:The amount must be at least 100", e.getMessage());
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testServerExceptionHasStatusCode() {
        try {
            mockResponseFromExternalClient("{\"unrecognized\":\"body\"}");
            mockResponseHTTPCodeFromExternalClient(500);
            mockURL(Collections.singletonList("orders"));
            orderClient.create(new JSONObject("{\"amount\":50,\"currency\":\"INR\"}"));
            fail("Expected RazorpayException");
        } catch (RazorpayException e) {
            assertEquals(500, e.getStatusCode());
            assertNull(e.getErrorResponse());
            assertTrue(e.getMessage().contains("Status Code: 500"));
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testNonJsonResponseDoesNotCrash() {
        try {
            mockNonJsonResponse("<html><body>503 Service Unavailable</body></html>");
            mockResponseHTTPCodeFromExternalClient(503);
            mockURL(Collections.singletonList("orders"));
            orderClient.create(new JSONObject("{\"amount\":50,\"currency\":\"INR\"}"));
            fail("Expected RazorpayException");
        } catch (RazorpayException e) {
            assertEquals(503, e.getStatusCode());
            assertNull(e.getErrorResponse());
            assertTrue(e.getMessage().contains("Unable to parse response"));
        }
    }

    @Test
    public void testExceptionConstructorsDefaults() {
        RazorpayException ex1 = new RazorpayException("test message");
        assertEquals("test message", ex1.getMessage());
        assertEquals(0, ex1.getStatusCode());
        assertNull(ex1.getErrorResponse());

        RazorpayException ex2 = new RazorpayException("test message", new JSONObject("{\"code\":\"ERR\"}"), 404);
        assertEquals("test message", ex2.getMessage());
        assertEquals(404, ex2.getStatusCode());
        assertNotNull(ex2.getErrorResponse());
        assertEquals("ERR", ex2.getErrorResponse().getString("code"));

        RazorpayException ex3 = new RazorpayException("server error", 502);
        assertEquals("server error", ex3.getMessage());
        assertEquals(502, ex3.getStatusCode());
        assertNull(ex3.getErrorResponse());
    }

    @Test
    public void testExceptionIsSerializable() {
        RazorpayException ex = new RazorpayException(
            "BAD_REQUEST_ERROR:The amount must be at least 100",
            new JSONObject(ERROR_RESPONSE_JSON).getJSONObject("error"),
            400
        );
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(ex);
            oos.close();
        } catch (IOException e) {
            fail("RazorpayException with errorResponse should be serializable: " + e.getMessage());
        }
    }

    @Test
    public void testExceptionSerializableWithoutErrorResponse() {
        RazorpayException ex = new RazorpayException("server error", 502);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(ex);
            oos.close();
        } catch (IOException e) {
            fail("RazorpayException without errorResponse should be serializable: " + e.getMessage());
        }
    }

    @Test
    public void testSafeGettersWithFullError() {
        try {
            mockResponseFromExternalClient(ERROR_RESPONSE_JSON);
            mockResponseHTTPCodeFromExternalClient(400);
            mockURL(Collections.singletonList("orders"));
            orderClient.create(new JSONObject("{\"amount\":50,\"currency\":\"INR\"}"));
            fail("Expected RazorpayException");
        } catch (RazorpayException e) {
            assertEquals("BAD_REQUEST_ERROR", e.getCode());
            assertEquals("The amount must be at least 100", e.getDescription());
            assertEquals("amount", e.getField());
            assertEquals("input_validation_failed", e.getReason());
            assertEquals("business", e.getSource());
            assertEquals("payment_initiation", e.getStep());
            assertNotNull(e.getMetadata());
            assertEquals("pay_EDNBKIP31Y4jl8", e.getMetadata().getString("payment_id"));
            assertEquals("order_DBJKIP31Y4jl8", e.getMetadata().getString("order_id"));
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testSafeGettersWithMissingFieldsReturnNull() {
        String authErrorJson = "{\"error\":{\"code\":\"BAD_REQUEST_ERROR\",\"description\":\"Invalid API Key\"}}";
        try {
            mockResponseFromExternalClient(authErrorJson);
            mockResponseHTTPCodeFromExternalClient(401);
            mockURL(Collections.singletonList("orders"));
            orderClient.create(new JSONObject("{\"amount\":50,\"currency\":\"INR\"}"));
            fail("Expected RazorpayException");
        } catch (RazorpayException e) {
            assertEquals("BAD_REQUEST_ERROR", e.getCode());
            assertEquals("Invalid API Key", e.getDescription());
            assertNull(e.getField());
            assertNull(e.getReason());
            assertNull(e.getSource());
            assertNull(e.getStep());
            assertNull(e.getMetadata());
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testSafeGettersReturnNullWhenNoErrorResponse() {
        RazorpayException ex = new RazorpayException("network error", 503);
        assertNull(ex.getCode());
        assertNull(ex.getDescription());
        assertNull(ex.getField());
        assertNull(ex.getReason());
        assertNull(ex.getSource());
        assertNull(ex.getStep());
        assertNull(ex.getMetadata());
    }
}
