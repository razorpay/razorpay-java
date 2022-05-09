package com.razorpay;

import org.json.JSONObject;

import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class InvoiceClientTest extends BaseTest{

    @InjectMocks
    protected InvoiceClient invoiceClient = new InvoiceClient(TEST_SECRET_KEY);

    private static final String INVOICE_ID = "inv_DAweOiQ7amIUVd";


    /**
     * Invoice is created using the customer and item details.
     * Here, the customer and item are created while creating the invoice.
     * @throws RazorpayException
     */
    @Test
    public void create() throws RazorpayException {
        JSONObject request = new JSONObject("{\n  type: \"invoice\",\n  description: \"Invoice for the month of January 2020\",\n  partial_payment: true,\n  customer: {\n    name: \"Gaurav Kumar\",\n    contact: 9999999999,\n    email: \"gaurav.kumar@example.com\",\n    billing_address: {\n      line1: \"Ground & 1st Floor, SJR Cyber Laskar\",\n      line2: \"Hosur Road\",\n      zipcode: 560068,\n      city: \"Bengaluru\",\n      state: \"Karnataka\",\n      country: \"in\"\n    },\n    shipping_address: {\n      line1: \"Ground & 1st Floor, SJR Cyber Laskar\",\n      line2: \"Hosur Road\",\n      zipcode: 560068,\n      city: \"Bengaluru\",\n      state: \"Karnataka\",\n      country: \"in\"\n    }\n  },\n  line_items: [\n    {\n      name: \"Master Cloud Computing in 30 Days\",\n      description: \"Book by Ravena Ravenclaw\",\n      amount: 399,\n      currency: \"USD\",\n      quantity: 1\n    }\n  ],\n  sms_notify: 1,\n  email_notify: 1,\n  currency: \"USD\",\n  expire_by: 1589765167\n}");
        String mockedResponseJson = "{\n  \"issued_at\": 1481541533,\n  \"customer_details\":\n  {\n    \"customer_name\": \"Gaurav Kumar\",\n    \"customer_email\": \"gaurav.kumar@example.com\",\n    \"customer_contact\": \"9999999999\"\n  },\n  \"short_url\": \"http://bit.ly/link\", \n  \"receipt\": null, \n  \"entity\": \"invoice\", \n  \"currency\": \"INR\", \n  \"paid_at\": null, \n  \"view_less\": true, \n  \"id\": \"random_id\", \n  \"customer_id\": \"cust_E7q0trFqXgExmT\", \n  \"type\": null, \n  \"status\": \"issued\", \n  \"description\": \"random decsription\", \n  \"order_id\": \"order_random_id\", \n  \"sms_status\": \"pending\", \n  \"date\": 1481541533, \n  \"payment_id\": null, \n  \"amount\": 100,\n  \"email_status\": \"pending\",\n  \"created_at\": 1481541534\n}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Invoice invoice = invoiceClient.create(request);
            assertNotNull(invoice);
            assertEquals("invoice",invoice.get("entity"));
            assertTrue(invoice.has("customer_details"));
            assertTrue(invoice.has("short_url"));
            String createRequest = getHost(Constants.INVOICE_CREATE);
            verifySentRequest(true, request.toString(), createRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }


    /**
     * Details of all the invoices can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws RazorpayException {

        String mockedResponseJson = "{\n  \"entity\": \"collection\",\n  \"count\": 1,\n  \"items\": [\n    {\n      \"id\": \"inv_DAweOiQ7amIUVd\",\n      \"entity\": \"invoice\",\n      \"receipt\": \"#0961\",\n      \"invoice_number\": \"#0961\",\n      \"customer_id\": \"cust_DAtUWmvpktokrT\",\n      \"customer_details\": {\n        \"id\": \"cust_DAtUWmvpktokrT\",\n        \"name\": \"Gaurav Kumar\",\n        \"email\": \"gaurav.kumar@example.com\",\n        \"contact\": \"9977886633\",\n        \"gstin\": null,\n        \"billing_address\": {\n          \"id\": \"addr_DAtUWoxgu91obl\",\n          \"type\": \"billing_address\",\n          \"primary\": true,\n          \"line1\": \"318 C-Wing, Suyog Co. Housing Society Ltd.\",\n          \"line2\": \"T.P.S Road, Vazira, Borivali\",\n          \"zipcode\": \"400092\",\n          \"city\": \"Mumbai\",\n          \"state\": \"Maharashtra\",\n          \"country\": \"in\"\n        },\n        \"shipping_address\": null,\n        \"customer_name\": \"Gaurav Kumar\",\n        \"customer_email\": \"gaurav.kumar@example.com\",\n        \"customer_contact\": \"9977886633\"\n      },\n      \"order_id\": null,\n      \"line_items\": [\n        {\n          \"id\": \"li_DAweOizsysoJU6\",\n          \"item_id\": null,\n          \"name\": \"Book English August - Updated name and quantity\",\n          \"description\": \"150 points in Quidditch\",\n          \"amount\": 400,\n          \"unit_amount\": 400,\n          \"gross_amount\": 400,\n          \"tax_amount\": 0,\n          \"taxable_amount\": 400,\n          \"net_amount\": 400,\n          \"currency\": \"INR\",\n          \"type\": \"invoice\",\n          \"tax_inclusive\": false,\n          \"hsn_code\": null,\n          \"sac_code\": null,\n          \"tax_rate\": null,\n          \"unit\": null,\n          \"quantity\": 1,\n          \"taxes\": []\n        },\n        {\n          \"id\": \"li_DAwjWQUo07lnjF\",\n          \"item_id\": null,\n          \"name\": \"Book A Wild Sheep Chase\",\n          \"description\": null,\n          \"amount\": 200,\n          \"unit_amount\": 200,\n          \"gross_amount\": 200,\n          \"tax_amount\": 0,\n          \"taxable_amount\": 200,\n          \"net_amount\": 200,\n          \"currency\": \"INR\",\n          \"type\": \"invoice\",\n          \"tax_inclusive\": false,\n          \"hsn_code\": null,\n          \"sac_code\": null,\n          \"tax_rate\": null,\n          \"unit\": null,\n          \"quantity\": 1,\n          \"taxes\": []\n        }\n      ],\n      \"payment_id\": null,\n      \"status\": \"draft\",\n      \"expire_by\": 1567103399,\n      \"issued_at\": null,\n      \"paid_at\": null,\n      \"cancelled_at\": null,\n      \"expired_at\": null,\n      \"sms_status\": null,\n      \"email_status\": null,\n      \"date\": 1566891149,\n      \"terms\": null,\n      \"partial_payment\": false,\n      \"gross_amount\": 600,\n      \"tax_amount\": 0,\n      \"taxable_amount\": 600,\n      \"amount\": 600,\n      \"amount_paid\": null,\n      \"amount_due\": null,\n      \"currency\": \"INR\",\n      \"currency_symbol\": \"\u20b9\",\n      \"description\": \"This is a test invoice.\",\n      \"notes\": {\n        \"updated-key\": \"An updated note.\"\n      },\n      \"comment\": null,\n      \"short_url\": null,\n      \"view_less\": true,\n      \"billing_start\": null,\n      \"billing_end\": null,\n      \"type\": \"invoice\",\n      \"group_taxes_discounts\": false,\n      \"created_at\": 1566906474,\n      \"idempotency_key\": null\n    }\n  ]\n}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<Invoice> fetch = invoiceClient.fetchAll();
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("type"));
            assertTrue(fetch.get(0).has("receipt"));
            String fetchRequest = getHost(Constants.INVOICE_LIST);
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }


    /**
     * Retrieve all the invoice details of respective customer using invoice id.
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws RazorpayException {

        String mockedResponseJson = "{\n  \"issued_at\": 1481541533,\n  \"customer_details\":\n  {\n    \"customer_name\": \"Gaurav Kumar\",\n    \"customer_email\": \"gaurav.kumar@example.com\",\n    \"customer_contact\": \"9999999999\"\n  },\n  \"short_url\": \"http://bit.ly/link\", \n  \"receipt\": null, \n  \"entity\": \"invoice\", \n  \"currency\": \"INR\", \n  \"paid_at\": null, \n  \"view_less\": true, \n  \"id\": "+INVOICE_ID+", \n  \"customer_id\": \"cust_E7q0trFqXgExmT\", \n  \"type\": null, \n  \"status\": \"issued\", \n  \"description\": \"random decsription\", \n  \"order_id\": \"order_random_id\", \n  \"sms_status\": \"pending\", \n  \"date\": 1481541533, \n  \"payment_id\": null, \n  \"amount\": 100,\n  \"email_status\": \"pending\",\n  \"created_at\": 1481541534\n}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Invoice fetch = invoiceClient.fetch(INVOICE_ID);
            assertNotNull(fetch);
            assertEquals(INVOICE_ID,fetch.get("id"));
            assertTrue(fetch.has("customer_details"));
            assertTrue(fetch.has("issued_at"));
            String fetchRequest = getHost(String.format(Constants.INVOICE_GET,INVOICE_ID));
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }


    /**
     * Cancel an unpaid invoice of respective customer using invoice id.
     * @throws RazorpayException
     */
    @Test
    public void cancel() throws RazorpayException {

        String mockedResponseJson = "{\n  \"issued_at\": 1481541533,\n  \"customer_details\":\n  {\n    \"customer_name\": \"Gaurav Kumar\",\n    \"customer_email\": \"gaurav.kumar@example.com\",\n    \"customer_contact\": \"9999999999\"\n  },\n  \"short_url\": \"http://bit.ly/link\", \n  \"receipt\": null, \n  \"entity\": \"invoice\", \n  \"currency\": \"INR\", \n  \"paid_at\": null, \n  \"view_less\": true, \n  \"id\": "+INVOICE_ID+", \n  \"customer_id\": \"cust_E7q0trFqXgExmT\", \n  \"type\": null, \n  \"status\": \"cancelled\", \n  \"description\": \"random decsription\", \n  \"order_id\": \"order_random_id\", \n  \"sms_status\": \"pending\", \n  \"date\": 1481541533, \n  \"payment_id\": null, \n  \"amount\": 100,\n  \"email_status\": \"pending\",\n  \"created_at\": 1481541534\n}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Invoice fetch = invoiceClient.cancel(INVOICE_ID);
            assertNotNull(fetch);
            assertEquals(INVOICE_ID,fetch.get("id"));
            assertEquals("pending",fetch.get("email_status"));
            assertTrue(fetch.has("customer_details"));
            assertTrue(fetch.has("issued_at"));
            String cancelRequest = getHost(String.format(Constants.INVOICE_CANCEL,INVOICE_ID));
            verifySentRequest(false, null, cancelRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }


    /**
     * Send notifications with the short URL to the customer via email or SMS
     * using invoice id
     * @throws RazorpayException
     */
    @Test
    public void notifyBy() throws RazorpayException {

        String mockedResponseJson = "{\n    \"entity\" : \"invoice\",\n    \"success\": true\n}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Invoice fetch = invoiceClient.notifyBy(INVOICE_ID,"sms");
            assertNotNull(fetch);
            assertTrue(fetch.has("success"));
            assertTrue(fetch.has("entity"));
            String notifyByRequest = getHost(String.format(Constants.INVOICE_NOTIFY,INVOICE_ID,"sms"));
            verifySentRequest(false, null, notifyByRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }


    /**
     * Create a registration for a recurring payment
     * @throws RazorpayException
     */
    @Test
    public void createRegistrationLink() throws RazorpayException {

        JSONObject request = new JSONObject("{\n  \"email\": \"gaurav.kumar@example.com\",\n  \"contact\": \"9123456789\",\n  \"amount\": 1000,\n  \"currency\": \"INR\",\n  \"order_id\": \"order_1Aa00000000002\",\n  \"customer_id\": \"cust_1Aa00000000001\",\n  \"token\": \"token_1Aa00000000001\",\n  \"recurring\": \"1\",\n  \"description\": \"Creating recurring payment for Gaurav Kumar\",\n  \"notes\": {\n    \"note_key 1\": \"Beam me up Scotty\",\n    \"note_key 2\": \"Tea. Earl Gray. Hot.\"\n  }\n}");
        String mockedResponseJson = "{\n   \"entity\" : \"invoice\",\n  \"razorpay_payment_id\" : \"pay_1Aa00000000001\",\n  \"razorpay_order_id\" : \"order_1Aa00000000001\",\n  \"razorpay_signature\" : \"9ef4dffbfd84f1318f6739a3ce19f9d85851857ae648f114332d8401e0949a3d\"\n}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Invoice fetch = invoiceClient.createRegistrationLink(request);
            assertNotNull(fetch);
            assertEquals("invoice",fetch.get("entity"));
            assertTrue(fetch.has("razorpay_payment_id"));
            assertTrue(fetch.has("razorpay_order_id"));
            assertTrue(fetch.has("razorpay_signature"));
            String createRegistrationLinkRequest = getHost(Constants.SUBSCRIPTION_REGISTRATION_LINK);
            verifySentRequest(true, request.toString(), createRegistrationLinkRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Invoice in the draft status can only be issued using customer's invoice id
     * @throws RazorpayException
     */
    @Test
    public void issue() throws RazorpayException {

        String mockedResponseJson = "{\n  \"id\": "+INVOICE_ID+",\n  \"entity\": \"invoice\",\n  \"receipt\": \"#0961\",\n  \"invoice_number\": \"#0961\",\n  \"customer_id\": \"cust_DAtUWmvpktokrT\",\n  \"customer_details\": {\n    \"id\": \"cust_DAtUWmvpktokrT\",\n    \"name\": \"Gaurav Kumar\",\n    \"email\": \"gaurav.kumar@example.com\",\n    \"contact\": \"9977886633\",\n    \"gstin\": null,\n    \"billing_address\": {\n      \"id\": \"addr_DAtUWoxgu91obl\",\n      \"type\": \"billing_address\",\n      \"primary\": true,\n      \"line1\": \"318 C-Wing, Suyog Co. Housing Society Ltd.\",\n      \"line2\": \"T.P.S Road, Vazira, Borivali\",\n      \"zipcode\": \"400092\",\n      \"city\": \"Mumbai\",\n      \"state\": \"Maharashtra\",\n      \"country\": \"in\"\n    },\n    \"shipping_address\": null,\n    \"customer_name\": \"Gaurav Kumar\",\n    \"customer_email\": \"gaurav.kumar@example.com\",\n    \"customer_contact\": \"9977886633\"\n  },\n  \"order_id\": \"order_DBG3P8ZgDd1dsG\",\n  \"line_items\": [\n    {\n      \"id\": \"li_DAweOizsysoJU6\",\n      \"item_id\": null,\n      \"name\": \"Book English August - Updated name and quantity\",\n      \"description\": \"150 points in Quidditch\",\n      \"amount\": 400,\n      \"unit_amount\": 400,\n      \"gross_amount\": 400,\n      \"tax_amount\": 0,\n      \"taxable_amount\": 400,\n      \"net_amount\": 400,\n      \"currency\": \"INR\",\n      \"type\": \"invoice\",\n      \"tax_inclusive\": false,\n      \"hsn_code\": null,\n      \"sac_code\": null,\n      \"tax_rate\": null,\n      \"unit\": null,\n      \"quantity\": 1,\n      \"taxes\": []\n    },\n    {\n      \"id\": \"li_DAwjWQUo07lnjF\",\n      \"item_id\": null,\n      \"name\": \"Book A Wild Sheep Chase\",\n      \"description\": null,\n      \"amount\": 200,\n      \"unit_amount\": 200,\n      \"gross_amount\": 200,\n      \"tax_amount\": 0,\n      \"taxable_amount\": 200,\n      \"net_amount\": 200,\n      \"currency\": \"INR\",\n      \"type\": \"invoice\",\n      \"tax_inclusive\": false,\n      \"hsn_code\": null,\n      \"sac_code\": null,\n      \"tax_rate\": null,\n      \"unit\": null,\n      \"quantity\": 1,\n      \"taxes\": []\n    }\n  ],\n  \"payment_id\": null,\n  \"status\": \"issued\",\n  \"expire_by\": 1567103399,\n  \"issued_at\": 1566974805,\n  \"paid_at\": null,\n  \"cancelled_at\": null,\n  \"expired_at\": null,\n  \"sms_status\": null,\n  \"email_status\": null,\n  \"date\": 1566891149,\n  \"terms\": null,\n  \"partial_payment\": false,\n  \"gross_amount\": 600,\n  \"tax_amount\": 0,\n  \"taxable_amount\": 600,\n  \"amount\": 600,\n  \"amount_paid\": 0,\n  \"amount_due\": 600,\n  \"currency\": \"INR\",\n  \"currency_symbol\": \"\u20b9\",\n  \"description\": \"This is a test invoice.\",\n  \"notes\": {\n    \"updated-key\": \"An updated note.\"\n  },\n  \"comment\": null,\n  \"short_url\": \"\",\n  \"view_less\": true,\n  \"billing_start\": null,\n  \"billing_end\": null,\n  \"type\": \"invoice\",\n  \"group_taxes_discounts\": false,\n  \"created_at\": 1566906474,\n  \"idempotency_key\": null\n}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Invoice fetch = invoiceClient.issue(INVOICE_ID);
            assertNotNull(fetch);
            assertEquals(INVOICE_ID,fetch.get("id"));
            assertEquals("invoice",fetch.get("entity"));
            assertTrue(fetch.has("invoice_number"));
            assertTrue(fetch.has("receipt"));
            String issueRequest = getHost(String.format(Constants.INVOICE_ISSUE,INVOICE_ID));
            verifySentRequest(false, null, issueRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Update an invoice of respective customer using invoice id with object of that properties
     * @throws RazorpayException
     */
    @Test
    public void edit() throws RazorpayException {
        JSONObject request = new JSONObject("{ \n  \"notes\": {\n    \"updated-key\": \"An updated note.\"\n  }\n}");
        String mockedResponseJson = "{\n  \"id\": "+INVOICE_ID+",\n  \"entity\": \"invoice\",\n  \"receipt\": \"#0961\",\n  \"invoice_number\": \"#0961\",\n  \"customer_id\": \"cust_DAtUWmvpktokrT\",\n  \"customer_details\": {\n    \"id\": \"cust_DAtUWmvpktokrT\",\n    \"name\": \"Gaurav Kumar\",\n    \"email\": \"gaurav.kumar@example.com\",\n    \"contact\": \"9977886633\",\n    \"gstin\": null,\n    \"billing_address\": {\n      \"id\": \"addr_DAtUWoxgu91obl\",\n      \"type\": \"billing_address\",\n      \"primary\": true,\n      \"line1\": \"318 C-Wing, Suyog Co. Housing Society Ltd.\",\n      \"line2\": \"T.P.S Road, Vazira, Borivali\",\n      \"zipcode\": \"400092\",\n      \"city\": \"Mumbai\",\n      \"state\": \"Maharashtra\",\n      \"country\": \"in\"\n    },\n    \"shipping_address\": null,\n    \"customer_name\": \"Gaurav Kumar\",\n    \"customer_email\": \"gaurav.kumar@example.com\",\n    \"customer_contact\": \"9977886633\"\n  },\n  \"order_id\": null,\n  \"line_items\": [\n    {\n      \"id\": \"li_DAweOizsysoJU6\",\n      \"item_id\": null,\n      \"name\": \"Book English August - Updated name and quantity\",\n      \"description\": \"150 points in Quidditch\",\n      \"amount\": 400,\n      \"unit_amount\": 400,\n      \"gross_amount\": 400,\n      \"tax_amount\": 0,\n      \"taxable_amount\": 400,\n      \"net_amount\": 400,\n      \"currency\": \"INR\",\n      \"type\": \"invoice\",\n      \"tax_inclusive\": false,\n      \"hsn_code\": null,\n      \"sac_code\": null,\n      \"tax_rate\": null,\n      \"unit\": null,\n      \"quantity\": 1,\n      \"taxes\": []\n    },\n    {\n      \"id\": \"li_DAwjWQUo07lnjF\",\n      \"item_id\": null,\n      \"name\": \"Book A Wild Sheep Chase\",\n      \"description\": null,\n      \"amount\": 200,\n      \"unit_amount\": 200,\n      \"gross_amount\": 200,\n      \"tax_amount\": 0,\n      \"taxable_amount\": 200,\n      \"net_amount\": 200,\n      \"currency\": \"INR\",\n      \"type\": \"invoice\",\n      \"tax_inclusive\": false,\n      \"hsn_code\": null,\n      \"sac_code\": null,\n      \"tax_rate\": null,\n      \"unit\": null,\n      \"quantity\": 1,\n      \"taxes\": []\n    }\n  ],\n  \"payment_id\": null,\n  \"status\": \"draft\",\n  \"expire_by\": 1567103399,\n  \"issued_at\": null,\n  \"paid_at\": null,\n  \"cancelled_at\": null,\n  \"expired_at\": null,\n  \"sms_status\": null,\n  \"email_status\": null,\n  \"date\": 1566891149,\n  \"terms\": null,\n  \"partial_payment\": false,\n  \"gross_amount\": 600,\n  \"tax_amount\": 0,\n  \"taxable_amount\": 600,\n  \"amount\": 600,\n  \"amount_paid\": null,\n  \"amount_due\": null,\n  \"currency\": \"INR\",\n  \"currency_symbol\": \"\u20b9\",\n  \"description\": \"This is a test invoice.\",\n  \"notes\": {\n    \"updated-key\": \"An updated note.\"\n  },\n  \"comment\": null,\n  \"short_url\": null,\n  \"view_less\": true,\n  \"billing_start\": null,\n  \"billing_end\": null,\n  \"type\": \"invoice\",\n  \"group_taxes_discounts\": false,\n  \"created_at\": 1566906474,\n  \"idempotency_key\": null\n}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Invoice invoice = invoiceClient.edit(INVOICE_ID,request);
            assertNotNull(invoice);
            assertEquals(INVOICE_ID,invoice.get("id"));
            assertEquals("invoice",invoice.get("entity"));
            assertTrue(invoice.has("invoice_number"));
            assertTrue(invoice.has("receipt"));
            String editRequest = getHost(String.format(Constants.INVOICE_GET,INVOICE_ID));
            verifySentRequest(true, request.toString(), editRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Delete an Invoice
     * @throws RazorpayException
     */
    @Test
    public void testDeleteInvoice() throws IOException, RazorpayException {
        try {
            ArrayList<String> mockedResponseJson = new ArrayList<String>();
            mockResponseFromExternalClient(String.valueOf(mockedResponseJson));
            mockResponseHTTPCodeFromExternalClient(200);
            List<Invoice> invoice = invoiceClient.delete(INVOICE_ID);
            assertNotNull(invoice);
            String editRequest = getHost(String.format(Constants.INVOICE_GET,INVOICE_ID));
            verifySentRequest(false, null, editRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}