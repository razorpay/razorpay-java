package com.razorpay;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;

import static org.junit.Assert.*;

public class IinClientTest extends BaseTest{
    @InjectMocks
    protected IinClient iinClient = new IinClient(TEST_SECRET_KEY);

    private static final String TOKEN_IIN = "412345";

    @Test
    public void fetch() throws RazorpayException {

        String mockedResponseJson = "{\n" +
                "  \"iin\": \"412345\",\n" +
                "  \"entity\": \"iin\",\n" +
                "  \"network\": \"Visa\",\n" +
                "  \"type\": \"credit\",\n" +
                "  \"sub_type\": \"business\",\n" +
                "  \"issuer_code\": \"HDFC\",\n" +
                "  \"issuer_name\": \"HDFC Bank Ltd\",\n" +
                "  \"international\": false,\n" +
                "  \"is_tokenized\": true,\n" +
                "  \"card_iin\": \"411111\",\n" +
                "  \"emi\":{\n" +
                "     \"available\": true\n" +
                "     },\n" +
                "  \"recurring\": {\n" +
                "     \"available\": true\n" +
                "     },\n" +
                "  \"authentication_types\": [\n" +
                "   {\n" +
                "       \"type\":\"3ds\"\n" +
                "   },\n" +
                "   {\n" +
                "       \"type\":\"otp\"\n" +
                "   }\n" +
                "  ]\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Iin fetch = iinClient.fetch(TOKEN_IIN);
            assertNotNull(fetch);
            assertEquals(TOKEN_IIN,fetch.get("iin"));
            String fetchRequest = getHost(String.format(Constants.IIN_FETCH, TOKEN_IIN));
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void fetchList() throws RazorpayException {

        JSONObject request = new JSONObject();
        request.put("flow","otp");

        JSONObject mockedResponseJson = new JSONObject();
        mockedResponseJson.put("count", 0);
        mockedResponseJson.put("entity", "iin");
        JSONArray iinArray = new JSONArray();
        mockedResponseJson.put("iins", iinArray);

        try {
            mockResponseFromExternalClient(mockedResponseJson.toString());
            mockResponseHTTPCodeFromExternalClient(200);
            Iin fetch = iinClient.fetchList(request);
            assertNotNull(fetch);
            assertEquals(true,fetch.has("iins"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}
