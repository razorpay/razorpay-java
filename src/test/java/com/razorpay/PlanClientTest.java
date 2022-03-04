package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class PlanClientTest extends BaseTest{

    @InjectMocks
    protected PlanClient planClient = new PlanClient(TEST_SECRET_KEY);

    private static final String PLAN_ID = "plan_00000000000001";
    
    /**
     * Create plan with currency and amount details
     * @throws RazorpayException
     */
    @Test
    public void create() throws RazorpayException{
        JSONObject request = new JSONObject("{\n" +
                "  period: \"weekly\",\n" +
                "  interval: 1,\n" +
                "  item: {\n" +
                "    name: \"Test plan - Weekly\",\n" +
                "    amount: 69900,\n" +
                "    currency: \"INR\",\n" +
                "    description: \"Description for the test plan\"\n" +
                "  },\n" +
                "  notes: {\n" +
                "    notes_key_1: \"Tea, Earl Grey, Hot\",\n" +
                "    notes_key_2: \"Tea, Earl Grey… decaf.\"\n" +
                "  }\n" +
                "}");

        String mockedResponseJson = "{\n" +
                "  \"id\":"+PLAN_ID+",\n" +
                "  \"entity\":\"plan\",\n" +
                "  \"interval\":1,\n" +
                "  \"period\":\"weekly\",\n" +
                "  \"item\":{\n" +
                "    \"id\":\"item_00000000000001\",\n" +
                "    \"active\":true,\n" +
                "    \"name\":\"Test plan - Weekly\",\n" +
                "    \"description\":\"Description for the test plan - Weekly\",\n" +
                "    \"amount\":69900,\n" +
                "    \"unit_amount\":69900,\n" +
                "    \"currency\":\"INR\",\n" +
                "    \"type\":\"plan\",\n" +
                "    \"unit\":null,\n" +
                "    \"tax_inclusive\":false,\n" +
                "    \"hsn_code\":null,\n" +
                "    \"sac_code\":null,\n" +
                "    \"tax_rate\":null,\n" +
                "    \"tax_id\":null,\n" +
                "    \"tax_group_id\":null,\n" +
                "    \"created_at\":1580219935,\n" +
                "    \"updated_at\":1580219935\n" +
                "  },\n" +
                "  \"notes\":{\n" +
                "    \"notes_key_1\":\"Tea, Earl Grey, Hot\",\n" +
                "    \"notes_key_2\":\"Tea, Earl Grey… decaf.\"\n" +
                "  },\n" +
                "  \"created_at\":1580219935\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Plan fetch = planClient.create(request);
            assertNotNull(fetch);
            JSONObject item = fetch.toJson().getJSONObject("item");
            assertEquals(PLAN_ID,fetch.get("id"));
            assertEquals("plan",fetch.get("entity"));
            assertTrue(item.has("amount"));
            assertTrue(item.has("unit_amount"));
            String createRequest = getHost(Constants.PLAN_CREATE);
            verifySentRequest(true, request.toString(), createRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Retrieve all the Plans details using plan id.
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws RazorpayException{

     String json = "{\n" +
             "  \"id\":"+PLAN_ID+",\n" +
             "  \"entity\":\"plan\",\n" +
             "  \"interval\":1,\n" +
             "  \"period\":\"weekly\",\n" +
             "  \"item\":{\n" +
             "    \"id\":\"item_00000000000001\",\n" +
             "    \"active\":true,\n" +
             "    \"name\":\"Test plan - Weekly\",\n" +
             "    \"description\":\"Description for the test plan - Weekly\",\n" +
             "    \"amount\":69900,\n" +
             "    \"unit_amount\":69900,\n" +
             "    \"currency\":\"INR\",\n" +
             "    \"type\":\"plan\",\n" +
             "    \"unit\":null,\n" +
             "    \"tax_inclusive\":false,\n" +
             "    \"hsn_code\":null,\n" +
             "    \"sac_code\":null,\n" +
             "    \"tax_rate\":null,\n" +
             "    \"tax_id\":null,\n" +
             "    \"tax_group_id\":null,\n" +
             "    \"created_at\":1580220492,\n" +
             "    \"updated_at\":1580220492\n" +
             "  },\n" +
             "  \"notes\":{\n" +
             "    \"notes_key_1\":\"Tea, Earl Grey, Hot\",\n" +
             "    \"notes_key_2\":\"Tea, Earl Grey… decaf.\"\n" +
             "  },\n" +
             "  \"created_at\":1580220492\n" +
             "}";
        try {
            mockResponseFromExternalClient(json);
            mockResponseHTTPCodeFromExternalClient(200);
            Plan fetch = planClient.fetch(PLAN_ID);
            assertNotNull(fetch);
            JSONObject item = fetch.toJson().getJSONObject("item");
            assertEquals(PLAN_ID,fetch.get("id"));
            assertEquals("INR",item.get("currency"));
            assertEquals(69900,item.get("amount"));
            assertTrue(item.getBoolean("active"));
            assertTrue(item.has("unit_amount"));
            String planFetch = getHost(String.format(Constants.PLAN_GET, PLAN_ID));
            verifySentRequest(false, null, planFetch);
        } catch (IOException e) {
            assertTrue(false);
        }
    }
    
    /**
     * Details of all the plans can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws RazorpayException{

        String json = "{\n" +
                "  \"entity\": \"collection\",\n" +
                "  \"count\": 1,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": "+PLAN_ID+",\n" +
                "      \"entity\": \"plan\",\n" +
                "      \"interval\": 1,\n" +
                "      \"period\": \"weekly\",\n" +
                "      \"item\": {\n" +
                "        \"id\": \"item_00000000000001\",\n" +
                "        \"active\": true,\n" +
                "        \"name\": \"Test plan - Weekly\",\n" +
                "        \"description\": \"Description for the test plan - Weekly\",\n" +
                "        \"amount\": 69900,\n" +
                "        \"unit_amount\": 69900,\n" +
                "        \"currency\": \"INR\",\n" +
                "        \"type\": \"plan\",\n" +
                "        \"unit\": null,\n" +
                "        \"tax_inclusive\": false,\n" +
                "        \"hsn_code\": null,\n" +
                "        \"sac_code\": null,\n" +
                "        \"tax_rate\": null,\n" +
                "        \"tax_id\": null,\n" +
                "        \"tax_group_id\": null,\n" +
                "        \"created_at\": 1580220492,\n" +
                "        \"updated_at\": 1580220492\n" +
                "      },\n" +
                "      \"notes\": {\n" +
                "        \"notes_key_1\": \"Tea, Earl Grey, Hot\",\n" +
                "        \"notes_key_2\": \"Tea, Earl Grey… decaf.\"\n" +
                "      },\n" +
                "      \"created_at\": 1580220492\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {
            mockResponseFromExternalClient(json);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Plan> fetch = planClient.fetchAll();
            assertNotNull(fetch);
            assertEquals(true,fetch.get(0).has("id"));
            assertEquals(true,fetch.get(0).has("entity"));
            assertEquals(true,fetch.get(0).has("item"));
            assertEquals(true,fetch.get(0).toJson().getJSONObject("item").has("hsn_code"));
            String planList = getHost(Constants.PLAN_LIST);
            verifySentRequest(false, null, planList);
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}