package com.razorpay;

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
            assertEquals(ADDON_ID,fetch.get("id"));
            assertEquals("INR",fetch.get("currency"));
            assertEquals("30000",fetch.get("amount"));
            assertTrue(fetch.get("active"));
            assertTrue(fetch.get("unit_amount"));
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
                "\"hsn_code\": null,\n " +
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
            assertEquals(true,fetch.get(0).has("items"));
            assertEquals(true,fetch.get(0).has("hsn_code"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}
