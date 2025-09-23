package com.razorpay;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class OrderClientTest extends BaseTest{

    @InjectMocks
    protected OrderClient orderClient = new OrderClient(TEST_SECRET_KEY);

    private static final String ORDER_ID = "order_EKwxwAgItmmXdp";

    /**
     * Create order with basic details such as currency and amount details
     * @throws RazorpayException
     */
    @Test
    public void create() throws RazorpayException {
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
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Order fetch = orderClient.create(request);
            assertNotNull(fetch);
            assertEquals(ORDER_ID,fetch.get("id"));
            assertEquals("order",fetch.get("entity"));
            assertTrue(fetch.has("amount"));
            assertTrue(fetch.has("amount_paid"));
            String createRequest = getHost(Constants.ORDER_CREATE);
            verifySentRequest(true, request.toString(), createRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Details of all the order can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws RazorpayException{
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
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Order> fetch = orderClient.fetchAll();
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("entity"));
            assertTrue(fetch.get(0).has("amount"));
            assertTrue(fetch.get(0).has("amount_paid"));
            String fetchRequest = getHost(Constants.ORDER_LIST);
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Retrieve the order details using order id.
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws RazorpayException{
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
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Order fetch = orderClient.fetch(ORDER_ID);
            assertNotNull(fetch);
            assertEquals(true,fetch.has("id"));
            assertTrue(fetch.has("entity"));
            assertTrue(fetch.has("amount"));
            assertTrue(fetch.has("amount_paid"));
            String fetchRequest = getHost(String.format(Constants.ORDER_GET,ORDER_ID));
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Retrieve the payment details of respective customer using payment id.
     * @throws RazorpayException
     */
    @Test
    public void fetchPayments() throws RazorpayException{
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
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Payment> fetch = orderClient.fetchPayments(ORDER_ID);
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("id"));
            assertTrue(fetch.get(0).has("amount"));
            assertTrue(fetch.get(0).has("currency"));
            String fetchRequest = getHost(String.format(Constants.ORDER_PAYMENT_LIST, ORDER_ID));
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Update an order of respective customer using order id with object of that properties
     * @throws RazorpayException
     */
    @Test
    public void edit() throws RazorpayException {
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
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Order fetch = orderClient.edit(ORDER_ID,request);
            assertNotNull(fetch);
            assertEquals(ORDER_ID,fetch.get("id"));
            assertEquals(ORDER_ID,fetch.get("id"));
            String editRequest = getHost(String.format(Constants.ORDER_EDIT, ORDER_ID));
            verifySentRequest(true, request.toString(), editRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void viewRtoReview() throws RazorpayException {
        JSONObject request = new JSONObject();
        request.put("payment_method","upi");

        JSONObject mockedResponseJson = new JSONObject();
        mockedResponseJson.put("entity","order");
        mockedResponseJson.put("risk_tier","high");

        JSONArray rtoArray = new JSONArray();
        JSONObject reason1 = new JSONObject();
        reason1.put("reason", "short_shipping_address");
        reason1.put("description", "Short shipping address");
        reason1.put("bucket", "address");

        JSONObject reason2 = new JSONObject();
        reason1.put("reason", "address_pincode_state_mismatch");
        reason1.put("description", "Incorrect pincode state entered");
        reason1.put("bucket", "address");

        rtoArray.put(reason1);
        rtoArray.put(reason2);

        mockedResponseJson.put("rto_reasons", rtoArray);

        try {
            mockResponseFromExternalClient(mockedResponseJson.toString());
            mockResponseHTTPCodeFromExternalClient(200);
            Order fetch = orderClient.viewRtoReview(ORDER_ID);
            assertNotNull(fetch);
            assertEquals(true,fetch.has("risk_tier"));
            String createRequest = getHost(String.format(Constants.VIEW_RTO, ORDER_ID));
            verifySentRequest(false, null, createRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }
    @Test
    public void editFulfillment() throws RazorpayException {
        JSONObject request = new JSONObject();
        JSONObject shipping = new JSONObject();
        shipping.put("waybill", "123456789");
        shipping.put("status", "rto");
        shipping.put("provider", "Bluedart");

        request.put("payment_method","upi");
        request.put("shipping","shipping");

        JSONObject mockedResponseJson = new JSONObject();
        mockedResponseJson.put("entity","order");
        mockedResponseJson.put("order_id","order_EKwxwAgItmmXdp");
        mockedResponseJson.put("payment_method","upi");
        
        JSONObject shippingObj = new JSONObject();
        shippingObj.put("waybill", "123456789");
        shippingObj.put("status", "rto");
        shippingObj.put("provider", "Bluedart");

        mockedResponseJson.put("shipping",shippingObj);

        try {
            mockResponseFromExternalClient(mockedResponseJson.toString());
            mockResponseHTTPCodeFromExternalClient(200);
            Order fetch = orderClient.editFulfillment(ORDER_ID, request);
            assertNotNull(fetch);
            assertEquals(ORDER_ID,fetch.get("order_id"));
            assertEquals("order",fetch.get("entity"));
            String createRequest = getHost(String.format(Constants.FULFILLMENT, ORDER_ID));
            verifySentRequest(true, request.toString(), createRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}