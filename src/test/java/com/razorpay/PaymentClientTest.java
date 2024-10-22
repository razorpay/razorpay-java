package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class PaymentClientTest extends BaseTest{

    @InjectMocks
    protected PaymentClient paymentClient = new PaymentClient(TEST_SECRET_KEY);

    private static final String PAYMENT_ID = "pay_IDRP0tbirMSsbn";

    private static final String REFUND_ID = "rfnd_FP8QHiV938haTz";

    private static final String ORDER_ID = "order_J2bVDDGFwgGJbW";

    private static final String SIGNATURE = "aa64f4fafe6b0c3febce374c1176bbf91bf8077a25501efc0558d60b4a68bead";
    /**
     * Retrieve payment details of respective customer using payment id.
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws RazorpayException{

      String mockedResponseJson = "{" +
              "\"id\":"+PAYMENT_ID+"," +
              "\"entity\":\"payment\"," +
              "\"amount\":1000," +
              "\"currency\":\"INR\"," +
              "\"status\":\"captured\"," +
              "\"order_id\":\"order_G8VPOayFxWEU28\"," +
              "\"invoice_id\":null," +
              "\"international\":false," +
              "\"method\":\"upi\"," +
              "\"amount_refunded\":0," +
              "\"refund_status\":null," +
              "\"captured\":true," +
              "\"description\":\"PurchaseShoes\"," +
              "\"card_id\":null," +
              "\"bank\":null," +
              "\"wallet\":null," +
              "\"vpa\":\"gaurav.kumar@exampleupi\"," +
              "\"email\":\"gaurav.kumar@example.com\"," +
              "\"contact\":\"+919999999999\"," +
              "\"customer_id\":\"cust_DitrYCFtCIokBO\"," +
              "\"notes\":[]," +
              "\"fee\":24," +
              "\"tax\":4," +
              "\"error_code\":null," +
              "\"error_description\":null," +
              "\"error_source\":null," +
              "\"error_step\":null," +
              "\"error_reason\":null," +
              "\"acquirer_data\":{\"rrn\":\"033814379298\"}," +
              "\"created_at\":1606985209}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Payment fetch = paymentClient.fetch(PAYMENT_ID);
            assertNotNull(fetch);
            assertEquals(PAYMENT_ID,fetch.get("id"));
            assertTrue(fetch.has("status"));
            assertTrue(fetch.has("currency"));
            String fetchRequest = getHost(String.format(Constants.PAYMENT_GET, PAYMENT_ID));
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Details of all the payments can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws RazorpayException{

        String mockedResponseJson = "{" +
                "\"entity\":\"collection\"," +
                "\"count\":2," +
                "\"items\":[{\"id\":\"pay_G8VaL2Z68LRtDs\"," +
                "\"entity\":\"payment\"," +
                "\"amount\":900," +
                "\"currency\":\"INR\"," +
                "\"status\":\"captured\"," +
                "\"order_id\":\"order_G8VXfKDWDEOHHd\"," +
                "\"invoice_id\":null," +
                "\"international\":false," +
                "\"method\":\"netbanking\"," +
                "\"amount_refunded\":0," +
                "\"refund_status\":null," +
                "\"captured\":true," +
                "\"description\":\"PurchaseShoes\"," +
                "\"card_id\":null," +
                "\"bank\":\"KKBK\"," +
                "\"wallet\":null," +
                "\"vpa\":null," +
                "\"email\":\"gaurav.kumar@example.com\"," +
                "\"contact\":\"+919999999999\"," +
                "\"customer_id\":\"cust_DitrYCFtCIokBO\"," +
                "\"notes\":[]," +
                "\"fee\":22," +
                "\"tax\":4," +
                "\"error_code\":null," +
                "\"error_description\":null," +
                "\"error_source\":null," +
                "\"error_step\":null," +
                "\"error_reason\":null," +
                "\"acquirer_data\":{\"bank_transaction_id\":\"0125836177\"}," +
                "\"created_at\":1606985740}]}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Payment> fetch = paymentClient.fetchAll();
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("id"));
            assertTrue(fetch.get(0).has("entity"));
            assertTrue(fetch.get(0).has("amount"));
            String fetchRequest = getHost(Constants.PAYMENT_LIST);
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Capture a payment that verifies the amount deducted from the customer
     * is same as the amount paid by the customer on website
     * @throws RazorpayException
     */
    @Test
    public void capture() throws RazorpayException{

        JSONObject request = new JSONObject("{" +
                "\"amount\":1000," +
                "\"currency\":\"INR\"}");

        String mockedResponseJson = "{" +
                "\"id\":"+PAYMENT_ID+"," +
                "\"entity\":\"payment\"," +
                "\"amount\":1000," +
                "\"currency\":\"INR\"," +
                "\"status\":\"captured\"," +
                "\"order_id\":\"order_G8VPOayFxWEU28\"," +
                "\"invoice_id\":null," +
                "\"international\":false," +
                "\"method\":\"upi\"," +
                "\"amount_refunded\":0," +
                "\"refund_status\":null," +
                "\"captured\":true," +
                "\"description\":\"PurchaseShoes\"," +
                "\"card_id\":null," +
                "\"bank\":null," +
                "\"wallet\":null," +
                "\"vpa\":\"gaurav.kumar@exampleupi\"," +
                "\"email\":\"gaurav.kumar@example.com\"," +
                "\"contact\":\"+919999999999\"," +
                "\"customer_id\":\"cust_DitrYCFtCIokBO\"," +
                "\"notes\":[]," +
                "\"fee\":24," +
                "\"tax\":4," +
                "\"error_code\":null," +
                "\"error_description\":null," +
                "\"error_source\":null," +
                "\"error_step\":null," +
                "\"error_reason\":null," +
                "\"acquirer_data\":{\"rrn\":\"033814379298\"}," +
                "\"created_at\":1606985209}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Payment fetch = paymentClient.capture(PAYMENT_ID,request);
            assertNotNull(fetch);
            assertEquals(PAYMENT_ID,fetch.get("id"));
            assertTrue(fetch.has("entity"));
            assertTrue(fetch.has("amount"));
            String captureRequest = getHost(String.format(Constants.PAYMENT_CAPTURE, PAYMENT_ID));
            verifySentRequest(true, request.toString(), captureRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Create a refunds to respective customers
     * @throws RazorpayException
     */
    @Test
    public void refund() throws Exception{
        JSONObject request = new JSONObject("{" +
                "\"amount\":\"100\"," +
                "\"speed\":\"normal\"," +
                "\"notes\":{\"notes_key_1\":\"BeammeupScotty.\"," +
                "\"notes_key_2\":\"Engage\"}," +
                "\"receipt\":\"ReceiptNo.31\"}");

        String mockedResponseJson = "{" +
                "\"id\":"+REFUND_ID+"," +
                "\"entity\":\"refund\"," +
                "\"amount\":500100," +
                "\"receipt\":\"ReceiptNo.31\"," +
                "\"currency\":\"INR\"," +
                "\"payment_id\":\"pay_FCXKPFtYfPXJPy\"," +
                "\"notes\":[],\"acquirer_data\":{\"arn\":null}," +
                "\"created_at\":1597078866,\"batch_id\":null," +
                "\"status\":\"processed\"," +
                "\"speed_processed\":\"normal\"}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Refund fetch = paymentClient.refund(PAYMENT_ID,request);
            assertNotNull(fetch);
            assertEquals(REFUND_ID,fetch.get("id"));
            assertEquals("INR",fetch.get("currency"));
            assertTrue(fetch.has("payment_id"));
            String refundRequest = getHost(String.format(Constants.PAYMENT_REFUND, PAYMENT_ID));
            verifySentRequest(true, request.toString(), refundRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void refundWithOutQuery() throws Exception{
        String mockedResponseJson = "{" +
                "\"id\":"+REFUND_ID+"," +
                "\"entity\":\"refund\"," +
                "\"amount\":500100," +
                "\"receipt\":\"ReceiptNo.31\"," +
                "\"currency\":\"INR\"," +
                "\"payment_id\":\"pay_FCXKPFtYfPXJPy\"," +
                "\"notes\":[],\"acquirer_data\":{\"arn\":null}," +
                "\"created_at\":1597078866,\"batch_id\":null," +
                "\"status\":\"processed\"," +
                "\"speed_processed\":\"normal\"}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Refund fetch = paymentClient.refund(PAYMENT_ID);
            assertNotNull(fetch);
            assertEquals(REFUND_ID,fetch.get("id"));
            assertEquals("INR",fetch.get("currency"));
            assertTrue(fetch.has("payment_id"));
            String refundRequest = getHost(String.format(Constants.PAYMENT_REFUND, PAYMENT_ID));
            verifySentRequest(false, null, refundRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Retrieve all the refunds for a payment by default only last 10 refunds returned.
     * @throws RazorpayException
     */
    @Test
    public void FetchAllRefunds() throws RazorpayException{
        JSONObject request = new JSONObject("{}");
        String mockedResponseJson = "{" +
                "\"entity\":\"collection\"," +
                "\"count\":1,\"items\":[{\"id\":\"rfnd_IDQbLKwiy0aHrA\"," +
                "\"entity\":\"refund\"," +
                "\"amount\":100," +
                "\"currency\":\"INR\"," +
                "\"payment_id\":\"pay_I3eaMwGV0462JA\"," +
                "\"notes\":[]," +
                "\"receipt\":null," +
                "\"acquirer_data\":{\"arn\":\"10000000000000\"}," +
                "\"created_at\":1635134062," +
                "\"batch_id\":null," +
                "\"status\":\"processed\"," +
                "\"speed_processed\":\"normal\"," +
                "\"speed_requested\":\"normal\"}]}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Refund> fetch = paymentClient.fetchAllRefunds(PAYMENT_ID,request);
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("id"));
            assertTrue(fetch.get(0).has("amount"));
            assertTrue(fetch.get(0).has("payment_id"));
            assertTrue(fetch.get(0).has("notes"));
            String refundRequest = getHost(String.format(Constants.PAYMENT_REFUND_LIST, PAYMENT_ID));
            verifySentRequest(false, null, refundRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Create transfer from payments using payment id and with
     * object of that properties
     * @throws RazorpayException
     */
    @Test
    public void transfers() throws RazorpayException{
        JSONObject request = new JSONObject("{" +
                "\"transfers\":[{\"account\":\"acc_CPRsN1LkFccllA\"," +
                "\"amount\":100," +
                "\"currency\":\"INR\"," +
                "\"notes\":{\"name\":\"GauravKumar\"," +
                "\"roll_no\":\"IEC2011025\"}," +
                "\"linked_account_notes\":[\"roll_no\"]," +
                "\"on_hold\":true," +
                "\"on_hold_until\":1671222870}]}");

        String mockedResponseJson = "{" +
                "\"entity\":\"collection\"," +
                "\"count\":1," +
                "\"items\":[{\"id\":\"trf_ItzBst0oybrcNx\"," +
                "\"entity\":\"transfer\"," +
                "\"status\":\"pending\"," +
                "\"source\":\"pay_IOyKpYsPTMSWph\"," +
                "\"recipient\":\"acc_I0QRP7PpvaHhpB\"," +
                "\"amount\":100," +
                "\"currency\":\"INR\"," +
                "\"amount_reversed\":0," +
                "\"notes\":{\"name\":\"GauravKumar\"," +
                "\"roll_no\":\"IEC2011025\"}," +
                "\"linked_account_notes\":[\"roll_no\"]," +
                "\"on_hold\":true," +
                "\"on_hold_until\":1671222870," +
                "\"recipient_settlement_id\":null," +
                "\"created_at\":1644426157," +
                "\"processed_at\":null," +
                "\"error\":{\"code\":null," +
                "\"description\":null," +
                "\"reason\":null," +
                "\"field\":null," +
                "\"step\":null," +
                "\"id\":\"trf_ItzBst0oybrcNx\"," +
                "\"source\":null,\"metadata\":null}}]}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List <Transfer> fetch = paymentClient.transfer(PAYMENT_ID,request);
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("status"));
            assertTrue(fetch.get(0).has("source"));
            assertTrue(fetch.get(0).has("recipient"));
            assertTrue(fetch.get(0).has("currency"));
            String transferRequest = getHost(String.format(Constants.PAYMENT_TRANSFER_CREATE, PAYMENT_ID));
            verifySentRequest(true, request.toString(), transferRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Details of all the transfers payment can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAllTransfers() throws RazorpayException{

        String mockedResponseJson = "{\n  " +
                "\"entity\": \"collection\",\n" +
                "\"count\": 1,\n" +
                "\"items\": [\n" +
                "{\n " +
                "\"id\": \"trf_EAznuJ9cDLnF7Y\",\n" +
                "\"entity\": \"transfer\",\n" +
                "\"source\": \"pay_E9up5WhIfMYnKW\",\n" +
                "\"recipient\": \"acc_CMaomTz4o0FOFz\",\n" +
                "\"amount\": 1000,\n" +
                "\"currency\": \"INR\",\n" +
                "\"amount_reversed\": 100,\n" +
                "\"notes\": [],\n" +
                "\"fees\": 3,\n" +
                "\"tax\": 0,\n" +
                "\"on_hold\": false,\n" +
                "\"on_hold_until\": null,\n" +
                "\"recipient_settlement_id\": null,\n" +
                "\"created_at\": 1580454666,\n" +
                "\"linked_account_notes\": [],\n" +
                "\"processed_at\": 1580454666\n" +
                "}\n  ]\n}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Transfer> fetch = paymentClient.fetchAllTransfers(PAYMENT_ID);
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("source"));
            assertTrue(fetch.get(0).has("recipient"));
            assertTrue(fetch.get(0).has("amount"));
            String transferRequest = getHost(String.format(Constants.PAYMENT_TRANSFER_GET, PAYMENT_ID));
            verifySentRequest(false, null, transferRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }


    /**
     * Retrieve transfers for a payment using payment id
     * @throws RazorpayException
     */
    @Test
    public void fetchBankTransfers() throws RazorpayException{

     String mockedResponseJson = "{\n" +
             "\"id\": \"bt_Di5iqCElVyRlCb\",\n" +
             "\"entity\": \"bank_transfer\",\n" +
             "\"payment_id\": "+PAYMENT_ID+",\n" +
             "\"mode\": \"NEFT\",\n" +
             "\"bank_reference\": \"157414364471\",\n" +
             "\"amount\": 239000,\n  \"payer_bank_account\": {\n" +
             "\"id\": \"ba_Di5iqSxtYrTzPU\",\n" +
             "\"entity\": \"bank_account\",\n" +
             "\"ifsc\": \"UTIB0003198\",\n" +
             "\"bank_name\": \"Axis Bank\",\n" +
             "\"name\": \"Acme Corp\",\n" +
             "\"notes\": [],\n" +
             "\"account_number\": \"765432123456789\"\n" +
             "},\n  \"virtual_account_id\": \"va_Di5gbNptcWV8fQ\",\n" +
             "\"virtual_account\": {\n" +
             "\"id\": \"va_Di5gbNptcWV8fQ\",\n" +
             "\"name\": \"Acme Corp\",\n" +
             "\"entity\": \"virtual_account\",\n" +
             "\"status\": \"closed\",\n" +
             "\"description\": \"Virtual Account created for MS ABC Exports\",\n" +
             "\"amount_expected\": 2300,\n" +
             "\"notes\": {\n" +
             "\"material\": \"teakwood\"\n" +
             "},\n" +
             "\"amount_paid\": 239000,\n" +
             "\"customer_id\": \"cust_DOMUFFiGdCaCUJ\",\n" +
             "\"receivers\": [\n" +
             " {\n " +
             "\"id\": \"ba_Di5gbQsGn0QSz3\",\n" +
             "\"entity\": \"bank_account\",\n" +
             "\"ifsc\": \"RATN0VAAPIS\",\n " +
             "\"bank_name\": \"RBL Bank\",\n" +
             "\"name\": \"Acme Corp\",\n" +
             "\"notes\": [],\n " +
             "\"account_number\": \"1112220061746877\"\n" +
             "}\n" +
             "],\n" +
             "\"close_by\": 1574427237,\n" +
             "\"closed_at\": 1574164078,\n" +
             "\"created_at\": 1574143517\n}\n}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            BankTransfer fetch = paymentClient.fetchBankTransfers(PAYMENT_ID);
            assertNotNull(fetch);
            assertEquals("bt_Di5iqCElVyRlCb",fetch.get("id"));
            assertEquals("bank_transfer",fetch.get("entity"));
            assertEquals(PAYMENT_ID,fetch.get("payment_id"));
            assertTrue(fetch.has("entity"));
            assertTrue(fetch.has("amount"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }
    
    /**
     * Create a payment of customer after order is created (Server to server integration)
     * @throws RazorpayException
     */ 
    @Test
    public void createJsonPayment() throws RazorpayException {

        JSONObject request = new JSONObject("{" +
                "\"amount\":\"100\"," +
                "\"currency\":\"INR\"," +
                "\"email\":\"gaurav.kumar@example.com\"," +
                "\"contact\":\"9123456789\"," +
                "\"order_id\":\"order_ItZMEZjpBD6dhT\"," +
                "\"method\":\"upi\"}");

        String mockedResponseJson = "{" +
                "\"entity\":\"payment\"," +
                "\"type\":\"respawn\"," +
                "\"request\":" +
                "{\"url\":\"https://api.razorpay.com/v1/payments?key_id=rzp_test_pNL6H0AmbBEyjD\"," +
                "\"method\":\"POST\"," +
                "\"content\":" +
                "{\"amount\":\"100\"," +
                "\"currency\":\"INR\"," +
                "\"email\":\"gaurav.kumar@example.com\"," +
                "\"contact\":\"9123456789\"," +
                "\"order_id\":\"order_ItYKzxCxnKAKlD\"," +
                "\"method\":\"upi\"," +
                "\"card\":" +
                "{\"number\":\"4854980604708430\"," +
                "\"cvv\":\"123\"," +
                "\"expiry_month\":\"12\"," +
                "\"expiry_year\":\"21\"," +
                "\"name\":\"GauravKumar\"}," +
                "\"_\":{\"library\":\"s2s\"}," +
                "\"upi\":{\"flow\":\"collect\"," +
                "\"type\":\"default\"}}}," +
                "\"image\":null,\"theme\":\"#3594E2\"," +
                "\"method\":\"upi\"," +
                "\"version\":\"1\"," +
                "\"missing\":[\"vpa\"],\"base\":\"api.razorpay.com\"}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Payment fetch = paymentClient.createJsonPayment(request);
            assertNotNull(fetch);
            assertEquals("upi",fetch.get("method"));
            assertEquals("payment",fetch.get("entity"));
            assertTrue(fetch.has("request"));
            assertTrue(fetch.has("base"));
            String createRequest = getHost(Constants.PAYMENT_JSON_CREATE);
            verifySentRequest(true, request.toString(), createRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void createRecurringPayment() throws RazorpayException {

        JSONObject request = new JSONObject("{\n" +
                "  \"email\": \"gaurav.kumar@example.com\",\n" +
                "  \"contact\": \"9876543567\",\n" +
                "  \"amount\": 10000,\n" +
                "  \"currency\": \"INR\",\n" +
                "  \"order_id\": \"order_J2bmbRQxkC3YTv\",\n" +
                "  \"customer_id\": \"cust_DzYEzfJLV03rkp\",\n" +
                "  \"token\": \"token_Ip8u7q7FA77Q30\",\n" +
                "  \"recurring\": \"1\",\n" +
                "  \"notes\": {\n" +
                "    \"note_key_1\": \"Tea. Earl grey. Hot.\",\n" +
                "    \"note_key_2\": \"Tea. Earl grey. Decaf.\"\n" +
                "  },\n" +
                "  \"description\": \"Creating recurring payment for Gaurav Kumar\"\n" +
                "}");

        String mockedResponseJson = "{\n" +
                "    \"entity\": \"payment\",\n" +
                "    \"razorpay_payment_id\": \"pay_IDRP0tbirMSsbn\",\n" +
                "    \"razorpay_order_id\": \"order_J2bVDDGFwgGJbW\",\n" +
                "    \"razorpay_signature\": \"aa64f4fafe6b0c3febce374c1176bbf91bf8077a25501efc0558d60b4a68bead\"\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Payment fetch = paymentClient.createRecurringPayment(request);
            assertNotNull(fetch);
            assertEquals(PAYMENT_ID,fetch.get("razorpay_payment_id"));
            assertEquals(ORDER_ID,fetch.get("razorpay_order_id"));
            assertEquals(SIGNATURE,fetch.get("razorpay_signature"));
            String createRequest = getHost(Constants.PAYMENT_RECURRING);
            verifySentRequest(true, request.toString(), createRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * OTP Generation
     * @throws RazorpayException
     */
    @Test
    public void otpGenerate() throws RazorpayException {

        JSONObject request = new JSONObject("{\n" +
             "\"otp\": \"\",\n" +
         "}");

        String mockedResponseJson = "{\n" +
                " \"entity\": \"payment\", " +
                "  \"next\": [\n" +
                "    {\n" +
                "      \"action\": \"otp_submit\",\n" +
                "      \"url\": \"https://api.razorpay.com/v1/payments/pay_JWaNvYmrx75sXo/otp_submit/4f59d1f402f17f4cd9f9561ec9916573d63e48e6?key_id=rzp_test_z98FBYYhU5lDjb\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"action\": \"otp_resend\",\n" +
                "      \"url\": \"https://api.razorpay.com/v1/payments/pay_JWaNvYmrx75sXo/otp_resend/json?key_id=rzp_test_z98FBYYhU5lDjb\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"metadata\": {\n" +
                "    \"last4\": \"8430\",\n" +
                "    \"issuer\": \"HDFC\",\n" +
                "    \"network\": \"VISA\",\n" +
                "    \"iin\": \"485498\"\n" +
                "  },\n" +
                "  \"razorpay_payment_id\": "+PAYMENT_ID+"\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Payment fetch = paymentClient.otpGenerate(PAYMENT_ID);
            assertNotNull(fetch);
            assertEquals(PAYMENT_ID,fetch.get("razorpay_payment_id"));
            assertTrue(fetch.has("next"));
            assertEquals("payment",fetch.get("entity"));
            String otpRequest = getHost(String.format(Constants.PAYMENT_OTP_GENERATE, PAYMENT_ID));
            verifySentRequest(false, null, otpRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Response on Submitting OTP
     * @throws RazorpayException
     */
    @Test
    public void otpSubmit() throws RazorpayException {

        String jsonRequest = "{\n" +
                "  \"otp\": \"123456\",\n" +
                "}";

        JSONObject request = new JSONObject(jsonRequest);

        String mockedResponseJson = "{\n" +
                " \"entity\": \"payment\", " +
                "  \"razorpay_signature\": \"6b6e4fc7e63d559bbb1088bd8d6e09a461b50c6d33b50319abca0c339449d448\",\n" +
                "  \"razorpay_order_id\": "+ORDER_ID+",\n" +
                "  \"razorpay_payment_id\": "+PAYMENT_ID+"\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Payment fetch = paymentClient.otpSubmit(PAYMENT_ID,request);
            assertNotNull(fetch);
            assertEquals(PAYMENT_ID,fetch.get("razorpay_payment_id"));
            assertEquals(ORDER_ID,fetch.get("razorpay_order_id"));
            assertTrue(fetch.has("razorpay_signature"));
            String otpRequest = getHost(String.format(Constants.PAYMENT_OTP_SUBMIT, PAYMENT_ID));
            verifySentRequest(true, request.toString(), otpRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * OTP Resend
     * @throws RazorpayException
     */
    @Test
    public void otpResend() throws RazorpayException {

        String mockedResponseJson = "{\n" +
                "  \"next\": [\"otp_submit\", \"otp_resend\"],\n" +
                "  \"razorpay_payment_id\": "+PAYMENT_ID+",\n" +
                "  \"entity\" : \"payment\"\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Payment fetch = paymentClient.otpResend(PAYMENT_ID);
            assertNotNull(fetch);
            assertEquals(PAYMENT_ID,fetch.get("razorpay_payment_id"));
            assertTrue(fetch.has("next"));
            assertEquals("payment",fetch.get("entity"));
            String otpRequest = getHost(String.format(Constants.PAYMENT_OTP_RESEND, PAYMENT_ID));
            verifySentRequest(false, null, otpRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void createUpi() throws RazorpayException {

        JSONObject request = new JSONObject("{\n" +
                "  \"amount\": 200,\n" +
                "  \"currency\": \"INR\",\n" +
                "  \"order_id\": \"order_GAWRjlWkVcRh0V\",\n" +
                "  \"email\": \"gaurav.kumar@example.com\",\n" +
                "  \"contact\": \"9123456789\",\n" +
                "  \"method\": \"upi\",\n" +
                "  \"customer_id\": \"cust_EIW4T2etiweBmG\",\n" +
                "  \"save\": 1,\n" +
                "  \"ip\": \"192.168.0.103\",\n" +
                "  \"referer\": \"http\",\n" +
                "  \"user_agent\": \"Mozilla/5.0\",\n" +
                "  \"description\": \"Test flow\",\n" +
                "  \"notes\": {\n" +
                "    \"note_key\": \"value1\"\n" +
                "  },\n" +
                "  \"upi\": {\n" +
                "    \"flow\": \"collect\",\n" +
                "    \"vpa\": \"gauravkumar@exampleupi\",\n" +
                "    \"expiry_time\": 5\n" +
                "  }\n" +
                "}");

        String mockedResponseJson = "{\n" +
                "  \"entity\": \"payment\",  \n" +
                "  \"razorpay_payment_id\": "+PAYMENT_ID+",\n"+
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Payment fetch = paymentClient.createUpi(request);
            assertNotNull(fetch);
            assertEquals(PAYMENT_ID,fetch.get("razorpay_payment_id"));
            String createUpiRequest = getHost(Constants.PAYMENT_CREATE_UPI);
            verifySentRequest(true, request.toString(), createUpiRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void validateUpi() throws RazorpayException {

        JSONObject request = new JSONObject("{\n" +
                "  \"vpa\": \"gauravkumar@exampleupi\"\n" +
                "}");

        String mockedResponseJson = "{\n" +
                 "  \"entity\": \"payment\",\n" +
                 "  \"vpa\": \"gauravkumar@exampleupi\",\n" +
                 "  \"success\": true,\n" +
                 "  \"customer_name\": \"Gaurav Kumar\"\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Payment fetch = paymentClient.validateUpi(request);
            assertNotNull(fetch);
            assertTrue(fetch.get("success"));
            String validateUpiRequest = getHost(Constants.VALIDATE_VPA);
            verifySentRequest(true, request.toString(), validateUpiRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Fetch a specific refund for a payment
     * @throws RazorpayException
     */
    @Test
    public void fetchRefundWithoutPaymentId() throws RazorpayException {

        String mockedResponseJson = "{\n" +
                "    \"acquirer_data\": {\n" +
                "        \"arn\": null\n" +
                "    },\n" +
                "    \"amount\": 3000,\n" +
                "    \"batch_id\": null,\n" +
                "    \"created_at\": 1648456234,\n" +
                "    \"currency\": \"INR\",\n" +
                "    \"entity\": \"refund\",\n" +
                "    \"id\": "+REFUND_ID+",\n" +
                "    \"notes\": [],\n" +
                "    \"payment_id\": "+PAYMENT_ID+",\n" +
                "    \"receipt\": null,\n" +
                "    \"speed_processed\": \"normal\",\n" +
                "    \"speed_requested\": \"normal\",\n" +
                "    \"status\": \"processed\"\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Refund fetch = paymentClient.fetchRefund(REFUND_ID);
            assertNotNull(fetch);
            assertEquals(PAYMENT_ID,fetch.get("payment_id"));
            String fetchRefundRequest = getHost(String.format(Constants.REFUND, REFUND_ID));
            verifySentRequest(false, null, fetchRefundRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void fetchRefund() throws RazorpayException {

        String mockedResponseJson = "{\n" +
                "    \"acquirer_data\": {\n" +
                "        \"arn\": null\n" +
                "    },\n" +
                "    \"amount\": 3000,\n" +
                "    \"batch_id\": null,\n" +
                "    \"created_at\": 1648456234,\n" +
                "    \"currency\": \"INR\",\n" +
                "    \"entity\": \"refund\",\n" +
                "    \"id\": "+REFUND_ID+",\n" +
                "    \"notes\": [],\n" +
                "    \"payment_id\": "+PAYMENT_ID+",\n" +
                "    \"receipt\": null,\n" +
                "    \"speed_processed\": \"normal\",\n" +
                "    \"speed_requested\": \"normal\",\n" +
                "    \"status\": \"processed\"\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Refund fetch = paymentClient.fetchRefund(PAYMENT_ID,REFUND_ID);
            assertNotNull(fetch);
            assertEquals(PAYMENT_ID,fetch.get("payment_id"));
            String fetchRefundRequest = getHost(String.format(Constants.PAYMENT_REFUND_GET, PAYMENT_ID, REFUND_ID));
            verifySentRequest(false, null, fetchRefundRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Fetch multiple refunds for a payment
     * @throws RazorpayException
     */
    @Test
    public void fetchAllRefundWithoutQuery() throws RazorpayException {

        String mockedResponseJson = "{\n" +
                "  \"entity\": \"collection\",\n" +
                "  \"count\": 1,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": "+REFUND_ID+",\n" +
                "      \"entity\": \"refund\",\n" +
                "      \"amount\": 300100,\n" +
                "      \"currency\": \"INR\",\n" +
                "      \"payment_id\": "+PAYMENT_ID+",\n" +
                "      \"notes\": {\n" +
                "        \"comment\": \"Comment for refund\"\n" +
                "      },\n" +
                "      \"receipt\": null,\n" +
                "      \"acquirer_data\": {\n" +
                "        \"arn\": \"10000000000000\"\n" +
                "      },\n" +
                "      \"created_at\": 1597078124,\n" +
                "      \"batch_id\": null,\n" +
                "      \"status\": \"processed\",\n" +
                "      \"speed_processed\": \"normal\",\n" +
                "      \"speed_requested\": \"optimum\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Refund> fetch = paymentClient.fetchAllRefunds(REFUND_ID);
            assertNotNull(fetch);
            assertEquals(PAYMENT_ID,fetch.get(0).get("payment_id"));
            String fetchRefundRequest = getHost(String.format(Constants.PAYMENT_REFUND_LIST, REFUND_ID));
            verifySentRequest(false, null, fetchRefundRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Fetch multiple refunds for a payment
     * @throws RazorpayException
     */
    @Test
    public void fetchAllRefundWithQuery() throws RazorpayException {
        JSONObject request = new JSONObject("{" + "\"count\":\"1\"," + "}");

        String mockedResponseJson = "{\n" +
                "  \"entity\": \"collection\",\n" +
                "  \"count\": 1,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": "+REFUND_ID+",\n" +
                "      \"entity\": \"refund\",\n" +
                "      \"amount\": 300100,\n" +
                "      \"currency\": \"INR\",\n" +
                "      \"payment_id\": "+PAYMENT_ID+",\n" +
                "      \"notes\": {\n" +
                "        \"comment\": \"Comment for refund\"\n" +
                "      },\n" +
                "      \"receipt\": null,\n" +
                "      \"acquirer_data\": {\n" +
                "        \"arn\": \"10000000000000\"\n" +
                "      },\n" +
                "      \"created_at\": 1597078124,\n" +
                "      \"batch_id\": null,\n" +
                "      \"status\": \"processed\",\n" +
                "      \"speed_processed\": \"normal\",\n" +
                "      \"speed_requested\": \"optimum\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Refund> fetch = paymentClient.fetchAllRefunds(request);
            assertNotNull(fetch);
            assertEquals(PAYMENT_ID,fetch.get(0).get("payment_id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Fetch multiple refunds for a payment
     * @throws RazorpayException
     */
    @Test
    public void fetchAllRefund() throws RazorpayException {
        JSONObject request = new JSONObject("{" + "\"count\":\"1\"," + "}");

        String mockedResponseJson = "{\n" +
                "  \"entity\": \"collection\",\n" +
                "  \"count\": 1,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": "+REFUND_ID+",\n" +
                "      \"entity\": \"refund\",\n" +
                "      \"amount\": 300100,\n" +
                "      \"currency\": \"INR\",\n" +
                "      \"payment_id\": "+PAYMENT_ID+",\n" +
                "      \"notes\": {\n" +
                "        \"comment\": \"Comment for refund\"\n" +
                "      },\n" +
                "      \"receipt\": null,\n" +
                "      \"acquirer_data\": {\n" +
                "        \"arn\": \"10000000000000\"\n" +
                "      },\n" +
                "      \"created_at\": 1597078124,\n" +
                "      \"batch_id\": null,\n" +
                "      \"status\": \"processed\",\n" +
                "      \"speed_processed\": \"normal\",\n" +
                "      \"speed_requested\": \"optimum\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Refund> fetch = paymentClient.fetchAllRefunds(REFUND_ID, request);
            assertNotNull(fetch);
            assertEquals(PAYMENT_ID,fetch.get(0).get("payment_id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void expandedDetails() throws RazorpayException {
        JSONObject request = new JSONObject();
        request.put("expand[]","payments");

        JSONObject mockedResponseJson = new JSONObject();
        mockedResponseJson.put("id","pay_IDRP0tbirMSsbn");
        mockedResponseJson.put("entity","payment");
        mockedResponseJson.put("amount",100);
        mockedResponseJson.put("currency","INR");
        mockedResponseJson.put("status","failed");
        mockedResponseJson.put("order_id","order_H9o58N6qmLYQKC");
        mockedResponseJson.put("invoice_id", JSONObject.NULL);
        mockedResponseJson.put("terminal_id","term_G5kJnYM9GhhLYT");
        mockedResponseJson.put("international",false);
        mockedResponseJson.put("method","card");
        mockedResponseJson.put("amount_refunded",JSONObject.NULL);
        mockedResponseJson.put("captured",false);
        mockedResponseJson.put("description",JSONObject.NULL);
        mockedResponseJson.put("card_id","card_H9oR0ocen1cmZq");
        JSONObject cardObj = new JSONObject();
        cardObj.put("id","");
        cardObj.put("entity","card");
        cardObj.put("name","Gaurav");
        cardObj.put("last4","1213");
        cardObj.put("network","RuPay");
        cardObj.put("type", "credit");
        cardObj.put("issuer","UTIB");
        cardObj.put("international", false);
        cardObj.put("emi", false);
        cardObj.put("sub_type","business");
        mockedResponseJson.put("card",cardObj);
        mockedResponseJson.put("bank",JSONObject.NULL);
        mockedResponseJson.put("wallet",JSONObject.NULL);
        mockedResponseJson.put("vpa",JSONObject.NULL);
        mockedResponseJson.put("email","gaurav.kumar@example.com");
        mockedResponseJson.put("contact","+919000090000");
        JSONObject notesObj = new JSONObject();
        notesObj.put("key1","");
        notesObj.put("key2","");
        mockedResponseJson.put("notes",notesObj);
        mockedResponseJson.put("fee",JSONObject.NULL);
        mockedResponseJson.put("tax",JSONObject.NULL);
        mockedResponseJson.put("error_code","BAD_REQUEST_ERROR");
        mockedResponseJson.put("error_description","Card issuer is invalid");
        mockedResponseJson.put("error_source","customer");
        mockedResponseJson.put("error_step","payment_authentication");
        mockedResponseJson.put("error_reason","incorrect_card_details");
        JSONObject acquirerDataObj = new JSONObject();
        acquirerDataObj.put("auth_code",JSONObject.NULL);
        acquirerDataObj.put("authentication_reference_number","100222021120200000000742753928");
        mockedResponseJson.put("acquirer_data",acquirerDataObj);
        mockedResponseJson.put("created_at",1620807547);

        try {
            mockResponseFromExternalClient(mockedResponseJson.toString());
            mockResponseHTTPCodeFromExternalClient(200);
            Payment fetch = paymentClient.expandedDetails(PAYMENT_ID, request);
            assertNotNull(fetch);
            assertEquals(PAYMENT_ID,fetch.get("id"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void fetchPaymentMethods() throws RazorpayException {
        String mockedResponseJson = "{\n" +
                "    \"entity\": \"methods\",\n" +
                "    \"card\": true,\n" +
                "    \"debit_card\": true,\n" +
                "    \"credit_card\": true,\n" +
                "    \"prepaid_card\": true,\n" +
                "    \"card_networks\": {},\n" +
                "    \"card_subtype\": {},\n" +
                "    \"amex\": false,\n" +
                "    \"netbanking\": {},\n" +
                "    \"wallet\": [],\n" +
                "    \"emi\": false,\n" +
                "    \"upi\": true,\n" +
                "    \"cardless_emi\": [],\n" +
                "    \"paylater\": [],\n" +
                "    \"google_pay_cards\": false,\n" +
                "    \"app\": {},\n" +
                "    \"gpay\": false,\n" +
                "    \"emi_types\": {},\n" +
                "    \"debit_emi_providers\": {},\n" +
                "    \"intl_bank_transfer\": [],\n" +
                "    \"fpx\": [],\n" +
                "    \"nach\": false,\n" +
                "    \"cod\": false,\n" +
                "    \"offline\": false,\n" +
                "    \"sodexo\": false,\n" +
                "    \"upi_config\": [],\n" +
                "    \"recurring\": {},\n" +
                "    \"upi_intent\": true\n" +
                "}";

        try {
            mockResponseFromExternalClient(mockedResponseJson.toString());
            mockResponseHTTPCodeFromExternalClient(200);
            Methods fetch = paymentClient.fetchPaymentMethods();
            assertNotNull(fetch);
            assertEquals("methods", fetch.get("entity"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}