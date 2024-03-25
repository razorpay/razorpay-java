package com.razorpay;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class DisputeClientTest extends BaseTest{

    @InjectMocks
    protected DisputeClient disputeClient = new DisputeClient(TEST_SECRET_KEY);

    private static final String DISPUTE_ID = "disp_AHfqOvkldwsbqt";

    /**
     * Contest a Dispute using dispute id.
     * @throws RazorpayException
     */
    @Test
    public void TestContest() throws RazorpayException{

        JSONObject request = new JSONObject();
        request.put("amount",5000);
        request.put("summary","goods delivered");
        List<Object> shipping_proof = new ArrayList<>();
        shipping_proof.add("doc_EFtmUsbwpXwBH9");
        shipping_proof.add("doc_EFtmUsbwpXwBH8");
        request.put("shipping_proof", shipping_proof);
        List<Object> others = new ArrayList<>();
        JSONObject otherParam = new JSONObject();
        otherParam.put("type","receipt_signed_by_customer");
        List<Object> doc = new ArrayList<>();
        doc.add("doc_EFtmUsbwpXwBH1");
        doc.add("doc_EFtmUsbwpXwBH7");
        otherParam.put("document_ids",doc);
        others.add(otherParam);
        request.put("others", others);
        request.put("action", "submit");

        JSONObject mockedResponseJson = new JSONObject();

        mockedResponseJson.put("id", "disp_AHfqOvkldwsbqt");
        mockedResponseJson.put("amount", 5000);
        mockedResponseJson.put("entity", "dispute");
        mockedResponseJson.put("summary", "goods delivered");

        JSONArray shippingProofArray = new JSONArray();
        shippingProofArray.put("doc_EFtmUsbwpXwBH9");
        shippingProofArray.put("doc_EFtmUsbwpXwBH8");
        mockedResponseJson.put("shipping_proof", shippingProofArray);

        mockedResponseJson.put("billing_proof", JSONObject.NULL);
        mockedResponseJson.put("cancellation_proof", JSONObject.NULL);
        mockedResponseJson.put("customer_communication", JSONObject.NULL);
        mockedResponseJson.put("proof_of_service", JSONObject.NULL);
        mockedResponseJson.put("explanation_letter", JSONObject.NULL);
        mockedResponseJson.put("refund_confirmation", JSONObject.NULL);
        mockedResponseJson.put("access_activity_log", JSONObject.NULL);
        mockedResponseJson.put("refund_cancellation_policy", JSONObject.NULL);
        mockedResponseJson.put("term_and_conditions", JSONObject.NULL);

        JSONArray othersArray = new JSONArray();
        JSONObject othersObject = new JSONObject();
        othersObject.put("type", "receipt_signed_by_customer");
        JSONArray documentIdsArray = new JSONArray();
        documentIdsArray.put("doc_EFtmUsbwpXwBH1");
        documentIdsArray.put("doc_EFtmUsbwpXwBH7");
        othersObject.put("document_ids", documentIdsArray);
        othersArray.put(othersObject);
        mockedResponseJson.put("others", othersArray);

        mockedResponseJson.put("submitted_at", JSONObject.NULL);

        JSONObject evidenceObject = new JSONObject();
        evidenceObject.put("amount", 5000);
        evidenceObject.put("summary", "goods delivered");
        evidenceObject.put("shipping_proof", shippingProofArray);
        evidenceObject.put("billing_proof", JSONObject.NULL);
        evidenceObject.put("cancellation_proof", JSONObject.NULL);
        evidenceObject.put("customer_communication", JSONObject.NULL);
        evidenceObject.put("proof_of_service", JSONObject.NULL);
        evidenceObject.put("explanation_letter", JSONObject.NULL);
        evidenceObject.put("refund_confirmation", JSONObject.NULL);
        evidenceObject.put("access_activity_log", JSONObject.NULL);
        evidenceObject.put("refund_cancellation_policy", JSONObject.NULL);
        evidenceObject.put("term_and_conditions", JSONObject.NULL);
        evidenceObject.put("others", othersArray);
        evidenceObject.put("submitted_at", JSONObject.NULL);
        mockedResponseJson.put("evidence", evidenceObject);

        try {
            mockResponseFromExternalClient(mockedResponseJson.toString());
            mockResponseHTTPCodeFromExternalClient(200);
            Dispute dispute = disputeClient.contest(DISPUTE_ID,request);
            assertNotNull(dispute);
            assertEquals(DISPUTE_ID,dispute.get("id"));
            String createRequest = getHost(String.format(Constants.DISPUTE_CONTEST, DISPUTE_ID));
            verifySentRequest(true, request.toString(), createRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Accept a dispute using dispute id.
     * @throws RazorpayException
     */
    @Test
    public void TestAccept() throws RazorpayException {

        String mockedResponseJson = "{\n" +
                "  \"id\": \"disp_AHfqOvkldwsbqt\",\n" +
                "  \"entity\": \"dispute\",\n" +
                "  \"payment_id\": \"pay_EsyWjHrfzb59eR\",\n" +
                "  \"amount\": 10000,\n" +
                "  \"currency\": \"INR\",\n" +
                "  \"amount_deducted\": 10000,\n" +
                "  \"reason_code\": \"pre_arbitration\",\n" +
                "  \"respond_by\": 1590604200,\n" +
                "  \"status\": \"lost\",\n" +
                "  \"phase\": \"pre_arbitration\",\n" +
                "  \"created_at\": 1590059211,\n" +
                "  \"evidence\": {\n" +
                "    \"amount\": 10000,\n" +
                "    \"summary\": null,\n" +
                "    \"shipping_proof\": null,\n" +
                "    \"billing_proof\": null,\n" +
                "    \"cancellation_proof\": null,\n" +
                "    \"customer_communication\": null,\n" +
                "    \"proof_of_service\": null,\n" +
                "    \"explanation_letter\": null,\n" +
                "    \"refund_confirmation\": null,\n" +
                "    \"access_activity_log\": null,\n" +
                "    \"refund_cancellation_policy\": null,\n" +
                "    \"term_and_conditions\": null,\n" +
                "    \"others\": null,\n" +
                "    \"submitted_at\": null\n" +
                "  }\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Dispute fetch = disputeClient.accept(DISPUTE_ID);
            assertNotNull(fetch);
            assertEquals(DISPUTE_ID,fetch.get("id"));
            String fetchRequest = getHost(String.format(Constants.DISPUTE_ACCEPT, DISPUTE_ID));
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Fetch all disputes
     * @throws RazorpayException
     */
    @Test
    public void TestfetchAll() throws RazorpayException {
        String mockedResponseJson = "{\n" +
                "  \"entity\": \"collection\",\n" +
                "  \"count\": 1,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": \"disp_Esz7KAitoYM7PJ\",\n" +
                "      \"entity\": \"dispute\",\n" +
                "      \"payment_id\": \"pay_EsyWjHrfzb59eR\",\n" +
                "      \"amount\": 10000,\n" +
                "      \"currency\": \"INR\",\n" +
                "      \"amount_deducted\": 0,\n" +
                "      \"reason_code\": \"pre_arbitration\",\n" +
                "      \"respond_by\": 1590604200,\n" +
                "      \"status\": \"open\",\n" +
                "      \"phase\": \"pre_arbitration\",\n" +
                "      \"created_at\": 1590059211,\n" +
                "      \"evidence\": {\n" +
                "        \"amount\": 10000,\n" +
                "        \"summary\": null,\n" +
                "        \"shipping_proof\": null,\n" +
                "        \"billing_proof\": null,\n" +
                "        \"cancellation_proof\": null,\n" +
                "        \"customer_communication\": null,\n" +
                "        \"proof_of_service\": null,\n" +
                "        \"explanation_letter\": null,\n" +
                "        \"refund_confirmation\": null,\n" +
                "        \"access_activity_log\": null,\n" +
                "        \"refund_cancellation_policy\": null,\n" +
                "        \"term_and_conditions\": null,\n" +
                "        \"others\": null,\n" +
                "        \"submitted_at\": null\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List <Dispute> fetch = disputeClient.fetchAll();
            assertNotNull(fetch);
            assertEquals(true,fetch.get(0).has("id"));
            String fetchRequest = getHost(Constants.DISPUTE);
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Fetch Disputes
     * @throws RazorpayException
     */
    @Test
    public void testFetch() throws IOException, RazorpayException {
        String mockedResponseJson = "{\n" +
                "  \"id\": \"disp_AHfqOvkldwsbqt\",\n" +
                "  \"entity\": \"dispute\",\n" +
                "  \"payment_id\": \"pay_EsyWjHrfzb59eR\",\n" +
                "  \"amount\": 10000,\n" +
                "  \"currency\": \"INR\",\n" +
                "  \"amount_deducted\": 0,\n" +
                "  \"reason_code\": \"pre_arbitration\",\n" +
                "  \"respond_by\": 1590604200,\n" +
                "  \"status\": \"open\",\n" +
                "  \"phase\": \"pre_arbitration\",\n" +
                "  \"created_at\": 1590059211,\n" +
                "  \"evidence\": {\n" +
                "    \"amount\": 10000,\n" +
                "    \"summary\": \"goods delivered\",\n" +
                "    \"shipping_proof\": null,\n" +
                "    \"billing_proof\": null,\n" +
                "    \"cancellation_proof\": null,\n" +
                "    \"customer_communication\": null,\n" +
                "    \"proof_of_service\": null,\n" +
                "    \"explanation_letter\": null,\n" +
                "    \"refund_confirmation\": null,\n" +
                "    \"access_activity_log\": null,\n" +
                "    \"refund_cancellation_policy\": null,\n" +
                "    \"term_and_conditions\": null,\n" +
                "    \"others\": null,\n" +
                "    \"submitted_at\": null\n" +
                "  }\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Dispute dispute = disputeClient.fetch(DISPUTE_ID);
            assertNotNull(dispute);
            assertEquals(DISPUTE_ID,dispute.get("id"));
            verifySentRequest(false, null, getHost(String.format(Constants.DISPUTE_FETCH,DISPUTE_ID)));
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}