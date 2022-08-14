package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class TransferClientTest extends BaseTest{

    @Mock
    ApiUtils apiUtils;

    private static final String TRANSFER_ID = "trf_E9uhYLFLLZ2pks";

    private static final String PAYMENT_ID = "pay_IDRP0tbirMSsbn";

    /**
     * Create transfer with currency and amount details
     * @throws RazorpayException
     */
    @Test
    public void create() throws Exception{

        JSONObject request = new JSONObject("{\n" +
                "  \"amount\": 500,\n" +
                "  \"currency\": \"INR\"\n" +
                "}");

        String mockedResponseJson = "{\n" +
                "  \"id\": "+TRANSFER_ID+",\n" +
                "  \"entity\": \"transfer\",\n" +
                "  \"source\": \"acc_CJoeHMNpi0nC7k\",\n" +
                "  \"recipient\": \"acc_CPRsN1LkFccllA\",\n" +
                "  \"amount\": 100,\n" +
                "  \"currency\": \"INR\",\n" +
                "  \"amount_reversed\": 0,\n" +
                "  \"notes\": [],\n" +
                "  \"fees\": 1,\n" +
                "  \"tax\": 0,\n" +
                "  \"on_hold\": false,\n" +
                "  \"on_hold_until\": null,\n" +
                "  \"recipient_settlement_id\": null,\n" +
                "  \"created_at\": 1580219046,\n" +
                "  \"linked_account_notes\": [],\n" +
                "  \"processed_at\": 1580219046\n" +
                "}";
        try {
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(Constants.TRANSFER_CREATE, null);
            mockPostRequest(apiUtils,builder,request.toString(), mockedResponseJson);

            TransferClient transferClient = new TransferClient("test",apiUtils);
            Transfer fetch = transferClient.create(request);
            assertNotNull(fetch);
            assertEquals(TRANSFER_ID,fetch.get("id"));
            assertTrue(fetch.has("amount"));
            assertTrue(fetch.has("currency"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Details of all the transfers can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws Exception{

        String mockedResponseJson = "{\n" +
                "  \"id\": "+TRANSFER_ID+",\n" +
                "  \"entity\": \"transfer\",\n" +
                "  \"source\": \"pay_E6j30Iu1R7XbIG\",\n" +
                "  \"recipient\": \"acc_CMaomTz4o0FOFz\",\n" +
                "  \"amount\": 100,\n" +
                "  \"currency\": \"INR\",\n" +
                "  \"amount_reversed\": 0,\n" +
                "  \"notes\": [],\n" +
                "  \"fees\": 1,\n" +
                "  \"tax\": 0,\n" +
                "  \"on_hold\": false,\n" +
                "  \"on_hold_until\": null,\n" +
                "  \"recipient_settlement_id\": null,\n" +
                "  \"created_at\": 1579691505,\n" +
                "  \"linked_account_notes\": [],\n" +
                "  \"processed_at\": 1579691505\n" +
                "}";
        try {
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(String.format(Constants.TRANSFER_GET, TRANSFER_ID), null);
            mockGetRequest(apiUtils,builder,null, mockedResponseJson);

            TransferClient transferClient = new TransferClient("test",apiUtils);
            Transfer fetch = transferClient.fetch(TRANSFER_ID);
            assertNotNull(fetch);
            assertEquals(TRANSFER_ID,fetch.get("id"));
            assertEquals("INR",fetch.get("currency"));
            assertTrue(fetch.has("amount_reversed"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Details of all the transfers can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws Exception{

        String mockedResponseJson = "{\n" +
                "  \"entity\": \"collection\",\n" +
                "  \"count\": 1,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": \"trf_DGSTeXzBkEVh48\",\n" +
                "      \"entity\": \"transfer\",\n" +
                "      \"source\": \"pay_DGSRhvMbOqeCe7\",\n" +
                "      \"recipient\": \"acc_CMaomTz4o0FOFz\",\n" +
                "      \"amount\": 500,\n" +
                "      \"currency\": \"INR\",\n" +
                "      \"amount_reversed\": 0,\n" +
                "      \"notes\": [],\n" +
                "      \"fees\": 2,\n" +
                "      \"tax\": 0,\n" +
                "      \"on_hold\": false,\n" +
                "      \"on_hold_until\": null,\n" +
                "      \"recipient_settlement_id\": \"setl_DHYJ3dRPqQkAgV\",\n" +
                "      \"recipient_settlement\": {\n" +
                "        \"id\": \"setl_DHYJ3dRPqQkAgV\",\n" +
                "        \"entity\": \"settlement\",\n" +
                "        \"amount\": 500,\n" +
                "        \"status\": \"failed\",\n" +
                "        \"fees\": 0,\n" +
                "        \"tax\": 0,\n" +
                "        \"utr\": \"CN0038699836\",\n" +
                "        \"created_at\": 1568349124\n" +
                "      },\n" +
                "      \"created_at\": 1568110256,\n" +
                "      \"linked_account_notes\": [],\n" +
                "      \"processed_at\": null\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(Constants.TRANSFER_LIST, null);
            mockGetRequest(apiUtils,builder,null, mockedResponseJson);
            TransferClient transferClient = new TransferClient("test",apiUtils);
            List<Transfer> fetch = transferClient.fetchAll();
            assertNotNull(fetch);
            assertEquals(true,fetch.get(0).has("id"));
            assertEquals(true,fetch.get(0).has("entity"));
            assertEquals(true,fetch.get(0).has("amount_reversed"));
            assertEquals(true,fetch.get(0).toJson().getJSONObject("recipient_settlement").has("amount"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Update an transfer details using transfer id with object of that properties
     * @throws RazorpayException
     */
    @Test
    public void edit() throws Exception{

        JSONObject request = new JSONObject("{\n" +
                "  \"on_hold\": \"1\",\n" +
                "  \"on_hold_until\": \"1679691505\"\n" +
                "}");

        String mockedResponseJson = "{\n" +
                "  \"id\": "+TRANSFER_ID+",\n" +
                "  \"entity\": \"transfer\",\n" +
                "  \"source\": \"pay_EAeSM2Xul8xYRo\",\n" +
                "  \"recipient\": \"acc_CMaomTz4o0FOFz\",\n" +
                "  \"amount\": 100,\n" +
                "  \"currency\": \"INR\",\n" +
                "  \"amount_reversed\": 0,\n" +
                "  \"notes\": [],\n" +
                "  \"fees\": 1,\n" +
                "  \"tax\": 0,\n" +
                "  \"on_hold\": true,\n" +
                "  \"on_hold_until\": 1679691505,\n" +
                "  \"recipient_settlement_id\": null,\n" +
                "  \"created_at\": 1580459321,\n" +
                "  \"linked_account_notes\": [],\n" +
                "  \"processed_at\": 1580459321\n" +
                "}\n";
        try {
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(String.format(Constants.TRANSFER_EDIT, TRANSFER_ID), null);
            mockPatchRequest(apiUtils,builder,request.toString(), mockedResponseJson);
            TransferClient transferClient = new TransferClient("test",apiUtils);

            Transfer fetch = transferClient.edit(TRANSFER_ID,request);
            assertNotNull(fetch);
            assertEquals(TRANSFER_ID,fetch.get("id"));
            assertEquals("INR",fetch.get("currency"));
            assertTrue(fetch.has("amount_reversed"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}