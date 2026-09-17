package com.razorpay;

import okhttp3.MultipartBody;
import okhttp3.Request;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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

    /**
     * Uploads a document with the given file name and returns the multipart body that was
     * actually put on the wire.
     */
    private MultipartBody captureUploadBody(String fileName) throws RazorpayException, IOException {
        File upload = new File(Files.createTempDirectory("razorpay-doc-test").toFile(), fileName);
        assertTrue(upload.createNewFile());
        upload.deleteOnExit();

        JSONObject request = new JSONObject();
        request.put("file", upload.getAbsolutePath());
        request.put("purpose", "dispute_evidence");

        JSONObject mockedResponseJson = new JSONObject();
        mockedResponseJson.put("id", DOCUMENT_ID);
        mockedResponseJson.put("entity", "document");
        mockedResponseJson.put("purpose", "dispute_evidence");
        mockedResponseJson.put("name", fileName);

        mockResponseFromExternalClient(mockedResponseJson.toString());
        mockResponseHTTPCodeFromExternalClient(200);
        documentClient.create(request);

        ArgumentCaptor<Request> sentRequest = ArgumentCaptor.forClass(Request.class);
        Mockito.verify(getOkHttpClient()).newCall(sentRequest.capture());

        return (MultipartBody) sentRequest.getValue().body();
    }

    /** Returns the Content-Disposition header of the part at the given index. */
    private String dispositionOf(MultipartBody body, int index) {
        return body.part(index).headers().get("Content-Disposition");
    }

    /** Counts how many parts of the multipart body are named {@code file}. */
    private int countFileParts(MultipartBody body) {
        int count = 0;
        for (int i = 0; i < body.size(); i++) {
            if (dispositionOf(body, i).contains("name=\"file\"")) {
                count++;
            }
        }
        return count;
    }

    @Test
    public void createSendsPngMimeType() throws RazorpayException, IOException {
        MultipartBody body = captureUploadBody("proof.png");

        assertEquals("image/png", body.part(0).body().contentType().toString());
    }

    @Test
    public void createSendsJpegMimeTypeForJpg() throws RazorpayException, IOException {
        MultipartBody body = captureUploadBody("proof.jpg");

        assertEquals("image/jpeg", body.part(0).body().contentType().toString());
    }

    @Test
    public void createSendsJpegMimeTypeForJpeg() throws RazorpayException, IOException {
        MultipartBody body = captureUploadBody("proof.jpeg");

        assertEquals("image/jpeg", body.part(0).body().contentType().toString());
    }

    @Test
    public void createSendsPdfMimeType() throws RazorpayException, IOException {
        MultipartBody body = captureUploadBody("proof.pdf");

        assertEquals("application/pdf", body.part(0).body().contentType().toString());
    }

    @Test
    public void createMatchesExtensionCaseInsensitively() throws RazorpayException, IOException {
        MultipartBody body = captureUploadBody("proof.PNG");

        assertEquals("image/png", body.part(0).body().contentType().toString());
    }

    @Test
    public void createSendsFilePartOnlyOnce() throws RazorpayException, IOException {
        MultipartBody body = captureUploadBody("proof.png");

        assertEquals(1, countFileParts(body));
        // Only the binary file part and the purpose field should be sent.
        assertEquals(2, body.size());
        assertTrue(dispositionOf(body, 0).contains("filename=\"proof.png\""));
        assertTrue(dispositionOf(body, 1).contains("name=\"purpose\""));
    }

    @Test
    public void createKeepsLegacyMimeTypeForUnsupportedExtension() throws RazorpayException, IOException {
        MultipartBody body = captureUploadBody("proof.txt");

        // Pre-existing behaviour, deliberately left unchanged: this upload path is shared with the
        // account and stakeholder document endpoints, whose accepted formats are not documented in
        // this repository.
        assertEquals("image/pdf", body.part(0).body().contentType().toString());
    }

}
