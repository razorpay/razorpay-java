package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;

import static org.junit.Assert.*;

public class ProductClientTest extends BaseTest{

    @InjectMocks
    protected ProductClient productClient = new ProductClient(TEST_SECRET_KEY);
    private static final String ACCOUNT_ID = "acc_GRWKk7qQsLnDjX";

    private static final String TNC_ID = "tnc_map_HjOVhIdpVDZ0FB";
    private static final String PRODUCT_ID = "acc_prd_HEgNpywUFctQ9f";

    @Test
    public void testProductRequestProduction() throws RazorpayException{
        JSONObject request = new JSONObject("{\n" +
                "  \"product_name\": \"payment_gateway\",\n" +
                "  \"tnc_accepted\": true,\n" +
                "  \"ip\": \"233.233.233.234\"\n" +
                "}");

        String mockedResponseJson = getResponse(ACCOUNT_ID);
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Account fetch = productClient.requestProductConfiguration(ACCOUNT_ID,request);
            assertNotNull(fetch);
            assertEquals(ACCOUNT_ID,fetch.get("account_id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testProductFetch() throws RazorpayException {
        String mockedResponseJson = getResponse(ACCOUNT_ID);
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Account fetch = productClient.fetch(ACCOUNT_ID, PRODUCT_ID);
            assertNotNull(fetch);
            assertEquals(ACCOUNT_ID,fetch.get("account_id"));
            assertEquals(PRODUCT_ID,fetch.get("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testAccountEdit() throws RazorpayException {
        JSONObject request = new JSONObject("{\n" +
                "  \"notifications\": {\n" +
                "    \"email\": [\n" +
                "      \"gaurav.kumar@example.com\",\n" +
                "      \"acd@gmail.com\"\n" +
                "    ]\n" +
                "  },\n" +
                "  \"checkout\": {\n" +
                "    \"theme_color\": \"#528FFF\"\n" +
                "  },\n" +
                "  \"refund\": {\n" +
                "    \"default_refund_speed\": \"optimum\"\n" +
                "  },\n" +
                "  \"settlements\": {\n" +
                "    \"account_number\": \"1234567890\",\n" +
                "    \"ifsc_code\": \"HDFC0000317\",\n" +
                "    \"beneficiary_name\": \"Gaurav Kumar\"\n" +
                "  },\n" +
                "  \"tnc_accepted\": true,\n" +
                "  \"ip\": \"233.233.233.234\"\n" +
                "}");

        String mockedResponseJson = getResponse(ACCOUNT_ID);
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Account fetch = productClient.edit(ACCOUNT_ID, PRODUCT_ID, request);
            assertNotNull(fetch);
            assertEquals(ACCOUNT_ID,fetch.get("account_id"));
            assertEquals(PRODUCT_ID,fetch.get("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testTncFetch() throws RazorpayException {
        String mockedResponseJson = "{\n" +
                "  \"entity\": \"tnc_map\",\n" +
                "  \"product_name\": \"payments\",\n" +
                "  \"id\": \"tnc_map_HjOVhIdpVDZ0FB\",\n" +
                "  \"tnc\": {\n" +
                "    \"terms\": \"https://razorpay.com/terms\",\n" +
                "    \"privacy\": \"https://razorpay.com/privacy\",\n" +
                "    \"agreement\": \"https://razorpay.com/agreement\"\n" +
                "  },\n" +
                "  \"last_published_at\": 1640589653\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            TncMap fetch = productClient.fetchTnc("payment");
            assertNotNull(fetch);
            assertEquals(TNC_ID,fetch.get("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    private String getResponse(String account_id){
        return "{\n" +
                "  \"requested_configuration\": {\n" +
                "    \"payment_methods\": []\n" +
                "  },\n" +
                "  \"active_configuration\": {\n" +
                "    \"payment_capture\": {\n" +
                "      \"mode\": \"automatic\",\n" +
                "      \"refund_speed\": \"normal\",\n" +
                "      \"automatic_expiry_period\": 7200\n" +
                "    },\n" +
                "    \"settlements\": {\n" +
                "      \"account_number\": null,\n" +
                "      \"ifsc_code\": null,\n" +
                "      \"beneficiary_name\": null\n" +
                "    },\n" +
                "    \"checkout\": {\n" +
                "      \"theme_color\": \"#FFFFFF\",\n" +
                "      \"flash_checkout\": true,\n" +
                "      \"logo\": \"https://example.com/your_logo\"\n" +
                "    },\n" +
                "    \"refund\": {\n" +
                "      \"default_refund_speed\": \"normal\"\n" +
                "    },\n" +
                "    \"notifications\": {\n" +
                "      \"whatsapp\": true,\n" +
                "      \"sms\": false,\n" +
                "      \"email\": [\n" +
                "        \"b963e252-1201-45b0-9c39-c53eceb0cfd6_load@gmail.com\"\n" +
                "      ]\n" +
                "    },\n" +
                "    \"payment_methods\": {\n" +
                "      \"netbanking\": {\n" +
                "        \"enabled\": true,\n" +
                "        \"instrument\": [\n" +
                "          {\n" +
                "            \"type\": \"retail\",\n" +
                "            \"bank\": [\n" +
                "              \"hdfc\",\n" +
                "              \"sbin\",\n" +
                "              \"utib\",\n" +
                "              \"icic\",\n" +
                "              \"scbl\",\n" +
                "              \"yesb\"\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"wallet\": {\n" +
                "        \"enabled\": true,\n" +
                "        \"instrument\": [\n" +
                "          \"airtelmoney\",\n" +
                "          \"freecharge\",\n" +
                "          \"jiomoney\",\n" +
                "          \"olamoney\",\n" +
                "          \"payzapp\",\n" +
                "          \"mobikwik\"\n" +
                "        ]\n" +
                "      },\n" +
                "      \"upi\": {\n" +
                "        \"enabled\": true,\n" +
                "        \"instrument\": [\n" +
                "          \"upi\"\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"requirements\": [\n" +
                "    {\n" +
                "      \"field_reference\": \"individual_proof_of_address\",\n" +
                "      \"resolution_url\": \"/accounts/acc_HQVlm3bnPmccC0/stakeholders/{stakeholderId}/documents\",\n" +
                "      \"status\": \"required\",\n" +
                "      \"reason_code\": \"document_missing\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"field_reference\": \"individual_proof_of_identification\",\n" +
                "      \"resolution_url\": \"/accounts/acc_HQVlm3bnPmccC0/stakeholders/{stakeholderId}/documents\",\n" +
                "      \"status\": \"required\",\n" +
                "      \"reason_code\": \"document_missing\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"field_reference\": \"business_proof_of_identification\",\n" +
                "      \"resolution_url\": \"/accounts/acc_HQVlm3bnPmccC0/documents\",\n" +
                "      \"status\": \"required\",\n" +
                "      \"reason_code\": \"document_missing\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"field_reference\": \"settlements.beneficiary_name\",\n" +
                "      \"resolution_url\": \"/accounts/acc_HQVlm3bnPmccC0/products/acc_prd_HEgNpywUFctQ9f\",\n" +
                "      \"status\": \"required\",\n" +
                "      \"reason_code\": \"field_missing\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"field_reference\": \"settlements.account_number\",\n" +
                "      \"resolution_url\": \"/accounts/acc_HQVlm3bnPmccC0/products/acc_prd_HEgNpywUFctQ9f\",\n" +
                "      \"status\": \"required\",\n" +
                "      \"reason_code\": \"field_missing\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"field_reference\": \"settlements.ifsc_code\",\n" +
                "      \"resolution_url\": \"/accounts/acc_HQVlm3bnPmccC0/products/acc_prd_HEgNpywUFctQ9f\",\n" +
                "      \"status\": \"required\",\n" +
                "      \"reason_code\": \"field_missing\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"field_reference\": \"contact_name\",\n" +
                "      \"resolution_url\": \"/accounts/acc_HQVlm3bnPmccC0\",\n" +
                "      \"status\": \"required\",\n" +
                "      \"reason_code\": \"field_missing\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"field_reference\": \"name\",\n" +
                "      \"resolution_url\": \"/accounts/acc_HQVlm3bnPmccC0/stakeholders\",\n" +
                "      \"status\": \"required\",\n" +
                "      \"reason_code\": \"field_missing\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"field_reference\": \"customer_facing_business_name\",\n" +
                "      \"resolution_url\": \"/accounts/acc_HQVlm3bnPmccC0\",\n" +
                "      \"status\": \"required\",\n" +
                "      \"reason_code\": \"field_missing\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"field_reference\": \"kyc.pan\",\n" +
                "      \"resolution_url\": \"/accounts/acc_HQVlm3bnPmccC0/stakeholders\",\n" +
                "      \"status\": \"required\",\n" +
                "      \"reason_code\": \"field_missing\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"tnc\":{\n" +
                "    \"id\": \"tnc_IgohZaDBHRGjPv\",\n" +
                "    \"accepted\": true,\n" +
                "    \"accepted_at\": 1641550798\n" +
                "  },\n" +
                "  \"id\": \"acc_prd_HEgNpywUFctQ9f\",\n" +
                "  \"entity\": \"account\",\n" +
                "  \"account_id\": "+account_id+",\n" +
                "  \"product_name\": \"payment_links\",\n" +
                "  \"activation_status\": \"needs_clarification\",\n" +
                "  \"requested_at\": 162547884\n" +
                "}";
    }

}
