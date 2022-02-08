package com.razorpay;

import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class CardClientTest extends BaseTest{

    @InjectMocks
    protected CardClient cardClientClient = new CardClient(TEST_SECRET_KEY);

    private static final String CARD_ID = "card_DZon6fd8J3IcA2";

    @Test
    public void fetch() throws RazorpayException {

        String mockedResponseJson = "{\n  \"id\": "+CARD_ID+",\n  \"entity\": \"card\",\n  \"international\": false,\n  \"last4\": 1111,\n  \"name\": \"sample name\",\n  \"network\": \"Visa\",\n  \"type\": \"debit\"\n}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Card fetch = cardClientClient.fetch(CARD_ID);
            assertNotNull(fetch);
            assertEquals(CARD_ID,fetch.get("id"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}