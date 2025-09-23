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
        request.put("files","/Users/your_name/Downloads/sample_uploaded.jpeg");
        request.put("purpose","dispute_evidence");

        JSONObject mockedResponseJson = new JSONObject();
        mockedResponseJson.put("id","doc_EsyWjHrfzb59Re");
        mockedResponseJson.put("entity","document");
        mockedResponseJson.put("purpose","dispute_evidence");
        mockedResponseJson.put("name","sample_uploaded.jpg");
        mockedResponseJson.put("mime_type","image/png");
        mockedResponseJson.put("size","2863");
        mockedResponseJson.put("created_at","1590604200");

        try {
            mockResponseFromExternalClient(mockedResponseJson.toString());
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

        JSONObject mockedResponseJson = new JSONObject();
        mockedResponseJson.put("id","doc_EsyWjHrfzb59Re");
        mockedResponseJson.put("entity","document");
        mockedResponseJson.put("purpose","dispute_evidence");
        mockedResponseJson.put("name","doc_19_12_2020.jpg");
        mockedResponseJson.put("mime_type","image/png");
        mockedResponseJson.put("size","2863");
        mockedResponseJson.put("created_at","1590604200");

        try {
            mockResponseFromExternalClient(mockedResponseJson.toString());
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