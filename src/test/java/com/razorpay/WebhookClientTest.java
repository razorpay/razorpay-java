package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class WebhookClientTest extends BaseTest{
    @InjectMocks
    protected WebhookClient webhookClient= new WebhookClient(TEST_SECRET_KEY);

    private static final String ACCOUNT_ID = "acc_GRWKk7qQsLnDjX";

    private static final String WEBHOOK_ID = "HK890egfiItP3H";

    @Test
    public void testWebhookCreate() throws RazorpayException{
        JSONObject request = new JSONObject("{\n" +
                "  \"url\": \"https://google.com\",\n" +
                "  \"alert_email\": \"gaurav.kumar@example.com\",\n" +
                "  \"secret\": \"12345\",\n" +
                "  \"events\": [\n" +
                "    \"payment.authorized\",\n" +
                "    \"payment.failed\",\n" +
                "    \"payment.captured\",\n" +
                "    \"payment.dispute.created\",\n" +
                "    \"refund.failed\",\n" +
                "    \"refund.created\"\n" +
                "  ]\n" +
                "}");

        String mockedResponseJson = getResponse(WEBHOOK_ID);
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Webhook fetch = webhookClient.create(ACCOUNT_ID,request);
            assertNotNull(fetch);
            assertEquals(WEBHOOK_ID,fetch.get("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testWebhookFetch() throws RazorpayException {
        String mockedResponseJson = getResponse(WEBHOOK_ID);
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Webhook fetch = webhookClient.fetch(ACCOUNT_ID,WEBHOOK_ID);
            assertNotNull(fetch);
            assertEquals(WEBHOOK_ID,fetch.get("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testWebhookEdit() throws RazorpayException {
        JSONObject request = new JSONObject("{\n" +
                "  \"url\": \"https://www.linkedin.com\",\n" +
                "  \"events\": [\n" +
                "    \"refund.created\"\n" +
                "  ]\n" +
                "}");

        String mockedResponseJson = getResponse(WEBHOOK_ID);
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Webhook fetch = webhookClient.edit(ACCOUNT_ID, WEBHOOK_ID, request);
            assertNotNull(fetch);
            assertEquals(WEBHOOK_ID,fetch.get("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testStakeholderFetchAll() throws RazorpayException {
        try {
            String mockedResponseJson = getResponseCollection();
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Webhook> fetch = webhookClient.fetchAll(ACCOUNT_ID);
            assertNotNull(fetch);
            assertEquals(true,fetch.get(0).has("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    private String getResponse(String webhook_id) {
        return "{\n" +
                "  \"id\": "+webhook_id+",\n" +
                "  \"created_at\": 1654605478,\n" +
                "  \"updated_at\": 1654605478,\n" +
                "  \"service\": \"beta-api-live\",\n" +
                "  \"owner_id\": \"JOGUdtKu3dB03d\",\n" +
                "  \"owner_type\": \"merchant\",\n" +
                "  \"context\": [],\n" +
                "  \"disabled_at\": 0,\n" +
                "  \"url\": \"https://google.com\",\n" +
                "  \"alert_email\": \"gaurav.kumar@example.com\",\n" +
                "  \"secret_exists\": true,\n" +
                "  \"entity\": \"webhook\",\n" +
                "  \"active\": true,\n" +
                "  \"events\": [\n" +
                "    \"payment.authorized\",\n" +
                "    \"payment.failed\",\n" +
                "    \"payment.captured\",\n" +
                "    \"payment.dispute.created\",\n" +
                "    \"refund.failed\",\n" +
                "    \"refund.created\"\n" +
                "  ]\n" +
                "}";
    }

    private String getResponseCollection(){
        return "{\n" +
                "  \"entity\": \"collection\",\n" +
                "  \"count\": 1,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": \"HK890egfiItP3H\",\n" +
                "      \"created_at\": 1624060358,\n" +
                "      \"updated_at\": 1624060358,\n" +
                "      \"service\": \"beta-api-test\",\n" +
                "      \"owner_id\": \"H3kYHQ635sBwXG\",\n" +
                "      \"owner_type\": \"merchant\",\n" +
                "      \"context\": [],\n" +
                "      \"disabled_at\": 0,\n" +
                "      \"url\": \"https://en1mwkqo5ioct.x.pipedream.net\",\n" +
                "      \"alert_email\": \"gaurav.kumar@example.com\",\n" +
                "      \"secret_exists\": true,\n" +
                "      \"entity\": \"webhook\",\n" +
                "      \"active\": true,\n" +
                "      \"events\": [\n" +
                "        \"payment.authorized\",\n" +
                "        \"payment.failed\",\n" +
                "        \"payment.captured\",\n" +
                "        \"payment.dispute.created\",\n" +
                "        \"refund.failed\",\n" +
                "        \"refund.created\"\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
}
