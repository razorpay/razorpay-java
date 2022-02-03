package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.List;
import static org.junit.Assert.*;

public class CustomerClientTest extends BaseTest{

    @InjectMocks
    private CustomerClient customerClient = new CustomerClient("test");

    public static final String CUSTOMER_ID = "cust_1Aa00000000004";

    public static final String TOKEN_ID = "token_Hxe0skTXLeg9pF";

    @Test
    public void create() throws RazorpayException{

        JSONObject request = new JSONObject("{\n  \"name\": \"Gaurav Kumar\",\n  \"contact\": 9123456780,\n  \"email\": \"gaurav.kumar@example.com\",\n  \"fail_existing\": 0,\n  \"gstin\": \"29XAbbA4369J1PA\",\n  \"notes\": {\n    \"notes_key_1\": \"Tea, Earl Grey, Hot\",\n    \"notes_key_2\": \"Tea, Earl Grey\u2026 decaf.\"\n  }\n}");
        String json = "{\n  \"id\" : \"cust_1Aa00000000004\",\n  \"entity\": \"customer\",\n  \"name\" : \"Gaurav Kumar\",\n  \"email\" : \"gaurav.kumar@example.com\",\n  \"contact\" : \"9123456780\",\n  \"gstin\": \"29XAbbA4369J1PA\",\n  \"notes\":{\n    \"notes_key_1\":\"Tea, Earl Grey, Hot\",\n    \"notes_key_2\":\"Tea, Earl Grey\u2026 decaf.\"\n  },\n  \"created_at \": 1234567890\n}";
        try {
            mockResponseFromExternalClient(json);
            mockResponseHTTPCodeFromExternalClient(200);
            Customer customer = customerClient.create(request);
            assertNotNull(customer);
            assertEquals(CUSTOMER_ID,customer.get("id"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fetch() throws RazorpayException {

        String json = "{\n  \"id\" : \"cust_1Aa00000000004\",\n  \"entity\": \"customer\",\n  \"name\" : \"Saurav Kumar\",\n  \"email\" : \"Saurav.kumar@example.com\",\n  \"contact\" : \"+919000000000\",\n  \"gstin\":\"29XAbbA4369J1PA\",\n  \"notes\" : [],\n  \"created_at \": 1234567890\n}\n";
        try {
            mockResponseFromExternalClient(json);
            mockResponseHTTPCodeFromExternalClient(200);
            Customer fetch = customerClient.fetch(CUSTOMER_ID);
            assertNotNull(fetch);
            assertEquals(CUSTOMER_ID,fetch.get("id"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fetchAll() throws RazorpayException {
        String json = "{\n  \"entity\":\"collection\",\n  \"count\":1,\n  \"items\":[\n    {\n      \"id\":\"cust_1Aa00000000001\",\n      \"entity\":\"customer\",\n      \"name\":\"Gaurav Kumar\",\n      \"email\":\"gaurav.kumar@example.com\",\n      \"contact\":\"9876543210\",\n      \"gstin\":\"29XAbbA4369J1PA\",\n      \"notes\":{\n        \"note_key_1\":\"September\",\n        \"note_key_2\":\"Make it so.\"\n      },\n      \"created_at \":1234567890\n    }\n  ]\n}";
        try {
            mockResponseFromExternalClient(json);
            mockResponseHTTPCodeFromExternalClient(200);
            List <Customer> fetch = customerClient.fetchAll();
            assertNotNull(fetch);
            assertEquals(true,fetch.get(0).has("id"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void edit() throws RazorpayException {

        JSONObject request = new JSONObject("{\n  \"name\": \"Gaurav Kumar\",\n  \"email\": \"Gaurav.Kumar@example.com\",\n  \"contact\": 9000000000\n}");
        String json = "{\n  \"id\": \"cust_1Aa00000000004\",\n  \"entity\": \"customer\",\n  \"name\": \"Gaurav Kumar\",\n  \"email\": \"Gaurav.Kumar@example.com\",\n  \"contact\": \"9000000000\",\n  \"gstin\": null,\n  \"notes\": {\n    \"notes_key_1\": \"Tea, Earl Grey, Hot\",\n    \"notes_key_2\": \"Tea, Earl Grey\u2026 decaf.\"\n  },\n  \"created_at\": 1582033731\n}";
        try {
            mockResponseFromExternalClient(json);
            mockResponseHTTPCodeFromExternalClient(200);
            Customer fetch = customerClient.edit(CUSTOMER_ID,request);
            assertNotNull(fetch);
            assertEquals(CUSTOMER_ID,fetch.get("id"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fetchTokens() throws RazorpayException{
        String json = "{\n    \"id\": \"token_Hxe0skTXLeg9pF\",\n    \"entity\": \"token\",\n    \"token\": \"F85BgXnGVwcuqV\",\n    \"bank\": null,\n    \"wallet\": null,\n    \"method\": \"card\",\n    \"card\": {\n        \"entity\": \"card\",\n        \"name\": \"ankit\",\n        \"last4\": \"5449\",\n        \"network\": \"MasterCard\",\n        \"type\": \"credit\",\n        \"issuer\": \"UTIB\",\n        \"international\": false,\n        \"emi\": false,\n        \"sub_type\": \"consumer\",\n        \"expiry_month\": 12,\n        \"expiry_year\": 2024,\n        \"flows\": {\n            \"recurring\": true\n        }\n    },\n    \"recurring\": true,\n    \"auth_type\": null,\n    \"mrn\": null,\n    \"used_at\": 1632976165,\n    \"created_at\": 1631687852,\n    \"expired_at\": 1634215992,\n    \"dcc_enabled\": false\n}";
        try {
            mockResponseFromExternalClient(json);
            mockResponseHTTPCodeFromExternalClient(200);
            Token fetch = customerClient.fetchToken(CUSTOMER_ID,TOKEN_ID);
            assertNotNull(fetch);
            assertEquals(TOKEN_ID,fetch.get("id"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fetchToken() throws RazorpayException{
        String json = "{\n   \"entity\":\"collection\",\n   \"count\":1,\n   \"items\":[\n      {\n         \"id\":\"token_Hxe0skTXLeg9pF\",\n         \"entity\":\"token\",\n         \"token\":\"2JPRk664pZHUWG\",\n         \"bank\":null,\n         \"wallet\":null,\n         \"method\":\"card\",\n         \"card\":{\n            \"entity\":\"card\",\n            \"name\":\"Gaurav Kumar\",\n            \"last4\":\"8950\",\n            \"network\":\"Visa\",\n            \"type\":\"credit\",\n            \"issuer\":\"STCB\",\n            \"international\":false,\n            \"emi\":false,\n            \"sub_type\":\"consumer\",\n            \"expiry_month\":12,\n            \"expiry_year\":2021,\n            \"flows\":{\n               \"otp\":true,\n               \"recurring\":true\n            }\n         },\n         \"recurring\":true,\n         \"recurring_details\":{\n            \"status\":\"confirmed\",\n            \"failure_reason\":null\n         },\n         \"auth_type\":null,\n         \"mrn\":null,\n         \"used_at\":1629779657,\n         \"created_at\":1629779657,\n         \"expired_at\":1640975399,\n         \"dcc_enabled\":false,\n         \"billing_address\":null\n      }\n   ]\n}";
        try {
            mockResponseFromExternalClient(json);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Token> fetch = customerClient.fetchTokens(CUSTOMER_ID);
            assertNotNull(fetch);
            assertEquals(true,fetch.get(0).has("id"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}