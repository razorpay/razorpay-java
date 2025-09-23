package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class CardClientTest extends BaseTest{

    @InjectMocks
    protected CardClient cardClientClient = new CardClient(TEST_SECRET_KEY);

    private static final String CARD_ID = "card_DZon6fd8J3IcA2";

    private static final String PAYMENT_ID = "pay_IDRP0tbirMSsbn";

    /** Retrieving a specific card details using card id
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws RazorpayException {

        String mockedResponseJson = "{\n  \"id\": "+CARD_ID+",\n  \"entity\": \"card\",\n  \"international\": false,\n  \"last4\": 1111,\n  \"name\": \"sample name\",\n  \"network\": \"Visa\",\n  \"type\": \"debit\"\n}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Card fetch = cardClientClient.fetch(CARD_ID);
            assertNotNull(fetch);
            assertEquals(CARD_ID,fetch.get("id"));
            assertTrue(fetch.has("international"));
            String addonCreate = getHost(String.format(Constants.CARD_GET, CARD_ID));
            verifySentRequest(false, null, addonCreate);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /** Fetch the details of the card used to make a payment
     * @throws RazorpayException
     * @return void
     */
    @Test
    public void fetchCardDetails() throws RazorpayException {

        String mockedResponseJson = "{\"id\":"+CARD_ID+",\"entity\":\"card\",\"name\":\"GauravKumar\",\"last4\":\"8430\",\"network\":\"Visa\",\"type\":\"credit\",\"issuer\":\"HDFC\",\"international\":false,\"emi\":true,\"sub_type\":\"consumer\",\"token_iin\":null}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Card fetch = cardClientClient.fetchCardDetails(PAYMENT_ID);
            assertNotNull(fetch);
            assertEquals(CARD_ID,fetch.get("id"));
            assertTrue(fetch.has("name"));
            assertTrue(fetch.has("network"));
            String fetchCardDetails = getHost(String.format(Constants.FETCH_CARD_DETAILS, PAYMENT_ID));
            verifySentRequest(false, null, fetchCardDetails);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testUsingCardNumber() throws RazorpayException {

        String mockedResponseJson = "{\n" +
                "  \"entity\": \"card\",\n" +
                "  \"network\": \"Visa\",\n" +
                "  \"payment_account_reference\": \"V0010013819231376539033235990\",\n" +
                "  \"network_reference_id\": null\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            JSONObject request = new JSONObject();
            request.put("number","4854980604708430");
            Card fetch = cardClientClient.requestCardReference(request);
            assertNotNull(fetch);
            assertEquals(true,fetch.has("network"));
            String fetchCardDetails = getHost(Constants.CARD_REQUEST_REFERENCE);
            verifySentRequest(true, request.toString(), fetchCardDetails);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

}