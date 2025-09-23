package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class AddonClientTest  extends BaseTest{

    @InjectMocks
    protected AddonClient client = new AddonClient(TEST_SECRET_KEY);

    private static final String ADDON_ID = "ao_00000000000001";

    /**
     * Retrieve all the addon details using addon id.
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws RazorpayException {

        String json = "{\n  \"id\":"+ADDON_ID+",\n" +
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
        try {
            mockResponseFromExternalClient(json);
            mockResponseHTTPCodeFromExternalClient(200);
            Addon fetch = client.fetch(ADDON_ID);
            assertNotNull(fetch);
            JSONObject item = fetch.toJson().getJSONObject("item");
            assertEquals(ADDON_ID,fetch.get("id"));
            assertEquals("INR",item.get("currency"));
            assertEquals(30000,item.get("amount"));
            assertTrue(item.getBoolean("active"));
            assertTrue(item.has("unit_amount"));
            String addonCreate = getHost(String.format(Constants.ADDON_GET, ADDON_ID));
            verifySentRequest(false, null, addonCreate);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Details of all the addon can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws RazorpayException {

        String json = "{\n  " +
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
            mockResponseFromExternalClient(json);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Addon> fetch = client.fetchAll();
            assertNotNull(fetch);
            assertEquals(true,fetch.get(0).has("id"));
            assertEquals(true,fetch.get(0).has("entity"));
            assertEquals(true,fetch.get(0).has("item"));
            assertEquals(true,fetch.get(0).toJson().getJSONObject("item").has("hsn_code"));
            String addonList = getHost(Constants.ADDON_LIST);
            verifySentRequest(false, null, addonList);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Delete an Add-on.
     * @throws RazorpayException
     */
    @Test
    public void delete() throws RazorpayException {

        String mockedResponseJson = "[]";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Addon> fetch = client.delete(ADDON_ID);
            assertNotNull(fetch);
            String addonList = getHost(String.format(Constants.ADDON_DELETE, ADDON_ID));
            verifySentRequest(false, null, addonList);
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}
