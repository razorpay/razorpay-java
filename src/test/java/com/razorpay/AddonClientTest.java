package com.razorpay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class AddonClientTest extends BaseTest{

    @Mock
    ApiUtils apiUtils;

    private static final String ADDON_ID = "ao_00000000000001";

    private static final String CUSTOMER_ID = "cust_1Aa00000000004";

    /**
     * Retrieve all the addon details using addon id.
     * @throws RazorpayException
     */


    @Test
    public void fetch() throws RazorpayException, JSONException, URISyntaxException {

        String response = "{\n  \"id\":"+ADDON_ID+",\n" +
                "\"entity\":\"addon\",\n" +
                "\"item\":{\n" +
                "\"id\":\"item_00000000000001\",\n" +
                "\"active\":true,\n" +
                "\"name\":\"Extra appala (papadum)\",\n" +
                "\"description\":\"1 extra oil fried appala with meals\",\n" +
                "\"amount\":30000,\n" +
                "\"unit_amount\":30000,\n" +
                "\"currency\":\"INR\",\n" +
                "\"type\":\"addon\",\n" +
                "\"unit\":null,\n" +
                "\"tax_inclusive\":false,\n" +
                "\"hsn_code\":null,\n" +
                "\"sac_code\":null,\n" +
                "\"tax_rate\":null,\n" +
                "\"tax_id\":null,\n" +
                "\"tax_group_id\":null,\n" +
                "\"created_at\":1581597318,\n" +
                "\"updated_at\":1581597318\n" +
                "},\n  \"quantity\":2,\n" +
                "\"created_at\":1581597318,\n" +
                "\"subscription_id\":\"sub_00000000000001\",\n" +
                "\"invoice_id\":null\n}";
         try{
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(String.format(Constants.ADDON_GET, "ao_JniYt836HF7aQm"), null);
            mockGetRequest(apiUtils,builder,null, response);

            AddonClient addonClient = new AddonClient("test",apiUtils);

            Addon fetch = addonClient.fetch("ao_JniYt836HF7aQm");

            assertNotNull(fetch);
            JSONObject item = fetch.toJson().getJSONObject("item");
            assertEquals(ADDON_ID,fetch.get("id"));
            assertEquals("INR",item.get("currency"));
            assertEquals(30000,item.get("amount"));
            assertTrue(item.getBoolean("active"));
            assertTrue(item.has("unit_amount"));
        } catch (IOException e) {
            assertTrue(false);
       }
    }

    /**
     * Details of all the addon can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws RazorpayException, JSONException, URISyntaxException {

        String response = "{\n  " +
                "\"entity\": \"collection\",\n" +
                "\"count\": 1,\n" +
                "\"items\": [\n {\n" +
                "\"id\": "+ADDON_ID+",\n" +
                "\"entity\": \"addon\",\n" +
                "\"item\": {\n" +
                "\"id\": \"item_00000000000002\",\n" +
                "\"active\": true,\n" +
                "\"name\": \"Extra sweet\",\n" +
                "\"description\": \"1 extra sweet of the day with meals\",\n" +
                "\"amount\": 90000,\n" +
                "\"unit_amount\": 90000,\n" +
                "\"currency\": \"INR\",\n " +
                "\"type\": \"addon\",\n " +
                "\"unit\": null,\n " +
                "\"tax_inclusive\": false,\n " +
                "\"hsn_code\": 123,\n " +
                "\"sac_code\": null,\n " +
                "\"tax_rate\": null,\n" +
                "\"tax_id\": null,\n  " +
                "\"tax_group_id\": null,\n" +
                "\"created_at\": 1581597318,\n " +
                "\"updated_at\": 1581597318\n" +
                "},\n" +
                "\"quantity\": 1,\n " +
                "\"created_at\": 1581597318,\n" +
                "\"subscription_id\": \"sub_00000000000001\",\n" +
                "\"invoice_id\": \"inv_00000000000001\"\n" +
                "}\n  ]\n}";
        try {

            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(Constants.ADDON_LIST, null);
            mockGetRequest(apiUtils,builder,null,response);

            AddonClient addonClient = new AddonClient("test",apiUtils);

            List<Addon> fetch = addonClient.fetchAll();

            assertNotNull(fetch);
            assertEquals(true,fetch.get(0).has("id"));
            assertEquals(true,fetch.get(0).has("entity"));
            assertEquals(true,fetch.get(0).has("item"));
            assertEquals(true,fetch.get(0).toJson().getJSONObject("item").has("hsn_code"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}