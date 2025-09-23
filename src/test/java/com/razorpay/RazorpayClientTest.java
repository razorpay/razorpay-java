package com.razorpay;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class RazorpayClientTest extends BaseTest {

    @InjectMocks
    protected RazorpayClient razorpayClient;

    @InjectMocks
    protected RazorpayClient instance;

    {
        try {
            instance = new RazorpayClient("test", "secret");
            razorpayClient = new RazorpayClient(TEST_SECRET_KEY);
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testOrderCreateWithOAuth() {
        JSONObject request = new JSONObject("{" +
                "amount:50000," +
                "currency:\"INR\"," +
                "receipt:\"receipt#1\"," +
                "notes:" +
                "{key1:\"value3\"," +
                "key2:\"value2\"}}");

        String mockedResponseJson = "{" +
                "\"id\":\"order_EKwxwAgItmmXdp\"," +
                "\"entity\":\"order\"," +
                "\"amount\":50000," +
                "\"amount_paid\":0," +
                "\"amount_due\":50000," +
                "\"currency\":\"INR\"," +
                "\"receipt\":\"receipt#1\"," +
                "\"offer_id\":null," +
                "\"status\":\"created\"," +
                "\"attempts\":0," +
                "\"notes\":[]," +
                "\"created_at\":1582628071}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Order fetch = razorpayClient.orders.create(request);
            assertNotNull(fetch);
            assertEquals("order_EKwxwAgItmmXdp",fetch.get("id"));
            assertEquals("order",fetch.get("entity"));
            String createRequest = getHost(Constants.ORDER_CREATE);

            Map<String, String> headers = new HashMap<>();
            headers.put(Constants.AUTH_HEADER_KEY, "Bearer " + TEST_SECRET_KEY);
            verifySendRequestAndHeaders(request.toString(), createRequest, headers);
        } catch (IOException | RazorpayException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testOrderCreationWithApiKeyAndSecret() {
        JSONObject request = new JSONObject("{" +
                "amount:50000," +
                "currency:\"INR\"," +
                "receipt:\"receipt#1\"," +
                "notes:" +
                "{key1:\"value3\"," +
                "key2:\"value2\"}}");

        String mockedResponseJson = "{" +
                "\"id\":\"order_EKwxwAgItmmXdp\"," +
                "\"entity\":\"order\"," +
                "\"amount\":50000," +
                "\"amount_paid\":0," +
                "\"amount_due\":50000," +
                "\"currency\":\"INR\"," +
                "\"receipt\":\"receipt#1\"," +
                "\"offer_id\":null," +
                "\"status\":\"created\"," +
                "\"attempts\":0," +
                "\"notes\":[]," +
                "\"created_at\":1582628071}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Order fetch = instance.orders.create(request);
            assertNotNull(fetch);
            assertEquals("order_EKwxwAgItmmXdp",fetch.get("id"));
            assertEquals("order",fetch.get("entity"));
            String createRequest = getHost(Constants.ORDER_CREATE);

            Map<String, String> headers = new HashMap<>();
            headers.put(Constants.AUTH_HEADER_KEY, "Basic dGVzdDpzZWNyZXQ=");
            verifySendRequestAndHeaders(request.toString(), createRequest, headers);
        } catch (IOException | RazorpayException e) {
            assertTrue(false);
        }
    }
}
