package com.razorpay;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ItemClientTest extends BaseTest{

    @Mock
    ApiUtils apiUtils;

    public static final String ITEM_ID = "item_IJol6jPh1ummTK";

    /**
     * Create item with basic details such as name and amount details
     * @throws RazorpayException
     */
    @Test
    public void create() throws RazorpayException, JSONException, URISyntaxException {
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
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(Constants.ITEMS, null);
            mockPostRequest(apiUtils,builder,request.toString(), mockedResponseJson);

            ItemClient itemClient = new ItemClient("test",apiUtils);

            Item item = itemClient.create(request);
            assertNotNull(item);
            assertEquals(ITEM_ID,item.get("id"));
            assertEquals("item",item.get("entity"));
            assertTrue(item.has("active"));
            assertTrue(item.has("name"));
            assertTrue(item.has("name"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Retrieve the item details using item id.
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws RazorpayException, JSONException, URISyntaxException {

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
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(String.format(Constants.ITEM, ITEM_ID), null);
            mockGetRequest(apiUtils,builder,null, mockedResponseJson);

            ItemClient itemClient = new ItemClient("test",apiUtils);

            Item fetch = itemClient.fetch(ITEM_ID);
            assertNotNull(fetch);
            assertEquals(ITEM_ID,fetch.get("id"));
            assertEquals("item",fetch.get("entity"));
            assertTrue(fetch.has("amount"));
            assertTrue(fetch.has("currency"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Details of all the items can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws  RazorpayException, JSONException, URISyntaxException {

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
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(Constants.ITEMS, null);
            mockGetRequest(apiUtils,builder,null, mockedResponseJson);

            ItemClient itemClient = new ItemClient("test",apiUtils);
            List<Item> fetch = itemClient.fetchAll();
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("entity"));
            assertTrue(fetch.get(0).has("id"));
            assertTrue(fetch.get(0).has("active"));

        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Update an item details using item id with object of that properties
     * @throws RazorpayException
     */
    @Test
    public void edit() throws RazorpayException, JSONException, URISyntaxException {

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
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(String.format(Constants.ITEM, ITEM_ID), null);
            mockPatchRequest(apiUtils,builder,request.toString(), mockedResponseJson);

            ItemClient itemClient = new ItemClient("test",apiUtils);
            Item item = itemClient.edit(ITEM_ID,request);
            assertNotNull(item);
            assertEquals(ITEM_ID,item.get("id"));
            assertEquals("item",item.get("entity"));
            assertTrue(item.has("amount"));
            assertTrue(item.has("description"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Delete an Item
     * @throws RazorpayException
     */
    @Test
    public void testDeleteItem() throws RazorpayException, JSONException, URISyntaxException {
        try {
            ArrayList<String> mockedResponseJson = new ArrayList<String>();
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(String.format(Constants.ITEM, ITEM_ID), null);
            mockDeleteRequest(apiUtils,builder,null, mockedResponseJson.toString());

            ItemClient itemClient = new ItemClient("test",apiUtils);
            List<Item> item = itemClient.delete(ITEM_ID);
            assertNotNull(item);
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}