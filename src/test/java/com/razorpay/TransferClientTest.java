package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class TransferClientTest extends BaseTest{

    @InjectMocks
    protected TransferClient transferClient = new TransferClient(TEST_SECRET_KEY);

    private static final String TRANSFER_ID = "trf_E9uhYLFLLZ2pks";

    private static final String PAYMENT_ID = "pay_IDRP0tbirMSsbn";

    /**
     * Create transfer with currency and amount details
     * @throws RazorpayException
     */
    @Test
    public void create() throws RazorpayException{

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
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Transfer fetch = transferClient.create(request);
            assertNotNull(fetch);
            assertEquals(TRANSFER_ID,fetch.get("id"));
            assertTrue(fetch.has("amount"));
            assertTrue(fetch.has("currency"));
            String createRequest = getHost(Constants.TRANSFER_CREATE);
            verifySentRequest(true, request.toString(), createRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Details of all the transfers can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws RazorpayException{

        String json = "{\n" +
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
            mockResponseFromExternalClient(json);
            mockResponseHTTPCodeFromExternalClient(200);
            Transfer fetch = transferClient.fetch(TRANSFER_ID);
            assertNotNull(fetch);
            assertEquals(TRANSFER_ID,fetch.get("id"));
            assertEquals("INR",fetch.get("currency"));
            assertTrue(fetch.has("amount_reversed"));
            String addonCreate = getHost(String.format(Constants.TRANSFER_GET, TRANSFER_ID));
            verifySentRequest(false, null, addonCreate);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Details of all the transfers can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws RazorpayException{

        String json = "{\n" +
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
            mockResponseFromExternalClient(json);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Transfer> fetch = transferClient.fetchAll();
            assertNotNull(fetch);
            assertEquals(true,fetch.get(0).has("id"));
            assertEquals(true,fetch.get(0).has("entity"));
            assertEquals(true,fetch.get(0).has("amount_reversed"));
            assertEquals(true,fetch.get(0).toJson().getJSONObject("recipient_settlement").has("amount"));
            String transferList = getHost(Constants.TRANSFER_LIST);
            verifySentRequest(false, null, transferList);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Update an transfer details using transfer id with object of that properties
     * @throws RazorpayException
     */
    @Test
    public void edit() throws RazorpayException{

        JSONObject request = new JSONObject("{\n" +
                "  \"on_hold\": \"1\",\n" +
                "  \"on_hold_until\": \"1679691505\"\n" +
                "}");

        String json = "{\n" +
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
            mockResponseFromExternalClient(json);
            mockResponseHTTPCodeFromExternalClient(200);
            Transfer fetch = transferClient.edit(TRANSFER_ID,request);
            assertNotNull(fetch);
            assertEquals(TRANSFER_ID,fetch.get("id"));
            assertEquals("INR",fetch.get("currency"));
            assertTrue(fetch.has("amount_reversed"));
            String editRequest = getHost(String.format(Constants.TRANSFER_EDIT, TRANSFER_ID));
            verifySentRequest(true, request.toString(), editRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Reverse transfers from all linked accounts
     * @throws RazorpayException
     */
    @Test
    public void reversal() throws RazorpayException{

        JSONObject request = new JSONObject("{\n" +
                "  \"amount\": \"100\",\n" +
                "}");

        String json = "{\n" +
                "  \"id\": \"rvrsl_EB0BWgGDAu7tOz\",\n" +
                "  \"entity\": \"reversal\",\n" +
                "  \"transfer_id\": "+TRANSFER_ID+",\n" +
                "  \"amount\": 100,\n" +
                "  \"fee\": 0,\n" +
                "  \"tax\": 0,\n" +
                "  \"currency\": \"INR\",\n" +
                "  \"notes\": [],\n" +
                "  \"initiator_id\": \"CJoeHMNpi0nC7k\",\n" +
                "  \"customer_refund_id\": null,\n" +
                "  \"created_at\": 1580456007\n" +
                "}";
        try {
            mockResponseFromExternalClient(json);
            mockResponseHTTPCodeFromExternalClient(200);
            Reversal fetch = transferClient.reversal(TRANSFER_ID,request);
            assertNotNull(fetch);
            assertEquals(TRANSFER_ID,fetch.get("transfer_id"));
            assertEquals("INR",fetch.get("currency"));
            assertTrue(fetch.has("customer_refund_id"));
            String editRequest = getHost(String.format(Constants.TRANSFER_REVERSAL_CREATE, TRANSFER_ID));
            verifySentRequest(true, request.toString(), editRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}