package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class QrCodeClientTest extends BaseTest{

    @InjectMocks
    protected QrCodeClient qrCodeClient = new QrCodeClient(TEST_SECRET_KEY);


    private static final String QRCODE_ID = "qr_HMsVL8HOpbMcjU";

    private static final String CUSTOMER_ID = "cust_HKsR5se84c5LTO";

    /**
     * Qrcode is created using the customer and amount details.
     * @throws RazorpayException
     */
    @Test
    public void create() throws RazorpayException {

        JSONObject request = new JSONObject("{\n" +
                                     "  \"type\": \"upi_qr\",\n" +
                                     "  \"name\": \"Store_1\",\n" +
                                     "  \"usage\": \"single_use\",\n" +
                                     "  \"fixed_amount\": true,\n" +
                                     "  \"payment_amount\": 300,\n" +
                                     "  \"description\": \"For Store 1\",\n" +
                                     "  \"customer_id\": \"cust_HKsR5se84c5LTO\",\n" +
                                     "  \"close_by\": 1681615838,\n" +
                                     "  \"notes\": {\n" +
                                     "    \"purpose\": \"Test UPI QR code notes\"\n" +
                                     "  }\n" +
                                     "}");

        String mockedResponseJson = "{\n" +
                                    "  \"id\": "+QRCODE_ID+",\n" +
                                    "  \"entity\": \"qr_code\",\n" +
                                    "  \"created_at\": 1623660301,\n" +
                                    "  \"name\": \"Store_1\",\n" +
                                    "  \"usage\": \"single_use\",\n" +
                                    "  \"type\": \"upi_qr\",\n" +
                                    "  \"image_url\": \"https://rzp.io/i/BWcUVrLp\",\n" +
                                    "  \"payment_amount\": 300,\n" +
                                    "  \"status\": \"active\",\n" +
                                    "  \"description\": \"For Store 1\",\n" +
                                    "  \"fixed_amount\": true,\n" +
                                    "  \"payments_amount_received\": 0,\n" +
                                    "  \"payments_count_received\": 0,\n" +
                                    "  \"notes\": {\n" +
                                    "    \"purpose\": \"Test UPI QR code notes\"\n" +
                                    "  },\n" +
                                    "  \"customer_id\": \"cust_HKsR5se84c5LTO\",\n" +
                                    "  \"close_by\": 1681615838\n" +
                                    "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            QrCode fetch = qrCodeClient.create(request);
            assertNotNull(fetch);
            assertEquals("qr_code",fetch.get("entity"));
            assertEquals(QRCODE_ID,fetch.get("id"));
            assertTrue(fetch.has("close_by"));
            assertTrue(fetch.has("fixed_amount"));
            String createRequest = getHost(Constants.QRCODE_CREATE);
            verifySentRequest(true, request.toString(), createRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Retrieve all the QrCode details using qr code id.
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws RazorpayException{
        String mockedResponseJson = "{\n" +
                "  \"id\": "+QRCODE_ID+",\n" +
                "  \"entity\": \"qr_code\",\n" +
                "  \"created_at\": 1623915088,\n" +
                "  \"name\": \"Store_1\",\n" +
                "  \"usage\": \"single_use\",\n" +
                "  \"type\": \"upi_qr\",\n" +
                "  \"image_url\": \"https://rzp.io/i/oCswTOcCo\",\n" +
                "  \"payment_amount\": 300,\n" +
                "  \"status\": \"active\",\n" +
                "  \"description\": \"For Store 1\",\n" +
                "  \"fixed_amount\": true,\n" +
                "  \"payments_amount_received\": 0,\n" +
                "  \"payments_count_received\": 0,\n" +
                "  \"notes\": {\n" +
                "    \"purpose\": \"Test UPI QR code notes\"\n" +
                "  },\n" +
                "  \"customer_id\": \"cust_HKsR5se84c5LTO\",\n" +
                "  \"close_by\": 1681615838,\n" +
                "  \"closed_at\": null,\n" +
                "  \"close_reason\": null\n" +
                "}";

        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            QrCode fetch = qrCodeClient.fetch(QRCODE_ID);
            assertNotNull(fetch);
            assertEquals("upi_qr",fetch.get("type"));
            assertEquals(QRCODE_ID,fetch.get("id"));
            assertEquals(CUSTOMER_ID,fetch.get("customer_id"));
            assertTrue(fetch.has("close_by"));
            assertTrue(fetch.has("fixed_amount"));
            String fetchRequest = getHost(String.format(Constants.QRCODE_FETCH, QRCODE_ID));
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }

    }

    /**
     * Details of all the Qrcode can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws RazorpayException{
        String mockedResponseJson = "{\n" +
                "  \"entity\": \"collection\",\n" +
                "  \"count\": 2,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": \"qr_HO2jGkWReVBMNu\",\n" +
                "      \"entity\": \"qr_code\",\n" +
                "      \"created_at\": 1623914648,\n" +
                "      \"name\": \"Store_1\",\n" +
                "      \"usage\": \"single_use\",\n" +
                "      \"type\": \"upi_qr\",\n" +
                "      \"image_url\": \"https://rzp.io/i/w2CEwYmkAu\",\n" +
                "      \"payment_amount\": 300,\n" +
                "      \"status\": \"active\",\n" +
                "      \"description\": \"For Store 1\",\n" +
                "      \"fixed_amount\": true,\n" +
                "      \"payments_amount_received\": 0,\n" +
                "      \"payments_count_received\": 0,\n" +
                "      \"notes\": {\n" +
                "        \"purpose\": \"Test UPI QR code notes\"\n" +
                "      },\n" +
                "      \"customer_id\": \"cust_HKsR5se84c5LTO\",\n" +
                "      \"close_by\": 1681615838,\n" +
                "      \"closed_at\": null,\n" +
                "      \"close_reason\": null\n" +
                "    },\n" +
                "  ]\n" +
                "}";

        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List <QrCode> fetch = qrCodeClient.fetchAll();
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("type"));
            assertTrue(fetch.get(0).has("id"));
            assertTrue(fetch.get(0).has("customer_id"));
            assertTrue(fetch.get(0).has("close_by"));
            String fetchRequest = getHost(Constants.QRCODE_LIST);
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Close a QR Code. A customer cannot make payments for the closed QR codes.
     * @throws RazorpayException
     */
    @Test
    public void close() throws RazorpayException{

        String mockedResponseJson = "{\n" +
                "  \"id\": "+QRCODE_ID+",\n" +
                "  \"entity\": \"qr_code\",\n" +
                "  \"created_at\": 1623660301,\n" +
                "  \"name\": \"Store_1\",\n" +
                "  \"usage\": \"single_use\",\n" +
                "  \"type\": \"upi_qr\",\n" +
                "  \"image_url\": \"https://rzp.io/i/BWcUVrLp\",\n" +
                "  \"payment_amount\": 300,\n" +
                "  \"status\": \"closed\",\n" +
                "  \"description\": \"For Store 1\",\n" +
                "  \"fixed_amount\": true,\n" +
                "  \"payments_amount_received\": 0,\n" +
                "  \"payments_count_received\": 0,\n" +
                "  \"notes\": {\n" +
                "    \"purpose\": \"Test UPI QR code notes\"\n" +
                "  },\n" +
                "  \"customer_id\": "+CUSTOMER_ID+",\n" +
                "  \"close_by\": 1681615838,\n" +
                "  \"closed_at\": 1623660445,\n" +
                "  \"close_reason\": \"on_demand\"\n" +
                "}";

        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            QrCode fetch = qrCodeClient.close(QRCODE_ID);
            assertNotNull(fetch);
            assertEquals("upi_qr",fetch.get("type"));
            assertEquals(QRCODE_ID,fetch.get("id"));
            assertEquals(CUSTOMER_ID,fetch.get("customer_id"));
            assertTrue(fetch.has("close_by"));
            assertTrue(fetch.has("fixed_amount"));
            String closeRequest = getHost(String.format(Constants.QRCODE_CLOSE,QRCODE_ID));
            verifySentRequest(false, null, closeRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void fetchAllPayments() throws RazorpayException{
        JSONObject request = new JSONObject("{\"count\":\"1\"}");
        String mockedResponseJson = getAllPaymentsResponse();
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<QrCode> response = qrCodeClient.fetchAllPayments(QRCODE_ID);
            assertNotNull(response);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void fetchAllPaymentsWithOutQuery() throws RazorpayException{
        JSONObject request = new JSONObject("{\"count\":\"1\"}");
        String mockedResponseJson = getAllPaymentsResponse();
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            List<QrCode> response = qrCodeClient.fetchAllPayments(QRCODE_ID, request);
            assertNotNull(response);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    private String getAllPaymentsResponse() {
      return "{\n" +
              "  \"entity\": \"collection\",\n" +
              "  \"count\": 1,\n" +
              "  \"items\": [\n" +
              "    {\n" +
              "      \"id\": \"pay_HMtDKn3TnF4D8x\",\n" +
              "      \"entity\": \"payment\",\n" +
              "      \"amount\": 500,\n" +
              "      \"currency\": \"INR\",\n" +
              "      \"status\": \"captured\",\n" +
              "      \"order_id\": null,\n" +
              "      \"invoice_id\": null,\n" +
              "      \"international\": false,\n" +
              "      \"method\": \"upi\",\n" +
              "      \"amount_refunded\": 0,\n" +
              "      \"refund_status\": null,\n" +
              "      \"captured\": true,\n" +
              "      \"description\": \"QRv2 Payment\",\n" +
              "      \"card_id\": null,\n" +
              "      \"bank\": null,\n" +
              "      \"wallet\": null,\n" +
              "      \"vpa\": \"gauri.kumari@okhdfcbank\",\n" +
              "      \"email\": \"gauri.kumari@example.com\",\n" +
              "      \"contact\": \"+919000090000\",\n" +
              "      \"customer_id\": \"cust_HKsR5se84c5LTO\",\n" +
              "      \"notes\": [],\n" +
              "      \"fee\": 0,\n" +
              "      \"tax\": 0,\n" +
              "      \"error_code\": null,\n" +
              "      \"error_description\": null,\n" +
              "      \"error_source\": null,\n" +
              "      \"error_step\": null,\n" +
              "      \"error_reason\": null,\n" +
              "      \"acquirer_data\": {\n" +
              "        \"rrn\": \"116514257019\"\n" +
              "      },\n" +
              "      \"created_at\": 1623662800\n" +
              "    }\n" +
              "  ]\n" +
              "}";
    }
}