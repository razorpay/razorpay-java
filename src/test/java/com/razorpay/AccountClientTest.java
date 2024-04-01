package com.razorpay;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AccountClientTest extends BaseTest{
    @InjectMocks
    protected AccountClient accountClient= new AccountClient(TEST_SECRET_KEY);

    private static final String ACCOUNT_ID = "acc_GRWKk7qQsLnDjX";

    @Test
    public void testAccountCreate() throws RazorpayException{
        JSONObject request = new JSONObject("{\n" +
                "  \"email\": \"gauriagain.kumar@example.org\",\n" +
                "  \"phone\": \"9000090000\",\n" +
                "  \"legal_business_name\": \"Acme Corp\",\n" +
                "  \"business_type\": \"partnership\",\n" +
                "  \"customer_facing_business_name\": \"Example\",\n" +
                "  \"profile\": {\n" +
                "    \"category\": \"healthcare\",\n" +
                "    \"subcategory\": \"clinic\",\n" +
                "    \"description\": \"Healthcare E-commerce platform\",\n" +
                "    \"addresses\": {\n" +
                "      \"operation\": {\n" +
                "        \"street1\": \"507, Koramangala 6th block\",\n" +
                "        \"street2\": \"Kormanagala\",\n" +
                "        \"city\": \"Bengaluru\",\n" +
                "        \"state\": \"Karnataka\",\n" +
                "        \"postal_code\": 560047,\n" +
                "        \"country\": \"IN\"\n" +
                "      },\n" +
                "      \"registered\": {\n" +
                "        \"street1\": \"507, Koramangala 1st block\",\n" +
                "        \"street2\": \"MG Road\",\n" +
                "        \"city\": \"Bengaluru\",\n" +
                "        \"state\": \"Karnataka\",\n" +
                "        \"postal_code\": 560034,\n" +
                "        \"country\": \"IN\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"business_model\": \"Online Clothing ( men, women, ethnic, modern ) fashion and lifestyle, accessories, t-shirt, shirt, track pant, shoes.\"\n" +
                "  },\n" +
                "  \"legal_info\": {\n" +
                "    \"pan\": \"AAACL1234C\",\n" +
                "    \"gst\": \"18AABCU9603R1ZM\"\n" +
                "  },\n" +
                "  \"brand\": {\n" +
                "    \"color\": \"FFFFFF\"\n" +
                "  },\n" +
                "  \"notes\": {\n" +
                "    \"internal_ref_id\": \"123123\"\n" +
                "  },\n" +
                "  \"contact_name\": \"Gaurav Kumar\",\n" +
                "  \"contact_info\": {\n" +
                "    \"chargeback\": {\n" +
                "      \"email\": \"cb@example.org\"\n" +
                "    },\n" +
                "    \"refund\": {\n" +
                "      \"email\": \"cb@example.org\"\n" +
                "    },\n" +
                "    \"support\": {\n" +
                "      \"email\": \"support@example.org\",\n" +
                "      \"phone\": \"9999999998\",\n" +
                "      \"policy_url\": \"https://www.google.com\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"apps\": {\n" +
                "    \"websites\": [\n" +
                "      \"https://www.example.org\"\n" +
                "    ],\n" +
                "    \"android\": [\n" +
                "      {\n" +
                "        \"url\": \"playstore.example.org\",\n" +
                "        \"name\": \"Example\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"ios\": [\n" +
                "      {\n" +
                "        \"url\": \"appstore.example.org\",\n" +
                "        \"name\": \"Example\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}");

        String mockedResponseJson = getResponse(ACCOUNT_ID);
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Account fetch = accountClient.create(request);
            assertNotNull(fetch);
            assertEquals(ACCOUNT_ID,fetch.get("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testAccountFetch() throws RazorpayException {
        String mockedResponseJson = getResponse(ACCOUNT_ID);
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Account fetch = accountClient.fetch(ACCOUNT_ID);
            assertNotNull(fetch);
            assertEquals(ACCOUNT_ID,fetch.get("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testAccountEdit() throws RazorpayException {
        JSONObject request = new JSONObject("{\n" +
                "\t\"customer_facing_business_name\": \"ABCD Ltd\"\n" +
                "}");

        String mockedResponseJson = getResponse(ACCOUNT_ID);
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Account fetch = accountClient.edit(ACCOUNT_ID,request);
            assertNotNull(fetch);
            assertEquals(ACCOUNT_ID,fetch.get("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testAccountDelete() throws RazorpayException {
        try {
            String mockedResponseJson = getResponse(ACCOUNT_ID);
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Account fetch = accountClient.delete(ACCOUNT_ID);
            assertNotNull(fetch);
            assertEquals(ACCOUNT_ID,fetch.get("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    private String getResponse(String account_id) {
        return "{\n" +
                "  \"id\": "+account_id+",\n" +
                "  \"entity\": \"account\",\n" +
                "  \"type\": \"standard\",\n" +
                "  \"status\": \"created\",\n" +
                "  \"email\": \"gauriagain.kumar@example.org\",\n" +
                "  \"profile\": {\n" +
                "    \"category\": \"healthcare\",\n" +
                "    \"subcategory\": \"clinic\",\n" +
                "    \"addresses\": {\n" +
                "      \"registered\": {\n" +
                "        \"street1\": \"507, Koramangala 1st block\",\n" +
                "        \"street2\": \"MG Road\",\n" +
                "        \"city\": \"Bengaluru\",\n" +
                "        \"state\": \"KARNATAKA\",\n" +
                "        \"postal_code\": 560034,\n" +
                "        \"country\": \"IN\"\n" +
                "      },\n" +
                "      \"operation\": {\n" +
                "        \"street1\": \"507, Koramangala 6th block\",\n" +
                "        \"street2\": \"Kormanagalo\",\n" +
                "        \"city\": \"Bengaluru\",\n" +
                "        \"state\": \"KARNATAKA\",\n" +
                "        \"country\": \"IN\",\n" +
                "        \"postal_code\": 560047\n" +
                "      }\n" +
                "    },\n" +
                "    \"business_model\": \"Online Clothing ( men, women, ethnic, modern ) fashion and lifestyle, accessories, t-shirt, shirt, track pant, shoes.\"\n" +
                "  },\n" +
                "  \"notes\": {\n" +
                "    \"internal_ref_id\": \"123123\"\n" +
                "  },\n" +
                "  \"created_at\": 1611136837,\n" +
                "  \"phone\": \"9000090000\",\n" +
                "  \"business_type\": \"partnership\",\n" +
                "  \"legal_business_name\": \"Acme Corp\",\n" +
                "  \"customer_facing_business_name\": \"Example\",\n" +
                "  \"legal_info\": {\n" +
                "    \"pan\": \"AAACL1234C\",\n" +
                "    \"gst\": \"18AABCU9603R1ZM\"\n" +
                "  },\n" +
                "  \"apps\": {\n" +
                "    \"websites\": [\n" +
                "      \"https://www.example.org\"\n" +
                "    ],\n" +
                "    \"android\": [\n" +
                "      {\n" +
                "        \"url\": \"playstore.example.org\",\n" +
                "        \"name\": \"Example\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"ios\": [\n" +
                "      {\n" +
                "        \"url\": \"appstore.example.org\",\n" +
                "        \"name\": \"Example\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"brand\": {\n" +
                "    \"color\": \"#FFFFFF\"\n" +
                "  },\n" +
                "  \"contact_info\": {\n" +
                "    \"chargeback\": {\n" +
                "      \"email\": \"cb@example.org\",\n" +
                "      \"phone\": null,\n" +
                "      \"policy_url\": null\n" +
                "    },\n" +
                "    \"refund\": {\n" +
                "      \"email\": \"cb@example.org\",\n" +
                "      \"phone\": null,\n" +
                "      \"policy_url\": null\n" +
                "    },\n" +
                "    \"support\": {\n" +
                "      \"email\": \"support@example.org\",\n" +
                "      \"phone\": \"9999999998\",\n" +
                "      \"policy_url\": \"https://www.google.com\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    @Test
    public void uploadAccountDoc() throws RazorpayException {
        JSONObject request = new JSONObject();
        request.put("files","/Users/your_name/Downloads/sample_uploaded.jpeg");
        request.put("document_type","business_proof_url");

        JSONObject mockedResponseJson = new JSONObject();
        mockedResponseJson.put("entity","account");
        JSONArray businessArray = new JSONArray();
        JSONObject businessObj = new JSONObject();
        businessObj.put("type","business_proof_url");
        businessObj.put("url","<https://rzp.io/i/bzDKbNg>");
        businessArray.put(businessObj);
        mockedResponseJson.put("business_proof_of_identification",businessArray);

        try {
            mockResponseFromExternalClient(mockedResponseJson.toString());
            mockResponseHTTPCodeFromExternalClient(200);
            Account document = accountClient.uploadAccountDoc(ACCOUNT_ID, request);
            assertNotNull(document);
            assertEquals(true,document.has("business_proof_of_identification"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void fetchAccountDoc() throws RazorpayException {

        JSONObject mockedResponseJson = new JSONObject();
        mockedResponseJson.put("entity","account");
        JSONArray businessArray = new JSONArray();
        JSONObject businessObj = new JSONObject();
        businessObj.put("type","business_proof_url");
        businessObj.put("url","<https://rzp.io/i/bzDKbNg>");
        businessArray.put(businessObj);
        mockedResponseJson.put("business_proof_of_identification",businessArray);
        try {
            mockResponseFromExternalClient(mockedResponseJson.toString());
            mockResponseHTTPCodeFromExternalClient(200);
            Account document = accountClient.fetchAccountDoc(ACCOUNT_ID);
            assertNotNull(document);
            assertEquals(true,document.has("business_proof_of_identification"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}
