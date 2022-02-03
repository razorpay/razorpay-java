package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ItemClientTest extends BaseTest{

    @InjectMocks
    private ItemClient itemClient = new ItemClient("test");

    public static final String ITEM_ID = "item_IJol6jPh1ummTK";

    @Test
    public void create() throws RazorpayException {
        JSONObject request = new JSONObject("{\n  \"name\": \"Book English August\",\n  \"description\": \"An indian story, Booker prize winner.\",\n  \"amount\": 20000,\n  \"currency\": \"INR\"\n}");
        String json = "{\n  \"entity\" : \"item\",\n  \"id\": \"item_IJol6jPh1ummTK\",\n  \"active\": true,\n  \"name\": \"Book English August\",\n  \"description\": \"An indian story, Booker prize winner.\",\n  \"amount\": 20000,\n  \"currency\": \"INR\"\n}";
        try {
            mockResponseFromExternalClient(json);
            mockResponseHTTPCodeFromExternalClient(200);
            Item item = itemClient.create(request);
            assertNotNull(item);
            assertEquals(ITEM_ID,item.get("id"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fetch() throws RazorpayException {

        String json = "{\n  \"entity\": \"item\",\n  \"id\": \"item_7Oxp4hmm6T4SCn\",\n  \"active\": true,\n  \"name\": \"Book English August\",\n  \"description\": \"An indian story, Booker prize winner.\",\n  \"amount\": 20000,\n  \"currency\": \"INR\"\n}";
        try {
            mockResponseFromExternalClient(json);
            mockResponseHTTPCodeFromExternalClient(200);
            Item fetch = itemClient.fetch(ITEM_ID);
            assertNotNull(fetch);
            assertEquals("item",fetch.get("entity"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fetchAll() throws RazorpayException{

        String json = "{\n  \"entity\": \"collection\",\n  \"count\": 1,\n  \"items\": [\n    {\n      \"id\": \"item_7OxoGnoxCuUKbo\",\n       \"entity\" : \"item\",\n      \"active\": true,\n      \"name\": \"Book  English August\",\n      \"description\": null,\n      \"amount\": 20000,\n      \"currency\": \"INR\"\n    }\n  ]\n}";
        try {
            mockResponseFromExternalClient(json);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Item> fetch = itemClient.fetchAll();
            assertNotNull(fetch);
            assertEquals(true,fetch.get(0).has("entity"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void edit() throws RazorpayException{

        JSONObject request = new JSONObject("{\n  \"name\": \"Book Ignited Minds - Updated name!\",\n  \"description\": \"New descirption too. :).\",\n  \"amount\": 20000,\n  \"currency\": \"INR\",\n  \"active\": true\n}");
        String json = "{\n   \"entity\" : \"item\",\n  \"id\": \"item_IJol6jPh1ummTK\",\n  \"active\": true,\n  \"name\": \"Book Ignited Minds - Updated name!\",\n  \"description\": \"New descirption too. :)\",\n  \"amount\": 15000,\n  \"currency\": \"INR\"\n}";
        try {
            mockResponseFromExternalClient(json);
            mockResponseHTTPCodeFromExternalClient(200);
            Item item = itemClient.edit(ITEM_ID,request);
            assertNotNull(item);
            assertEquals(ITEM_ID,item.get("id"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}