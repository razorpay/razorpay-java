package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class SettlementClientTest extends BaseTest{

    @InjectMocks
    protected SettlementClient settlementClient = new SettlementClient(TEST_SECRET_KEY);

    private static final String SETTLEMENT_ID = "setl_DGlQ1Rj8os78Ec";

    /**
     * Details of all the Settlement can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws RazorpayException{
        String mockedResponseJson = "{\n" +
                "  \"entity\": \"collection\",\n" +
                "  \"count\": 2,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": \"setl_DGlQ1Rj8os78Ec\",\n" +
                "      \"entity\": \"settlement\",\n" +
                "      \"amount\": 9973635,\n" +
                "      \"status\": \"processed\",\n" +
                "      \"fees\": 471699,\n" +
                "      \"tax\": 42070,\n" +
                "      \"utr\": \"1568176960vxp0rj\",\n" +
                "      \"created_at\": 1568176960\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"setl_4xbSwsPABDJ8oK\",\n" +
                "      \"entity\": \"settlement\",\n" +
                "      \"amount\": 50000,\n" +
                "      \"status\": \"processed\",\n" +
                "      \"fees\": 123,\n" +
                "      \"tax\": 12,\n" +
                "      \"utr\": \"RZRP173069230702\",\n" +
                "      \"created_at\": 1509622306\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Settlement> fetch = settlementClient.fetchAll();
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("amount"));
            assertTrue(fetch.get(0).has("id"));
            assertTrue(fetch.get(0).has("fees"));
            assertTrue(fetch.get(0).has("tax"));
            String fetchRequest = getHost(Constants.SETTLEMENTS);
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Retrieve all the Settlement details using settlement id.
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws RazorpayException{
        String mockedResponseJson = "{\n" +
                "    \"id\": "+SETTLEMENT_ID+",\n" +
                "    \"entity\": \"settlement\",\n" +
                "    \"amount\": 9973635,\n" +
                "    \"status\": \"processed\",\n" +
                "    \"fees\": 471699,\n" +
                "    \"tax\": 42070,\n" +
                "    \"utr\": \"1568176960vxp0rj\",\n" +
                "    \"created_at\": 1568176960\n" +
                "}";

        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Settlement fetch = settlementClient.fetch(SETTLEMENT_ID);
            assertNotNull(fetch);
            assertEquals(SETTLEMENT_ID,fetch.get("id"));
            assertEquals("settlement",fetch.get("entity"));
            assertEquals("processed",fetch.get("status"));
            assertEquals("1568176960vxp0rj",fetch.get("utr"));
            String fetchRequest = getHost(String.format(Constants.SETTLEMENT,SETTLEMENT_ID));
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Settlement report for a month can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void reports() throws RazorpayException{
        JSONObject request = new JSONObject("{year:2020,month:9}");

        String mockedResponseJson = "{\n" +
                "  \"entity\": \"collection\",\n" +
                "  \"count\": 4,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"entity_id\": \"pay_DEXrnipqTmWVGE\",\n" +
                "      \"entity\": \"settlement\",\n"+
                "      \"type\": \"payment\",\n" +
                "      \"debit\": 0,\n" +
                "      \"credit\": 97100,\n" +
                "      \"amount\": 100000,\n" +
                "      \"currency\": \"INR\",\n" +
                "      \"fee\": 2900,\n" +
                "      \"tax\": 0,\n" +
                "      \"on_hold\": false,\n" +
                "      \"settled\": true,\n" +
                "      \"created_at\": 1567692556,\n" +
                "      \"settled_at\": 1568176960,\n" +
                "      \"settlement_id\": \"setl_DGlQ1Rj8os78Ec\",\n" +
                "      \"posted_at\": null,\n" +
                "      \"credit_type\": \"default\",\n" +
                "      \"description\": \"Recurring Payment via Subscription\",\n" +
                "      \"notes\": \"{}\",\n" +
                "      \"payment_id\": null,\n" +
                "      \"settlement_utr\": \"1568176960vxp0rj\",\n" +
                "      \"order_id\": \"order_DEXrnRiR3SNDHA\",\n" +
                "      \"order_receipt\": null,\n" +
                "      \"method\": \"card\",\n" +
                "      \"card_network\": \"MasterCard\",\n" +
                "      \"card_issuer\": \"KARB\",\n" +
                "      \"card_type\": \"credit\",\n" +
                "      \"dispute_id\": null\n" +
                "    },\n" +
                "  ]\n" +
                "}";

        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Settlement> fetch = settlementClient.reports(request);
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("card_network"));
            assertTrue(fetch.get(0).has("order_id"));
            assertTrue(fetch.get(0).has("method"));
            assertTrue(fetch.get(0).has("card_type"));
            String reportRequest = getHost(Constants.SETTLEMENTS_REPORTS)+"?month=9&year=2020";
            verifySentRequest(false, null, reportRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Settlement report for a month can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void reportsWithOutQuery() throws RazorpayException{
        JSONObject request = new JSONObject("{year:2020,month:9}");

        String mockedResponseJson = "{\n" +
                "  \"entity\": \"collection\",\n" +
                "  \"count\": 4,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"entity_id\": \"pay_DEXrnipqTmWVGE\",\n" +
                "      \"entity\": \"settlement\",\n"+
                "      \"type\": \"payment\",\n" +
                "      \"debit\": 0,\n" +
                "      \"credit\": 97100,\n" +
                "      \"amount\": 100000,\n" +
                "      \"currency\": \"INR\",\n" +
                "      \"fee\": 2900,\n" +
                "      \"tax\": 0,\n" +
                "      \"on_hold\": false,\n" +
                "      \"settled\": true,\n" +
                "      \"created_at\": 1567692556,\n" +
                "      \"settled_at\": 1568176960,\n" +
                "      \"settlement_id\": \"setl_DGlQ1Rj8os78Ec\",\n" +
                "      \"posted_at\": null,\n" +
                "      \"credit_type\": \"default\",\n" +
                "      \"description\": \"Recurring Payment via Subscription\",\n" +
                "      \"notes\": \"{}\",\n" +
                "      \"payment_id\": null,\n" +
                "      \"settlement_utr\": \"1568176960vxp0rj\",\n" +
                "      \"order_id\": \"order_DEXrnRiR3SNDHA\",\n" +
                "      \"order_receipt\": null,\n" +
                "      \"method\": \"card\",\n" +
                "      \"card_network\": \"MasterCard\",\n" +
                "      \"card_issuer\": \"KARB\",\n" +
                "      \"card_type\": \"credit\",\n" +
                "      \"dispute_id\": null\n" +
                "    },\n" +
                "  ]\n" +
                "}";

        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Settlement> fetch = settlementClient.reports();
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("card_network"));
            assertTrue(fetch.get(0).has("order_id"));
            assertTrue(fetch.get(0).has("method"));
            assertTrue(fetch.get(0).has("card_type"));
            String reportRequest = getHost(Constants.SETTLEMENTS_REPORTS);
            verifySentRequest(false, null, reportRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Settlement is created using the amount details.
     * @throws RazorpayException
     */
    @Test
    public void create() throws RazorpayException{
        JSONObject request = new JSONObject("{\"amount\":200000," +
                "\"settle_full_balance\":false," +
                "\"description\":\"Needthistomakevendorpayments.\"," +
                "\"notes\":{\"notes_key_1\":\"Tea,EarlGrey,Hot\"," +
                "\"notes_key_2\":\"Tea,EarlGrey…decaf.\"}}");

        String mockedResponseJson = "{\n" +
                "  \"id\": "+SETTLEMENT_ID+",\n" +
                "  \"entity\": \"settlement\",\n" +
                "  \"amount_requested\": 200000,\n" +
                "  \"amount_settled\": 0,\n" +
                "  \"amount_pending\": 199410,\n" +
                "  \"amount_reversed\": 0,\n" +
                "  \"fees\": 590,\n" +
                "  \"tax\": 90,\n" +
                "  \"currency\": \"INR\",\n" +
                "  \"settle_full_balance\": false,\n" +
                "  \"status\": \"initiated\",\n" +
                "  \"description\": \"Need this to make vendor payments.\",\n" +
                "  \"notes\": {\n" +
                "    \"notes_key_1\": \"Tea, Earl Grey, Hot\",\n" +
                "    \"notes_key_2\": \"Tea, Earl Grey… decaf.\"\n" +
                "  },\n" +
                "  \"created_at\": 1596771429,\n" +
                "  \"ondemand_payouts\": {\n" +
                "    \"entity\": \"collection\",\n" +
                "    \"count\": 1,\n" +
                "    \"items\": [\n" +
                "      {\n" +
                "        \"id\": \"setlodp_FNj7g2cbvw8ueO\",\n" +
                "        \"entity\": \"settlement.ondemand_payout\",\n" +
                "        \"initiated_at\": null,\n" +
                "        \"processed_at\": null,\n" +
                "        \"reversed_at\": null,\n" +
                "        \"amount\": 200000,\n" +
                "        \"amount_settled\": null,\n" +
                "        \"fees\": 590,\n" +
                "        \"tax\": 90,\n" +
                "        \"utr\": null,\n" +
                "        \"status\": \"created\",\n" +
                "        \"created_at\": 1596771429\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Settlement fetch = settlementClient.create(request);
            assertNotNull(fetch);
            assertEquals(SETTLEMENT_ID,fetch.get("id"));
            assertEquals("initiated",fetch.get("status"));
            assertTrue(fetch.has("notes"));
            String createRequest = getHost(Constants.SETTLEMENTS_INSTANT);
            verifySentRequest(true, request.toString(), createRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Details of all on-demand Settlement can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAllDemand() throws RazorpayException{
        String mockedResponseJson = "{\n" +
                "  \"entity\": \"collection\",\n" +
                "  \"count\": 1,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": \"setlod_FNj7g2YS5J67Rz\",\n" +
                "      \"entity\": \"settlement\",\n" +
                "      \"amount_requested\": 200000,\n" +
                "      \"amount_settled\": 199410,\n" +
                "      \"amount_pending\": 0,\n" +
                "      \"amount_reversed\": 0,\n" +
                "      \"fees\": 590,\n" +
                "      \"tax\": 90,\n" +
                "      \"currency\": \"INR\",\n" +
                "      \"settle_full_balance\": false,\n" +
                "      \"status\": \"processed\",\n" +
                "      \"description\": \"Need this to make vendor payments.\",\n" +
                "      \"notes\": {\n" +
                "        \"notes_key_1\": \"Tea, Earl Grey, Hot\",\n" +
                "        \"notes_key_2\": \"Tea, Earl Grey… decaf.\"\n" +
                "      },\n" +
                "      \"created_at\": 1596771429\n" +
                "    },\n" +
                "  ]\n" +
                "}";

        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Settlement> fetch = settlementClient.fetchAllDemand();
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("id"));
            assertTrue(fetch.get(0).has("entity"));
            assertTrue(fetch.get(0).has("amount_requested"));
            assertTrue(fetch.get(0).has("amount_pending"));
            String fetchRequest = getHost(Constants.SETTLEMENTS_INSTANT);
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Retrieve all on-demand Settlement details using settlement id.
     * @throws RazorpayException
     */
    @Test
    public void fetchDemandSettlement() throws RazorpayException{
        String mockedResponseJson = "{\n" +
                "  \"id\": "+SETTLEMENT_ID+",\n" +
                "  \"entity\": \"settlement\",\n" +
                "  \"amount_requested\": 200000,\n" +
                "  \"amount_settled\": 199410,\n" +
                "  \"amount_pending\": 0,\n" +
                "  \"amount_reversed\": 0,\n" +
                "  \"fees\": 590,\n" +
                "  \"tax\": 90,\n" +
                "  \"currency\": \"INR\",\n" +
                "  \"settle_full_balance\": false,\n" +
                "  \"status\": \"processed\",\n" +
                "  \"description\": \"Need this to make vendor payments.\",\n" +
                "  \"notes\": {\n" +
                "    \"notes_key_1\": \"Tea, Earl Grey, Hot\",\n" +
                "    \"notes_key_2\": \"Tea, Earl Grey… decaf.\"\n" +
                "  },\n" +
                "  \"created_at\": 1596771429\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Settlement fetch = settlementClient.fetchDemandSettlement(SETTLEMENT_ID);
            assertNotNull(fetch);
            assertEquals(SETTLEMENT_ID,fetch.get("id"));
            assertEquals("processed",fetch.get("status"));
            assertEquals(200000,(int)fetch.get("amount_requested"));
            String fetchRequest = getHost(String.format(Constants.SETTLEMENT_INSTANT,SETTLEMENT_ID));
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}