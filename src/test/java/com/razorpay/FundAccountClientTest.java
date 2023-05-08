package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class FundAccountClientTest extends BaseTest{

    @InjectMocks
    protected FundAccountClient fundAccountClient = new FundAccountClient(TEST_SECRET_KEY);

    private static final String FUNDACCOUNT_ID = "fa_Aa00000000001";
    
    /**
     * Fund account is created using the customer and item details.
     * @throws RazorpayException
     */
    @Test
    public void create() throws RazorpayException {
        JSONObject request = new JSONObject("{\n  " +
                "\"customer_id\":\"cust_Aa000000000001\",\n" +
                "\"account_type\":\"bank_account\",\n" +
                "\"bank_account\":{\n" +
                "\"name\":\"Gaurav Kumar\",\n" +
                "\"account_number\":\"11214311215411\",\n" +
                "\"ifsc\":\"HDFC0000053\"\n}\n}");

        String mockedResponseJson = "{\n  " +
                "\"id\":\"fa_Aa00000000001\",\n" +
                "\"entity\":\"fund_account\",\n" +
                "\"customer_id\":\"cust_Aa000000000001\",\n" +
                "\"account_type\":\"bank_account\",\n" +
                "\"bank_account\":{\n" +
                "\"name\":\"Gaurav Kumar\",\n" +
                "\"account_number\":\"11214311215411\",\n" +
                "\"ifsc\":\"HDFC0000053\",\n" +
                "\"bank_name\":\"HDFC Bank\"\n" +
                "},\n" +
                "\"active\":true,\n" +
                "\"created_at\":1543650891\n}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            FundAccount fundaccount = fundAccountClient.create(request);
            assertNotNull(fundaccount);
            assertEquals(FUNDACCOUNT_ID,fundaccount.get("id"));
            String createRequest = getHost(Constants.FUND_ACCOUNT_CREATE);
            verifySentRequest(true, request.toString(), createRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }
    
    /**
     * Retrieve all fund account of respective customer using customer id.
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws RazorpayException {

        String mockedResponseJson = "{\n" +
                "\"id\":\"fa_Aa00000000001\",\n" +
                "\"entity\":\"fund_account\",\n" +
                "\"customer_id\":\"cust_Aa000000000001\",\n" +
                "\"account_type\":\"bank_account\",\n" +
                "\"bank_account\":{\n" +
                "\"name\":\"Gaurav Kumar\",\n" +
                "\"account_number\":\"11214311215411\",\n" +
                "\"ifsc\":\"HDFC0000053\",\n" +
                "\"bank_name\":\"HDFC Bank\"\n" +
                "},\n" +
                "\"active\":true,\n" +
                "\"created_at\":1543650891\n}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            FundAccount fetch = fundAccountClient.fetch(FUNDACCOUNT_ID);
            assertNotNull(fetch);
            assertEquals(FUNDACCOUNT_ID,fetch.get("id"));
            String fetchRequest = getHost(String.format(Constants.FUND_ACCOUNT_FETCH,FUNDACCOUNT_ID));
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Retrieve all fund account.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws RazorpayException {

        String mockedResponseJson = "{\n" +
                "    \"entity\": \"collection\",\n" +
                "    \"count\": 2,\n" +
                "    \"items\": [\n" +
                "        {\n" +
                "            \"id\": \"fa_Aa00000000001\",\n" +
                "            \"entity\": \"fund_account\",\n" +
                "            \"customer_id\": \"cust_JZse2vlC5nK9AQ\",\n" +
                "            \"account_type\": \"bank_account\",\n" +
                "            \"bank_account\": {\n" +
                "                \"ifsc\": \"HDFC0000053\",\n" +
                "                \"bank_name\": \"HDFC Bank\",\n" +
                "                \"name\": \"Gaurav Kumar\",\n" +
                "                \"notes\": [],\n" +
                "                \"account_number\": \"11214311215411\"\n" +
                "            },\n" +
                "            \"batch_id\": null,\n" +
                "            \"active\": true,\n" +
                "            \"created_at\": 1654154246\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<FundAccount> fetch = fundAccountClient.fetchAll();
            assertNotNull(fetch);
            assertEquals(FUNDACCOUNT_ID,fetch.get(0).get("id"));
            String fetchRequest = getHost(String.format(Constants.FUND_ACCOUNT_LIST));
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}