package com.razorpay;

import okhttp3.ResponseBody;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
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
            "\"metadata\":{}," +
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
}
