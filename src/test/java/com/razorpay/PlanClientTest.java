package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class PlanClientTest extends BaseTest{

    private static final String PLAN_ID = "plan_00000000000001";

    @InjectMocks
    protected PlanClient planClient = new PlanClient(TEST_SECRET_KEY);

    /**
     * Plan is created using the amount and billing details.
     * @throws RazorpayException
     */
    @Test
    public void create() throws RazorpayException{

        JSONObject request = new JSONObject("{period:\"weekly\",interval:1,item:{name:\"Testplan-Weekly\",amount:69900,currency:\"INR\",description:\"Descriptionforthetestplan\"},notes:{notes_key_1:\"Tea,EarlGrey,Hot\",notes_key_2:\"Tea,EarlGrey…decaf.\"}}");
        String mockedResponseJson = "{\"id\":"+PLAN_ID+",\"entity\":\"plan\",\"interval\":1,\"period\":\"weekly\",\"item\":{\"id\":\"item_00000000000001\",\"active\":true,\"name\":\"Testplan-Weekly\",\"description\":\"Descriptionforthetestplan-Weekly\",\"amount\":69900,\"unit_amount\":69900,\"currency\":\"INR\",\"type\":\"plan\",\"unit\":null,\"tax_inclusive\":false,\"hsn_code\":null,\"sac_code\":null,\"tax_rate\":null,\"tax_id\":null,\"tax_group_id\":null,\"created_at\":1580219935,\"updated_at\":1580219935},\"notes\":{\"notes_key_1\":\"Tea,EarlGrey,Hot\",\"notes_key_2\":\"Tea,EarlGrey…decaf.\"},\"created_at\":1580219935}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Plan fetch = planClient.create(request);
            assertNotNull(fetch);
            assertEquals(PLAN_ID,fetch.get("id"));
            assertEquals("plan",fetch.get("entity"));
            assertEquals("weekly",fetch.get("period"));
            assertTrue(fetch.has("item"));
            assertTrue(fetch.has("notes"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Retrieve the plan details using plan id.
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws RazorpayException{

        String mockedResponseJson = "{\"id\":"+PLAN_ID+",\"entity\":\"plan\",\"interval\":1,\"period\":\"weekly\",\"item\":{\"id\":\"item_00000000000001\",\"active\":true,\"name\":\"Testplan-Weekly\",\"description\":\"Descriptionforthetestplan-Weekly\",\"amount\":69900,\"unit_amount\":69900,\"currency\":\"INR\",\"type\":\"plan\",\"unit\":null,\"tax_inclusive\":false,\"hsn_code\":null,\"sac_code\":null,\"tax_rate\":null,\"tax_id\":null,\"tax_group_id\":null,\"created_at\":1580219935,\"updated_at\":1580219935},\"notes\":{\"notes_key_1\":\"Tea,EarlGrey,Hot\",\"notes_key_2\":\"Tea,EarlGrey…decaf.\"},\"created_at\":1580219935}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Plan fetch = planClient.fetch(PLAN_ID);
            assertNotNull(fetch);
            assertEquals(PLAN_ID,fetch.get("id"));
            assertEquals("plan",fetch.get("entity"));
            assertEquals("weekly",fetch.get("period"));
            assertTrue(fetch.has("item"));
            assertTrue(fetch.has("notes"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Details of all the plans can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws Exception{

       String mockedResponseJson = "{\"entity\":\"collection\",\"count\":1,\"items\":[{\"id\":\"plan_00000000000001\",\"entity\":\"plan\",\"interval\":1,\"period\":\"weekly\",\"item\":{\"id\":\"item_00000000000001\",\"active\":true,\"name\":\"Testplan-Weekly\",\"description\":\"Descriptionforthetestplan-Weekly\",\"amount\":69900,\"unit_amount\":69900,\"currency\":\"INR\",\"type\":\"plan\",\"unit\":null,\"tax_inclusive\":false,\"hsn_code\":null,\"sac_code\":null,\"tax_rate\":null,\"tax_id\":null,\"tax_group_id\":null,\"created_at\":1580220492,\"updated_at\":1580220492},\"notes\":{\"notes_key_1\":\"Tea,EarlGrey,Hot\",\"notes_key_2\":\"Tea,EarlGrey…decaf.\"},\"created_at\":1580220492}]}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Plan> fetch = planClient.fetchAll();
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("id"));
            assertTrue(fetch.get(0).has("entity"));
            assertTrue(fetch.get(0).has("interval"));
            assertTrue(fetch.get(0).has("period"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}