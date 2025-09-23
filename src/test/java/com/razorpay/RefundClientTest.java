package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class RefundClientTest extends BaseTest{

    @InjectMocks
    protected RefundClient refundClient = new RefundClient(TEST_SECRET_KEY);

    private static final String REFUND_ID = "rfnd_FP8QHiV938haTz";

    /**
     * Refund is created using the payment id and amount details.
     * @throws RazorpayException
     */
    @Test
    public void create() throws RazorpayException{
        JSONObject request = new JSONObject("{" +
                "\"amount\":\"100\"," +
                "\"speed\":\"normal\"," +
                "\"notes\":{\"notes_key_1\":\"BeammeupScotty.\"," +
                "\"notes_key_2\":\"Engage\"}," +
                "\"receipt\":\"ReceiptNo.31\"}");

        String mockedResponseJson = "{\n" +
                "  \"id\": "+REFUND_ID+",\n" +
                "  \"entity\": \"refund\",\n" +
                "  \"amount\": 500100,\n" +
                "  \"receipt\": \"Receipt No. 31\",\n" +
                "  \"currency\": \"INR\",\n" +
                "  \"payment_id\": \"pay_FCXKPFtYfPXJPy\",\n" +
                "  \"notes\": [],\n" +
                "  \"acquirer_data\": {\n" +
                "    \"arn\": null\n" +
                "  },\n" +
                "  \"created_at\": 1597078866,\n" +
                "  \"batch_id\": null,\n" +
                "  \"status\": \"processed\",\n" +
                "  \"speed_processed\": \"normal\"\n" +
                "}";

        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Refund fetch = refundClient.create(request);
            assertNotNull(fetch);
            assertEquals(REFUND_ID,fetch.get("id"));
            assertEquals("refund",fetch.get("entity"));
            assertEquals(500100,(int)fetch.get("amount"));
            assertEquals("INR",fetch.get("currency"));
            String createRequest = getHost(Constants.REFUNDS);
            verifySentRequest(true, request.toString(), createRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Retrieve all refund details using refund id.
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws RazorpayException{
        String mockedResponseJson = "{\n" +
                "  \"id\": "+REFUND_ID+",\n" +
                "  \"entity\": \"refund\",\n" +
                "  \"amount\": 300100,\n" +
                "  \"currency\": \"INR\",\n" +
                "  \"payment_id\": \"pay_FIKOnlyii5QGNx\",\n" +
                "  \"notes\": {\n" +
                "    \"comment\": \"Comment for refund\"\n" +
                "  },\n" +
                "  \"receipt\": null,\n" +
                "  \"acquirer_data\": {\n" +
                "    \"arn\": \"10000000000000\"\n" +
                "  },\n" +
                "  \"created_at\": 1597078124,\n" +
                "  \"batch_id\": null,\n" +
                "  \"status\": \"processed\",\n" +
                "  \"speed_processed\": \"normal\",\n" +
                "  \"speed_requested\": \"optimum\"\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Refund fetch = refundClient.fetch(REFUND_ID);
            assertNotNull(fetch);
            assertEquals(REFUND_ID,fetch.get("id"));
            String fetchRequest = getHost(String.format(Constants.REFUND,REFUND_ID));
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Details of all the refund can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws RazorpayException{
        String mockedResponseJson = "{\n" +
                "  \"entity\": \"collection\",\n" +
                "  \"count\": 1,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": \"rfnd_FFX6AnnIN3puqW\",\n" +
                "      \"entity\": \"refund\",\n" +
                "      \"amount\": 88800,\n" +
                "      \"currency\": \"INR\",\n" +
                "      \"payment_id\": \"pay_FFX5FdEYx8jPwA\",\n" +
                "      \"notes\": {\n" +
                "        \"comment\": \"Issuing an instant refund\"\n" +
                "      },\n" +
                "      \"receipt\": null,\n" +
                "      \"acquirer_data\": {},\n" +
                "      \"created_at\": 1594982363,\n" +
                "      \"batch_id\": null,\n" +
                "      \"status\": \"processed\",\n" +
                "      \"speed_processed\": \"optimum\",\n" +
                "      \"speed_requested\": \"optimum\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Refund> fetch = refundClient.fetchAll();
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("id"));
            assertTrue(fetch.get(0).has("entity"));
            assertTrue(fetch.get(0).has("amount"));
            assertTrue(fetch.get(0).has("currency"));
            assertTrue(fetch.get(0).has("payment_id"));
            String fetchRequest = getHost(Constants.REFUNDS);
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Details of mutiple refunds for a payment can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchMultipleRefund() throws RazorpayException{
        String mockedResponseJson = "{\n" +
                "  \"entity\": \"collection\",\n" +
                "  \"count\": 1,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": \"rfnd_FP8DDKxqJif6ca\",\n" +
                "      \"entity\": \"refund\",\n" +
                "      \"amount\": 300100,\n" +
                "      \"currency\": \"INR\",\n" +
                "      \"payment_id\": \"pay_FIKOnlyii5QGNx\",\n" +
                "      \"notes\": {\n" +
                "        \"comment\": \"Comment for refund\"\n" +
                "      },\n" +
                "      \"receipt\": null,\n" +
                "      \"acquirer_data\": {\n" +
                "        \"arn\": \"10000000000000\"\n" +
                "      },\n" +
                "      \"created_at\": 1597078124,\n" +
                "      \"batch_id\": null,\n" +
                "      \"status\": \"processed\",\n" +
                "      \"speed_processed\": \"normal\",\n" +
                "      \"speed_requested\": \"optimum\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Refund> fetch = refundClient.fetchMultipleRefund(REFUND_ID);
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("id"));
            assertTrue(fetch.get(0).has("entity"));
            assertTrue(fetch.get(0).has("amount"));
            assertTrue(fetch.get(0).has("currency"));
            assertTrue(fetch.get(0).has("payment_id"));
            String fetchRequest = getHost(String.format(Constants.REFUND_MULTIPLE,REFUND_ID));
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Update an refund details using refund id with object of that properties
     * @throws RazorpayException
     */
    @Test
    public void edit() throws RazorpayException{
        JSONObject request = new JSONObject("" +
                "{\"notes\":" +
                "{\"notes_key_1\":\"BeammeupScotty.\"," +
                "\"notes_key_2\":\"Engage\"}}");

        String mockedResponseJson = "{\n" +
                "  \"id\": "+REFUND_ID+",\n" +
                "  \"entity\": \"refund\",\n" +
                "  \"amount\": 300100,\n" +
                "  \"currency\": \"INR\",\n" +
                "  \"payment_id\": \"pay_FIKOnlyii5QGNx\",\n" +
                "  \"notes\": {\n" +
                "    \"notes_key_1\": \"Beam me up Scotty.\",\n" +
                "    \"notes_key_2\": \"Engage\"\n" +
                "  },\n" +
                "  \"receipt\": null,\n" +
                "  \"acquirer_data\": {\n" +
                "    \"arn\": \"10000000000000\"\n" +
                "  },\n" +
                "  \"created_at\": 1597078124,\n" +
                "  \"batch_id\": null,\n" +
                "  \"status\": \"processed\",\n" +
                "  \"speed_processed\": \"normal\",\n" +
                "  \"speed_requested\": \"optimum\"\n" +
                "}";

        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Refund fetch = refundClient.edit(REFUND_ID, request);
            assertNotNull(fetch);
            assertEquals(REFUND_ID,fetch.get("id"));
            assertEquals("refund",fetch.get("entity"));
            assertEquals(300100,(int)fetch.get("amount"));
            assertEquals("INR",fetch.get("currency"));
            String editRequest = getHost(String.format(Constants.REFUND,REFUND_ID));
            verifySentRequest(true, request.toString(), editRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}