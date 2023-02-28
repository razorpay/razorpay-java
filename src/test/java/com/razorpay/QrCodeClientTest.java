package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class QrCodeClientTest extends BaseTest{

    @Mock
    ApiUtils apiUtils;


    private static final String QRCODE_ID = "qr_HMsVL8HOpbMcjU";

    private static final String CUSTOMER_ID = "cust_HKsR5se84c5LTO";

    /**
     * Qrcode is created using the customer and amount details.
     * @throws RazorpayException
     */
    @Test
    public void create() throws Exception {

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
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(Constants.QRCODE_CREATE, null);
            mockPostRequest(apiUtils,builder,request.toString(), mockedResponseJson);

            QrCodeClient qrCodeClient = new QrCodeClient("test",apiUtils);
            QrCode fetch = qrCodeClient.create(request);
            assertNotNull(fetch);
            assertEquals("qr_code",fetch.get("entity"));
            assertEquals(QRCODE_ID,fetch.get("id"));
            assertTrue(fetch.has("close_by"));
            assertTrue(fetch.has("fixed_amount"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Retrieve all the QrCode details using qr code id.
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws Exception{
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
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(String.format(Constants.QRCODE_FETCH, QRCODE_ID), null);
            mockGetRequest(apiUtils,builder,null, mockedResponseJson);

            QrCodeClient qrCodeClient = new QrCodeClient("test",apiUtils);
            QrCode fetch = qrCodeClient.fetch(QRCODE_ID);
            assertNotNull(fetch);
            assertEquals("upi_qr",fetch.get("type"));
            assertEquals(QRCODE_ID,fetch.get("id"));
            assertEquals(CUSTOMER_ID,fetch.get("customer_id"));
            assertTrue(fetch.has("close_by"));
            assertTrue(fetch.has("fixed_amount"));
        } catch (IOException e) {
            assertTrue(false);
        }

    }

    /**
     * Details of all the Qrcode can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws Exception{
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
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(Constants.QRCODE_LIST, null);
            mockGetRequest(apiUtils,builder,null, mockedResponseJson);

            QrCodeClient qrCodeClient = new QrCodeClient("test",apiUtils);
            List <QrCode> fetch = qrCodeClient.fetchAll();
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("type"));
            assertTrue(fetch.get(0).has("id"));
            assertTrue(fetch.get(0).has("customer_id"));
            assertTrue(fetch.get(0).has("close_by"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Close a QR Code. A customer cannot make payments for the closed QR codes.
     * @throws RazorpayException
     */
    @Test
    public void close() throws Exception{

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
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(String.format(Constants.QRCODE_CLOSE, QRCODE_ID), null);
            mockPostRequest(apiUtils,builder,null, mockedResponseJson);

            QrCodeClient qrCodeClient = new QrCodeClient("test",apiUtils);
            QrCode fetch = qrCodeClient.close(QRCODE_ID);
            assertNotNull(fetch);
            assertEquals("upi_qr",fetch.get("type"));
            assertEquals(QRCODE_ID,fetch.get("id"));
            assertEquals(CUSTOMER_ID,fetch.get("customer_id"));
            assertTrue(fetch.has("close_by"));
            assertTrue(fetch.has("fixed_amount"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}