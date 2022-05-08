package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ItemClientTest extends BaseTest{

    @InjectMocks
    protected ItemClient itemClient = new ItemClient("test");

    public static final String ITEM_ID = "item_IJol6jPh1ummTK";
    
    /**
     * Create item with basic details such as name and amount details
     * @throws RazorpayException
     */
    @Test
    public void create() throws RazorpayException {
        JSONObject request = new JSONObject("{\n" +
                "\"name\": \"Book English August\",\n" +
                "\"description\": \"An indian story, Booker prize winner.\",\n" +
                "\"amount\": 20000,\n" +
                "\"currency\": \"INR\"\n" +
                "}");

        String mockedResponseJson = "{\n" +
                "\"entity\" : \"item\",\n" +
                "\"id\": "+ITEM_ID+",\n" +
                "\"active\": true,\n" +
                "\"name\": \"Book English August\",\n" +
                "\"description\": \"An indian story, Booker prize winner.\",\n" +
                "\"amount\": 20000,\n" +
                "\"currency\": \"INR\"\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Item item = itemClient.create(request);
            assertNotNull(item);
            assertEquals(ITEM_ID,item.get("id"));
            assertEquals("item",item.get("entity"));
            assertTrue(item.has("active"));
            assertTrue(item.has("name"));
            assertTrue(item.has("name"));
            String createRequest = getHost(Constants.ITEMS);
            verifySentRequest(true, request.toString(), createRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Retrieve the item details using item id.
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws RazorpayException {

        String mockedResponseJson = "{\n  " +
                "\"entity\": \"item\",\n" +
                "\"id\": "+ITEM_ID+",\n" +
                "\"active\": true,\n" +
                "\"name\": \"Book English August\",\n" +
                "\"description\": \"An indian story, Booker prize winner.\",\n" +
                "\"amount\": 20000,\n" +
                "\"currency\": \"INR\"\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Item fetch = itemClient.fetch(ITEM_ID);
            assertNotNull(fetch);
            assertEquals(ITEM_ID,fetch.get("id"));
            assertEquals("item",fetch.get("entity"));
            assertTrue(fetch.has("amount"));
            assertTrue(fetch.has("currency"));
            String fetchRequest = getHost(String.format(Constants.ITEM,ITEM_ID));
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }
    
    /**
     * Details of all the items can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws RazorpayException{

        String mockedResponseJson = "{\n  " +
                "\"entity\": \"collection\",\n" +
                "\"count\": 1,\n" +
                "\"items\": [\n" +
                "{\n" +
                  "\"id\": \"item_7OxoGnoxCuUKbo\",\n" +
                  "\"entity\" : \"item\",\n" +
                  "\"active\": true,\n" +
                  "\"name\": \"Book  English August\",\n" +
                  "\"description\": null,\n" +
                  "\"amount\": 20000,\n" +
                  "\"currency\": \"INR\"\n" +
                  "}\n]\n}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Item> fetch = itemClient.fetchAll();
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("entity"));
            assertTrue(fetch.get(0).has("id"));
            assertTrue(fetch.get(0).has("active"));
            String fetchRequest = getHost(Constants.ITEMS);
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }
    
    /**
     * Update an item details using item id with object of that properties
     * @throws RazorpayException
     */
    @Test
    public void edit() throws RazorpayException{

        JSONObject request = new JSONObject("{\n" +
                "\"name\": \"Book Ignited Minds - Updated name!\",\n" +
                "\"description\": \"New descirption too. :).\",\n" +
                "\"amount\": 20000,\n  \"currency\": \"INR\",\n" +
                "\"active\": true\n}");

        String mockedResponseJson = "{\n   " +
                "\"entity\" : \"item\",\n" +
                "\"id\": "+ITEM_ID+",\n" +
                "\"active\": true,\n" +
                "\"name\": \"Book Ignited Minds - Updated name!\",\n" +
                "\"description\": \"New descirption too. :)\",\n" +
                "\"amount\": 15000,\n" +
                "\"currency\": \"INR\"\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Item item = itemClient.edit(ITEM_ID,request);
            assertNotNull(item);
            assertEquals(ITEM_ID,item.get("id"));
            assertEquals("item",item.get("entity"));
            assertTrue(item.has("amount"));
            assertTrue(item.has("description"));
            String editRequest = getHost(String.format(Constants.ITEM, ITEM_ID));
            verifySentRequest(true, request.toString(), editRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Delete an Item
     * @throws RazorpayException
     */
    @Test
    public void testDeleteItem() throws IOException, RazorpayException {
        try {
            ArrayList<String> mockedResponseJson = new ArrayList<String>();
            mockResponseFromExternalClient(String.valueOf(mockedResponseJson));
            mockResponseHTTPCodeFromExternalClient(200);
            List<Item> item = itemClient.delete(ITEM_ID);
            assertNotNull(item);
            String editRequest = getHost(String.format(Constants.ITEM,ITEM_ID));
            verifySentRequest(false, null, editRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}