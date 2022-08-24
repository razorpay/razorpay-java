package com.razorpay;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class OrderClientTest extends BaseTest{

    @Mock
    ApiUtils apiUtils;

    private static final String ORDER_ID = "order_EKwxwAgItmmXdp";

    /**
     * Create order with basic details such as currency and amount details
     * @throws RazorpayException
     */
    @Test
    public void create() throws RazorpayException, JSONException, URISyntaxException {
        JSONObject request = new JSONObject("{" +
                "amount:50000," +
                "currency:\"INR\"," +
                "receipt:\"receipt#1\"," +
                "notes:" +
                "{key1:\"value3\"," +
                "key2:\"value2\"}}");

        String mockedResponseJson = "{" +
                "\"id\":\"order_EKwxwAgItmmXdp\"," +
                "\"entity\":\"order\"," +
                "\"amount\":50000," +
                "\"amount_paid\":0," +
                "\"amount_due\":50000," +
                "\"currency\":\"INR\"," +
                "\"receipt\":\"receipt#1\"," +
                "\"offer_id\":null," +
                "\"status\":\"created\"," +
                "\"attempts\":0," +
                "\"notes\":[]," +
                "\"created_at\":1582628071}";
        try {
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(Constants.ORDER_CREATE, null);
            mockPostRequest(apiUtils,builder,request.toString(), mockedResponseJson);

            OrderClient orderClient = new OrderClient("test",apiUtils);

            Order fetch = orderClient.create(request);
            assertNotNull(fetch);
            assertEquals(ORDER_ID,fetch.get("id"));
            assertEquals("order",fetch.get("entity"));
            assertTrue(fetch.has("amount"));
            assertTrue(fetch.has("amount_paid"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Details of all the order can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws RazorpayException, JSONException, URISyntaxException {
        String mockedResponseJson = "{" +
                "\"entity\":\"collection\"," +
                "\"count\":1,\"items\":" +
                "[{\"id\":\"order_EKzX2WiEWbMxmx\"," +
                "\"entity\":\"order\"," +
                "\"amount\":1234," +
                "\"amount_paid\":0," +
                "\"amount_due\":1234," +
                "\"currency\":\"INR\"," +
                "\"receipt\":\"ReceiptNo.1\"," +
                "\"offer_id\":null," +
                "\"status\":\"created\"," +
                "\"attempts\":0," +
                "\"notes\":[]," +
                "\"created_at\":1582637108}]}";
        try {
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(Constants.ORDER_LIST, null);
            mockGetRequest(apiUtils,builder,null, mockedResponseJson);

            OrderClient orderClient = new OrderClient("test",apiUtils);
            List<Order> fetch = orderClient.fetchAll();
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("entity"));
            assertTrue(fetch.get(0).has("amount"));
            assertTrue(fetch.get(0).has("amount_paid"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Retrieve the order details using order id.
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws RazorpayException, JSONException, URISyntaxException {
        String mockedResponseJson = "{\"id\":"+ORDER_ID+"," +
                "\"entity\":\"order\"," +
                "\"amount\":2200," +
                "\"amount_paid\":0," +
                "\"amount_due\":2200," +
                "\"currency\":\"INR\"," +
                "\"receipt\":\"Receipt#211\"," +
                "\"status\":\"attempted\"," +
                "\"attempts\":1," +
                "\"notes\":[]," +
                "\"created_at\":1572505143}";
        try {
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(String.format(Constants.ORDER_GET, ORDER_ID), null);
            mockGetRequest(apiUtils,builder,null, mockedResponseJson);

            OrderClient orderClient = new OrderClient("test",apiUtils);
            Order fetch = orderClient.fetch(ORDER_ID);
            assertNotNull(fetch);
            assertEquals(true,fetch.has("id"));
            assertTrue(fetch.has("entity"));
            assertTrue(fetch.has("amount"));
            assertTrue(fetch.has("amount_paid"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Retrieve the payment details of respective customer using payment id.
     * @throws RazorpayException
     */
    @Test
    public void fetchPayments() throws RazorpayException, JSONException, URISyntaxException {

        String mockedResponseJson = "{" +
                "\"entity\":\"collection\"," +
                "\"count\":1," +
                "\"items\":[{\"id\":\"pay_DaaSOvhgcOfzgR\"," +
                "\"entity\":\"payment\"," +
                "\"amount\":2200," +
                "\"currency\":\"INR\"," +
                "\"status\":\"captured\"," +
                "\"order_id\":\"order_DaaS6LOUAASb7Y\"," +
                "\"invoice_id\":null," +
                "\"international\":false," +
                "\"method\":\"card\"," +
                "\"amount_refunded\":0," +
                "\"refund_status\":null," +
                "\"captured\":true," +
                "\"description\":\"Beansineveryimaginableflavour\"," +
                "\"card_id\":\"card_DZon6fd8J3IcA2\"," +
                "\"bank\":null," +
                "\"wallet\":null," +
                "\"vpa\":null," +
                "\"email\":\"gaurav.kumar@example.com\"," +
                "\"contact\":\"+919999999988\"," +
                "\"notes\":[]," +
                "\"fee\":44," +
                "\"tax\":0," +
                "\"error_code\":null," +
                "\"error_description\":null," +
                "\"created_at\":1572505160}]}";
        try {
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(String.format(Constants.ORDER_PAYMENT_LIST, ORDER_ID), null);
            mockGetRequest(apiUtils,builder,null, mockedResponseJson);

            OrderClient orderClient = new OrderClient("test",apiUtils);
            List<Payment> fetch = orderClient.fetchPayments(ORDER_ID);
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("id"));
            assertTrue(fetch.get(0).has("amount"));
            assertTrue(fetch.get(0).has("currency"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Update an order of respective customer using order id with object of that properties
     * @throws RazorpayException
     */
    @Test
    public void edit() throws RazorpayException, JSONException, URISyntaxException {

        JSONObject request = new JSONObject("{" +
                "\"notes\":{" +
                "\"key1\":\"value3\"," +
                "\"key2\":\"value2\"" +
                "}}");

        String mockedResponseJson = "{" +
                "\"id\":"+ORDER_ID+"," +
                "\"entity\":\"order\"," +
                "\"amount\":2200," +
                "\"amount_paid\":0," +
                "\"amount_due\":2200," +
                "\"currency\":\"INR\"," +
                "\"receipt\":\"Receipt#211\"," +
                "\"offer_id\":null," +
                "\"status\":\"attempted\"," +
                "\"attempts\":1," +
                "\"notes\":{" +
                "\"notes_key_1\":\"Tea,EarlGrey,Hot\"," +
                "\"notes_key_2\":\"Tea,EarlGrey…decaf.\"" +
                "}," +
                "\"created_at\":1572505143" +
                "}";
        try {
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(String.format(Constants.ORDER_EDIT, ORDER_ID), null);
            mockPatchRequest(apiUtils,builder,request.toString(), mockedResponseJson);

            OrderClient orderClient = new OrderClient("test",apiUtils);
            Order fetch = orderClient.edit(ORDER_ID,request);
            assertNotNull(fetch);
            assertEquals(ORDER_ID,fetch.get("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}