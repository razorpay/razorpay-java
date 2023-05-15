package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TokenClientTest extends BaseTest{
    @InjectMocks
    protected TokenClient tokenClient = new TokenClient(TEST_SECRET_KEY);
    private static final String TOKEN_ID = "token_4lsdksD31GaZ09x";

    private static final String SERVICE_PROVIDER_TOKEN_ID = "spt_4lsdksD31GaZ09";

    @Test
    public void testTokenCreate() throws RazorpayException{
        JSONObject request = new JSONObject("{\n" +
                "  \"customer_id\": \"cust_1Aa00000000001\",\n" +
                "  \"method\": \"card\",\n" +
                "  \"card\": {\n" +
                "    \"number\": \"4111111111111111\",\n" +
                "    \"cvv\": \"123\",\n" +
                "    \"expiry_month\": \"12\",\n" +
                "    \"expiry_year\": \"21\",\n" +
                "    \"name\": \"Gaurav Kumar\"\n" +
                "  },\n" +
                "  \"authentication\": {\n" +
                "    \"provider\": \"razorpay\",\n" +
                "    \"provider_reference_id\": \"pay_123wkejnsakd\",\n" +
                "    \"authentication_reference_number\": \"100222021120200000000742753928\"   \n" +
                "  }\n" +
                "}");

        String mockedResponseJson = getResponse(TOKEN_ID);
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Token fetch = tokenClient.create(request);
            assertNotNull(fetch);
            assertEquals(TOKEN_ID,fetch.get("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testTokenFetch() throws RazorpayException {
        String mockedResponseJson = getResponse(TOKEN_ID);
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            JSONObject request = new JSONObject();
            request.put("id",TOKEN_ID);
            Token fetch = tokenClient.fetch(request);
            assertNotNull(fetch);
            assertEquals(TOKEN_ID,fetch.get("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testTokenDelete() throws RazorpayException {
        JSONObject request = new JSONObject();
        request.put("id",TOKEN_ID);
        ArrayList<String> mockedResponseJson = new ArrayList<String>();
        try {
            mockResponseFromExternalClient(String.valueOf(mockedResponseJson));
            mockResponseHTTPCodeFromExternalClient(200);
            List<Token> fetch = tokenClient.delete(request);
            assertNotNull(fetch);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testProcessPaymentOnAlternatePAorPG() throws RazorpayException {
        String mockedResponseJson = "{\n" +
                "  \"entity\": \"token\",\n" +
                "  \"token_number\": \"4016981500100002\",\n" +
                "  \"cryptogram_value\": \"a345345dfgdfasdfh45jtyhgjkyutsdasd2\",\n" +
                "  \"token_expiry_month\": 12,\n" +
                "  \"token_expiry_year\": 2021\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            JSONObject request = new JSONObject();
            request.put("id",TOKEN_ID);
            Token fetch = tokenClient.processPaymentOnAlternatePAorPG(request);
            assertNotNull(fetch);
            assertEquals(true,fetch.has("token_number"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    private String getResponse(String token_id) {
        return "{\n" +
                "  \"id\": "+token_id+",\n" +
                "  \"entity\": \"token\",\n" +
                "  \"customer_id\": \"cust_1Aa00000000001\",\n" +
                "  \"method\": \"card\",\n" +
                "  \"card\": {\n" +
                "    \"last4\": \"3335\",\n" +
                "    \"network\": \"Visa\",\n" +
                "    \"type\": \"debit\",\n" +
                "    \"issuer\": \"HDFC\",\n" +
                "    \"international\": false,\n" +
                "    \"emi\": true,\n" +
                "    \"sub_type\": \"consumer\",\n" +
                "    \"token_iin\": \"453335\"\n" +
                "  },\n" +
                "  \"compliant_with_tokenisation_guidelines\": true,\n" +
                "  \"service_provider_tokens\": [\n" +
                "    {\n" +
                "      \"id\": \"spt_1234abcd\",\n" +
                "      \"entity\": \"service_provider_token\",\n" +
                "      \"provider_type\": \"network\",\n" +
                "      \"provider_name\": \"Visa\",\n" +
                "      \"interoperable\": true,\n" +
                "      \"status\": \"active\",\n" +
                "      \"status_reason\": null,\n" +
                "      \"provider_data\": {\n" +
                "        \"token_reference_number\": \"sas7wqaoidasdfssdjjk\",\n" +
                "        \"payment_account_reference\": \"8324ssdas7wqaoidassdjjk\",\n" +
                "        \"token_iin\": \"453335\",\n" +
                "        \"token_expiry_month\": 12,\n" +
                "        \"token_expiry_year\": 2021\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"expired_at\": 1748716199,\n" +
                "  \"status\": \"active\",\n" +
                "  \"status_reason\": null,\n" +
                "  \"notes\": []\n" +
                "}";
    }
}