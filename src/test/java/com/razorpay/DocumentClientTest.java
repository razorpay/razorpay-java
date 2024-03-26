package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;
import java.io.IOException;
import static org.junit.Assert.*;

public class DocumentClientTest extends BaseTest{

    @InjectMocks
    protected DocumentClient documentClient = new DocumentClient(TEST_SECRET_KEY);

    private static final String DOCUMENT_ID = "doc_EsyWjHrfzb59Re";

    @Test
    public void create() throws RazorpayException {
        JSONObject request = new JSONObject();
        request.put("files","/Users/your_name/Downloads/sample_uploaded.pdf");
        request.put("purpose","dispute_evidence");

        String mockedResponseJson = "{\n" +
                "  \"id\": \"doc_EsyWjHrfzb59Re\",\n" +
                "  \"entity\": \"document\",\n" +
                "  \"purpose\": \"dispute_evidence\",\n" +
                "  \"name\": \"file_19_18_2020.jpg\",\n" +
                "  \"mime_type\": \"image/png\",\n" +
                "  \"size\": 2863,\n" +
                "  \"created_at\": 1590604200\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Document document = documentClient.create(request);
            assertNotNull(document);
            assertEquals("document",document.get("entity"));
            assertTrue(document.has("name"));
            assertTrue(document.has("size"));
            String createRequest = getHost(Constants.DOCUMENTS);
            verifySentRequest(true, request.toString(), createRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void fetch() throws RazorpayException {

        String mockedResponseJson = "{\n" +
                "  \"id\": \"doc_EsyWjHrfzb59Re\",\n" +
                "  \"entity\": \"document\",\n" +
                "  \"purpose\": \"dispute_evidence\",\n" +
                "  \"name\": \"doc_19_12_2020.jpg\",\n" +
                "  \"mime_type\": \"image/png\",\n" +
                "  \"size\": 2863,\n" +
                "  \"created_at\": 1590604200\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            Document fetch = documentClient.fetch(DOCUMENT_ID);
            assertNotNull(fetch);
            assertEquals(DOCUMENT_ID,fetch.get("id"));
            assertTrue(fetch.has("purpose"));
            assertTrue(fetch.has("name"));
            String fetchRequest = getHost(String.format(Constants.DOCUMENT_FETCH,DOCUMENT_ID));
            verifySentRequest(false, null, fetchRequest);
        } catch (IOException e) {
            assertTrue(false);
        }
    }
}