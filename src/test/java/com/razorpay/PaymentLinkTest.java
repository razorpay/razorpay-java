package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class PaymentLinkTest extends BaseTest{

    @InjectMocks
    protected PaymentLinkClient paymentLinkClient = new PaymentLinkClient(TEST_SECRET_KEY);

    private static final String PAYMENTLINK_ID = "plink_ETbyWrRHW2oXVt";

    /**
     * Create basic or customized Payment Links
     * @throws RazorpayException
     */
    @Test
    public void create() throws RazorpayException{

        JSONObject request = new JSONObject("{\"amount\":500,\"currency\":\"INR\",\"accept_partial\":true,\"first_min_partial_amount\":100,\"description\":\"ForXYZpurpose\",\"customer\":{\"name\":\"GauravKumar\",\"email\":\"gaurav.kumar@example.com\",\"contact\":\"+919999999999\"},\"notify\":{\"sms\":true,\"email\":true},\"reminder_enable\":true,\"notes\":{\"policy_name\":\"JeevanBima\"},\"callback_url\":\"https://example-callback-url.com/\",\"callback_method\":\"get\"}");
        String mockedResponseJson = "{\"entity\":\"payment_link\",\"accept_partial\":false,\"amount\":1000,\"amount_paid\":0,\"cancelled_at\":0,\"created_at\":1584524459,\"currency\":\"INR\",\"customer\":{\"contact\":\"9912650835\",\"email\":\"gaurav.kumar@razorpay.com\",\"name\":\"GauravKumar\"},\"description\":\"Paymentforpolicyno#23456\",\"expire_by\":0,\"expired_at\":0,\"first_min_partial_amount\":0,\"id\":"+PAYMENTLINK_ID+",\"upi_link\":\"true\",\"notes\":{\"policy_name\":\"JeevanBima\"},\"payments\":null,\"reference_id\":\"#456\",\"reminder_enable\":true,\"reminders\":[],\"short_url\":\"https://rzp.io/i/AiGGmnh\",\"status\":\"created\",\"updated_at\":null,\"user_id\":\"API\"}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            PaymentLink fetch = paymentLinkClient.create(request);
            assertNotNull(fetch);
            assertEquals("payment_link",fetch.get("entity"));
            assertEquals(PAYMENTLINK_ID,fetch.get("id"));
            assertEquals("INR",fetch.get("currency"));
            assertTrue(fetch.has("amount_paid"));
            assertTrue(fetch.has("upi_link"));
            String createRequest = getHost(Constants.PAYMENTLINK_CREATE);
            verifySentRequest(true, request.toString(), createRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Retrieve the payment-link details using payment-link id.
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws RazorpayException{

        String mockedResponseJson = "{\"entity\":\"payment_link\",\"accept_partial\":false,\"amount\":1000,\"amount_paid\":0,\"cancelled_at\":0,\"created_at\":1584524459,\"currency\":\"INR\",\"customer\":{\"contact\":\"9912650835\",\"email\":\"gaurav.kumar@razorpay.com\",\"name\":\"GauravKumar\"},\"description\":\"Paymentforpolicyno#23456\",\"expire_by\":0,\"expired_at\":0,\"first_min_partial_amount\":0,\"id\":"+PAYMENTLINK_ID+",\"upi_link\":\"true\",\"notes\":{\"policy_name\":\"JeevanBima\"},\"payments\":null,\"reference_id\":\"#456\",\"reminder_enable\":true,\"reminders\":[],\"short_url\":\"https://rzp.io/i/AiGGmnh\",\"status\":\"created\",\"updated_at\":null,\"user_id\":\"API\"}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            PaymentLink fetch = paymentLinkClient.fetch(PAYMENTLINK_ID);
            assertNotNull(fetch);
            assertEquals("payment_link",fetch.get("entity"));
            assertEquals(PAYMENTLINK_ID,fetch.get("id"));
            assertEquals("INR",fetch.get("currency"));
            assertTrue(fetch.has("amount_paid"));
            assertTrue(fetch.has("upi_link"));
            String fetchRequest = getHost(String.format(Constants.PAYMENTLINK_GET, PAYMENTLINK_ID));
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Cancel the payment-link using payment-link id.
     * @throws RazorpayException
     */
    @Test
    public void cancel() throws RazorpayException{

        String mockedResponseJson = "{\"entity\":\"payment_link\",\"accept_partial\":true,\"amount\":1000,\"amount_paid\":0,\"callback_method\":\"get\",\"callback_url\":\"https://example-callback-url.com/\",\"cancelled_at\":1591097270,\"created_at\":1591097057,\"currency\":\"INR\",\"customer\":{\"contact\":\"+919999999999\",\"email\":\"gaurav.kumar@example.com\",\"name\":\"GauravKumar\"},\"description\":\"Paymentforpolicyno#23456\",\"expire_by\":1691097057,\"expired_at\":0,\"first_min_partial_amount\":100,\"id\":"+PAYMENTLINK_ID+",\"notes\":{\"policy_name\":\"JeevanBima\"},\"notify\":{\"email\":true,\"sms\":true},\"payments\":[],\"reference_id\":\"TS1989\",\"reminder_enable\":true,\"reminders\":{\"status\":\"failed\"},\"short_url\":\"https://rzp.io/i/nxrHnLJ\",\"source\":\"\",\"source_id\":\"\",\"status\":\"cancelled\",\"updated_at\":1591097270,\"user_id\":\"\"}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            PaymentLink fetch = paymentLinkClient.cancel(PAYMENTLINK_ID);
            assertNotNull(fetch);
            assertEquals("payment_link",fetch.get("entity"));
            assertEquals(PAYMENTLINK_ID,fetch.get("id"));
            assertEquals("INR",fetch.get("currency"));
            assertTrue(fetch.has("callback_method"));
            assertTrue(fetch.has("cancelled_at"));
            String cancelRequest = getHost(String.format(Constants.PAYMENTLINK_CANCEL, PAYMENTLINK_ID));
            verifySentRequest(false, null, cancelRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Send or resend notifications to customer via email and SMS
     * using payment-link id
     * @throws RazorpayException
     */
    @Test
    public void notifyBy() throws RazorpayException{

        String mockedResponseJson = "{\"entity\":\"payment_link\",\"success\":true}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            PaymentLink fetch = paymentLinkClient.notifyBy(PAYMENTLINK_ID,"sms");
            assertNotNull(fetch);
            assertEquals("payment_link",fetch.get("entity"));
            assertTrue(fetch.get("success"));
            String notifyByRequest = getHost(String.format(Constants.PAYMENTLINK_NOTIFYBY, PAYMENTLINK_ID, "sms"));
            verifySentRequest(false, null, notifyByRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Update an payment-link of using invoice payment-link id with object of that properties
     * @throws RazorpayException
     */
    @Test
    public void edit() throws RazorpayException{
        JSONObject request = new JSONObject("{\"reference_id\":\"TS35\",\"expire_by\":1653347540,\"reminder_enable\":false,\"notes\":{\"policy_name\":\"JeevanSaral\"}}");
        String mockedResponseJson = "{\"entity\":\"payment_link\",\"accept_partial\":false,\"amount\":100,\"amount_paid\":100,\"cancelled_at\":0,\"created_at\":1602522293,\"currency\":\"INR\",\"customer\":{\"contact\":\"9999999999\",\"email\":\"gaurav.kumar@razorpay.com\"},\"description\":\"PaymentforAcmeInc\",\"expire_by\":1653347540,\"expired_at\":0,\"first_min_partial_amount\":0,\"id\":\"plink_Fo48rl281ENAg9\",\"notes\":{\"policy_name\":\"JeevanSaral\"},\"notify\":{\"email\":true,\"sms\":true},\"order_id\":\"order_Fo491cL6NGAjkI\",\"payments\":[{\"amount\":100,\"created_at\":1602522351,\"method\":\"upi\",\"payment_id\":\"pay_Fo49sHbQ78PCMI\",\"status\":\"captured\"}],\"reference_id\":\"TS35\",\"reminder_enable\":false,\"reminders\":[],\"short_url\":\"https://rzp.io/i/XQiMe4w\",\"status\":\"paid\",\"updated_at\":1602523645,\"upi_link\":true,\"user_id\":\"FmjfFPCOUOAcSH\"}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            PaymentLink fetch = paymentLinkClient.edit(PAYMENTLINK_ID,request);
            assertNotNull(fetch);
            assertEquals("plink_Fo48rl281ENAg9",fetch.get("id"));
            assertEquals("INR",fetch.get("currency"));
            assertTrue(fetch.has("accept_partial"));
            assertTrue(fetch.has("order_id"));
            assertTrue(fetch.has("payments"));
            String fetchRequest = getHost(String.format(Constants.PAYMENTLINK_EDIT, PAYMENTLINK_ID));
            verifySentRequest(true, request.toString(), fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }
    
     /**
     * Details of all the payment-links can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws RazorpayException{
        String mockedResponseJson = "{\"payment_links\":[{\"cancelled_at\":1644517434,\"reminders\":{\"status\":\"in_progress\"},\"amount_paid\":0,\"notes\":{\"policy_name\":\"JeevanBima\"},\"reference_id\":\"\",\"payments\":[],\"created_at\":1644517182,\"description\":\"ForXYZpurpose\",\"expired_at\":0,\"notify\":{\"sms\":true,\"email\":true},\"short_url\":\"https://rzp.io/i/Yyzzizh\",\"callback_url\":\"https://example-callback-url.com/\",\"updated_at\":1644517434,\"upi_link\":false,\"accept_partial\":true,\"currency\":\"INR\",\"id\":\"plink_IuP2QzddqlPG2A\",\"callback_method\":\"get\",\"expire_by\":0,\"first_min_partial_amount\":100,\"amount\":500,\"reminder_enable\":true,\"user_id\":\"\",\"entity\":\"payment_link\",\"customer\":{\"contact\":\"+919999999999\",\"name\":\"GauravKumar\",\"email\":\"gaurav.kumar@example.com\"},\"status\":\"cancelled\"},{\"cancelled_at\":0,\"reminders\":{\"status\":\"in_progress\"},\"amount_paid\":0,\"notes\":{\"policy_name\":\"JeevanBima\"},\"reference_id\":\"\",\"payments\":[],\"created_at\":1643024367,\"description\":\"ForXYZpurpose\",\"expired_at\":0,\"notify\":{\"sms\":true,\"email\":true},\"short_url\":\"https://rzp.io/i/zdFAncL\",\"callback_url\":\"https://example-callback-url.com/\",\"updated_at\":1643024367,\"upi_link\":false,\"accept_partial\":true,\"currency\":\"INR\",\"id\":\"plink_InZ8aqDn9IfpAj\",\"callback_method\":\"get\",\"expire_by\":0,\"first_min_partial_amount\":100,\"amount\":500,\"reminder_enable\":true,\"user_id\":\"\",\"entity\":\"payment_link\",\"customer\":{\"name\":\"GauravKumar\",\"email\":\"gaurav.kumar@example.com\"},\"status\":\"created\"},]}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<PaymentLink> fetch = paymentLinkClient.fetchAll();
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("cancelled_at"));
            assertTrue(fetch.get(0).has("reminders"));
            assertTrue(fetch.get(0).has("created_at"));
            assertTrue(fetch.get(0).has("currency"));
            String fetchRequest = getHost(Constants.PAYMENTLINK_LIST);
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}