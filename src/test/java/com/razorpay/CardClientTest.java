package com.razorpay;

import org.json.JSONException;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CardClientTest extends BaseTest{

    @Mock
    ApiUtils apiUtils;

    private static final String CARD_ID = "card_DZon6fd8J3IcA2";

    private static final String PAYMENT_ID = "pay_IDRP0tbirMSsbn";

    /** Retrieving a specific card details using card id
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws RazorpayException, JSONException, URISyntaxException {

        String mockedResponseJson = "{\n  \"id\": "+CARD_ID+",\n  \"entity\": \"card\",\n  \"international\": false,\n  \"last4\": 1111,\n  \"name\": \"sample name\",\n  \"network\": \"Visa\",\n  \"type\": \"debit\"\n}";
        try {
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(String.format(Constants.CARD_GET, CARD_ID), null);
            when(apiUtils.processGetRequest(builder.toString(),null,"test")).thenReturn(mockedResponseJson);

            CardClient cardClient = new CardClient("test",apiUtils);

            Card fetch = cardClient.fetch(CARD_ID);
            assertNotNull(fetch);
            assertEquals(CARD_ID,fetch.get("id"));
            assertTrue(fetch.has("international"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /** Fetch the details of the card used to make a payment
     * @throws RazorpayException
     * @return void
     */
    @Test
    public void fetchCardDetails() throws RazorpayException, JSONException, URISyntaxException {

        String mockedResponseJson = "{\"id\":"+CARD_ID+",\"entity\":\"card\",\"name\":\"GauravKumar\",\"last4\":\"8430\",\"network\":\"Visa\",\"type\":\"credit\",\"issuer\":\"HDFC\",\"international\":false,\"emi\":true,\"sub_type\":\"consumer\",\"token_iin\":null}";
        try {
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(String.format(Constants.FETCH_CARD_DETAILS, PAYMENT_ID), null);
            when(apiUtils.processGetRequest(builder.toString(),null,"test")).thenReturn(mockedResponseJson);

            CardClient cardClient = new CardClient("test",apiUtils);

            Card fetch = cardClient.fetchCardDetails(PAYMENT_ID);
            assertNotNull(fetch);
            assertEquals(CARD_ID,fetch.get("id"));
            assertTrue(fetch.has("name"));
            assertTrue(fetch.has("network"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}