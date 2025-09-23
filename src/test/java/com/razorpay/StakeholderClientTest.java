package com.razorpay;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StakeholderClientTest extends BaseTest{
    @InjectMocks
    protected StakeholderClient stakeholderClient= new StakeholderClient(TEST_SECRET_KEY);

    private static final String ACCOUNT_ID = "acc_GRWKk7qQsLnDjX";

    private static final String STAKEHOLDER_ID = "sth_GLGgm8fFCKc92m";

    @Test
    public void testStakeholderCreate() throws RazorpayException{
        JSONObject request = new JSONObject("{\n" +
                "  \"percentage_ownership\": 10,\n" +
                "  \"name\": \"Gaurav Kumar\",\n" +
                "  \"email\": \"gaurav.kumar@example.com\",\n" +
                "  \"relationship\": {\n" +
                "    \"director\": true,\n" +
                "    \"executive\": false\n" +
                "  },\n" +
                "  \"phone\": {\n" +
                "    \"primary\": \"7474747474\",\n" +
                "    \"secondary\": \"7474747474\"\n" +
                "  },\n" +
                "  \"addresses\": {\n" +
                "    \"residential\": {\n" +
                "      \"street\": \"506, Koramangala 1st block\",\n" +
                "      \"city\": \"Bengaluru\",\n" +
                "      \"state\": \"Karnataka\",\n" +
                "      \"postal_code\": \"560034\",\n" +
                "      \"country\": \"IN\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"kyc\": {\n" +
                "    \"pan\": \"AVOPB1111K\"\n" +
                "  },\n" +
                "  \"notes\": {\n" +
                "    \"random_key_by_partner\": \"random_value\"\n" +
                "  }\n" +
                "}");

        String mockedResponseJson = getResponse(STAKEHOLDER_ID);
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Stakeholder fetch = stakeholderClient.create(ACCOUNT_ID,request);
            assertNotNull(fetch);
            assertEquals(STAKEHOLDER_ID,fetch.get("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testAccountFetch() throws RazorpayException {
        String mockedResponseJson = getResponse(STAKEHOLDER_ID);
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Stakeholder fetch = stakeholderClient.fetch(ACCOUNT_ID,STAKEHOLDER_ID);
            assertNotNull(fetch);
            assertEquals(STAKEHOLDER_ID,fetch.get("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testAccountEdit() throws RazorpayException {
        JSONObject request = new JSONObject("{\n" +
                "  \"percentage_ownership\": 20,\n" +
                "  \"name\": \"Gauri Kumar\",\n" +
                "  \"relationship\": {\n" +
                "    \"director\": false,\n" +
                "    \"executive\": true\n" +
                "  },\n" +
                "  \"phone\": {\n" +
                "    \"primary\": \"9898989898\",\n" +
                "    \"secondary\": \"9898989898\"\n" +
                "  },\n" +
                "  \"addresses\": {\n" +
                "    \"residential\": {\n" +
                "      \"street\": \"507, Koramangala 1st block\",\n" +
                "      \"city\": \"Bangalore\",\n" +
                "      \"state\": \"Karnataka\",\n" +
                "      \"postal_code\": \"560035\",\n" +
                "      \"country\": \"IN\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"kyc\": {\n" +
                "    \"pan\": \"AVOPB1111J\"\n" +
                "  },\n" +
                "  \"notes\": {\n" +
                "    \"random_key_by_partner\": \"random_value2\"\n" +
                "  }\n" +
                "}");

        String mockedResponseJson = getResponse(STAKEHOLDER_ID);
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Stakeholder fetch = stakeholderClient.edit(ACCOUNT_ID, STAKEHOLDER_ID, request);
            assertNotNull(fetch);
            assertEquals(STAKEHOLDER_ID,fetch.get("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testStakeholderFetchAll() throws RazorpayException {
        try {
            String mockedResponseJson = getResponseCollection();
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Stakeholder> fetch = stakeholderClient.fetchAll(ACCOUNT_ID);
            assertNotNull(fetch);
            assertEquals(true,fetch.get(0).has("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    private String getResponse(String stakeholder_id) {
        return "{\n" +
                "  \"entity\": \"stakeholder\",\n" +
                "  \"relationship\": {\n" +
                "    \"director\": true\n" +
                "  },\n" +
                "  \"phone\": {\n" +
                "    \"primary\": \"7474747474\",\n" +
                "    \"secondary\": \"7474747474\"\n" +
                "  },\n" +
                "  \"notes\": {\n" +
                "    \"random_key_by_partner\": \"random_value\"\n" +
                "  },\n" +
                "  \"kyc\": {\n" +
                "    \"pan\": \"AVOPB1111K\"\n" +
                "  },\n" +
                "  \"id\": "+stakeholder_id+",\n" +
                "  \"name\": \"Gaurav Kumar\",\n" +
                "  \"email\": \"gaurav.kumar@example.com\",\n" +
                "  \"percentage_ownership\": 10,\n" +
                "  \"addresses\": {\n" +
                "    \"residential\": {\n" +
                "      \"street\": \"506, Koramangala 1st block\",\n" +
                "      \"city\": \"Bengaluru\",\n" +
                "      \"state\": \"Karnataka\",\n" +
                "      \"postal_code\": \"560034\",\n" +
                "      \"country\": \"IN\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    private String getResponseCollection(){
        return "{\n" +
                "  \"entity\": \"collection\",\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": \"GZ13yPHLJof9IE\",\n" +
                "      \"entity\": \"stakeholder\",\n" +
                "      \"relationship\": {\n" +
                "        \"director\": true\n" +
                "      },\n" +
                "      \"phone\": {\n" +
                "        \"primary\": \"9000090000\",\n" +
                "        \"secondary\": \"9000090000\"\n" +
                "      },\n" +
                "      \"notes\": {\n" +
                "        \"random_key_by_partner\": \"random_value\"\n" +
                "      },\n" +
                "      \"kyc\": {\n" +
                "        \"pan\": \"AVOPB1111K\"\n" +
                "      },\n" +
                "      \"name\": \"Gaurav Kumar\",\n" +
                "      \"email\": \"gaurav.kumar@acme.org\",\n" +
                "      \"percentage_ownership\": 10,\n" +
                "      \"addresses\": {\n" +
                "        \"residential\": {\n" +
                "          \"street\": \"506, Koramangala 1st block\",\n" +
                "          \"city\": \"Bengaluru\",\n" +
                "          \"state\": \"Karnataka\",\n" +
                "          \"postal_code\": \"560034\",\n" +
                "          \"country\": \"in\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"count\": 1\n" +
                "}";
    }

    @Test
    public void testuploadStakeholderDoc() throws RazorpayException {
        JSONObject request = new JSONObject();
        request.put("files","/Users/your_name/Downloads/sample_uploaded.jpeg");
        request.put("document_type","aadhar_front");

        JSONObject mockedResponseJson = new JSONObject();
        mockedResponseJson.put("entity", "account");
        JSONArray addressArray = new JSONArray();
        JSONObject addressObj = new JSONObject();
        addressObj.put("type","aadhar_front");
        addressObj.put("url","https://rzp.io/i/bzDAbNg");
        addressArray.put(addressObj);
        mockedResponseJson.put("individual_proof_of_address", addressArray);

        try {
            mockResponseFromExternalClient(mockedResponseJson.toString());
            mockResponseHTTPCodeFromExternalClient(200);
            Account document = stakeholderClient.uploadStakeholderDoc(ACCOUNT_ID, STAKEHOLDER_ID, request);
            assertNotNull(document);
            assertEquals(true,document.has("individual_proof_of_address"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testfetchStakeholderDoc() throws RazorpayException {

        JSONObject mockedResponseJson = new JSONObject();
        mockedResponseJson.put("entity", "account");
        JSONArray addressArray = new JSONArray();
        JSONObject addressObj = new JSONObject();
        addressObj.put("type","aadhar_front");
        addressObj.put("url","https://rzp.io/i/bzDAbNg");
        addressArray.put(addressObj);
        mockedResponseJson.put("individual_proof_of_address", addressArray);

        try {
            mockResponseFromExternalClient(mockedResponseJson.toString());
            mockResponseHTTPCodeFromExternalClient(200);
            Account document = stakeholderClient.fetchStakeholderDoc(ACCOUNT_ID, STAKEHOLDER_ID);
            assertNotNull(document);
            assertEquals(true,document.has("individual_proof_of_address"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}
