package com.razorpay;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;

import static org.junit.Assert.*;

public class OAuthTokenClientTest extends BaseTest {

    @InjectMocks
    protected OAuthTokenClient oAuthTokenClient;

    {
        try {
            oAuthTokenClient = new OAuthTokenClient();
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetAuthURL() throws RazorpayException {
        JSONObject request = new JSONObject("{\n    " +
                "\"client_id\": \"8DXCMTshWSWECc\",\n    " +
                "\"redirect_uri\": \"https://example.com/razorpay_callback\",\n    " +
                "\"state\": \"NOBYtv8r6c75ex6WZ\",\n    " +
                "\"scopes\": [\n        \"read_write\"\n    ]\n" +
                "}");

        String expectedAuthURL = "https://auth.razorpay.com/authorize?response_type=code&client_id=8DXCMTshWSWECc&redirect_uri=https%3A%2F%2Fexample.com%2Frazorpay_callback&state=NOBYtv8r6c75ex6WZ&scope%5B%5D%3D=read_write";
        String authURL = oAuthTokenClient.getAuthURL(request);
        assertEquals(expectedAuthURL, authURL);
    }

    @Test
    public void testRequestValidationFailureForGetAuthURL() {
        try {
            JSONObject request = new JSONObject("{\n    " +
                    "\"client_id\": \"8DXCMTshWSWECc\",\n    " +
                    "\"redirect_uri\": \"https://example.com/razorpay_callback\",\n    " +
                    "\"scopes\": [\n        \"read_write\"\n    ]\n" +
                    "}");
            oAuthTokenClient.getAuthURL(request);
        } catch (RazorpayException ex) {
            assertEquals("Field state cannot be empty", ex.getMessage());
        }
    }

    @Test
    public void testGetAccessToken() throws RazorpayException {
        JSONObject request = new JSONObject("{\n    " +
                "\"client_id\": \"8DXCMTshWSWECc\",\n    " +
                "\"client_secret\": \"AESSECRETKEY\",\n    " +
                "\"grant_type\": \"authorization_code\",\n    " +
                "\"redirect_uri\": \"http://example.com/razorpay_callback\",\n    " +
                "\"code\": \"def50200d844dc80cc44dce2c665d07a374d76802\",\n    " +
                "\"mode\": \"test\"\n}");
        String mockedResponseJson = "{\n    " +
                "\"public_token\": \"rzp_test_oauth_9xu1rkZqoXlClS\",\n    " +
                "\"token_type\": \"Bearer\",\n    " +
                "\"expires_in\": 7862400,\n    " +
                "\"access_token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IkY1Z0NQYkhhRzRjcUpnIn0." +
                "eyJhdWQiOiJGNFNNeEgxanMxbkpPZiIsImp0aSI6IkY1Z0NQYkhhRzRjcUpnIiwiaWF0IjoxNTkyO" +
                "DMxMDExLCJuYmYiOjE1OTI4MzEwMTEsInN1YiI6IiIsImV4cCI6MTYwMDc3OTgxMSwidXNlcl9pZC" +
                "I6IkYycVBpejJEdzRPRVFwIiwibWVyY2hhbnRfaWQiOiJGMnFQaVZ3N0lNV01GSyIsInNjb3BlcyI6" +
                "WyJyZWFkX29ubHkiXX0.Wwqt5czhoWpVzP5_aoiymKXoGj-ydo-4A_X2jf_7rrSvk4pXdqzbA5BMrHxPdP" +
                "beFQWV6vsnsgbf99Q3g-W4kalHyH67LfAzc3qnJ-mkYDkFY93tkeG-MCco6GJW-Jm8xhaV9EPUak7z9J9j" +
                "cdluu9rNXYMtd5qxD8auyRYhEgs\",\n    " +
                "\"refresh_token\": \"def50200f42e07aded65a323f6c53181d802cc797b62cc5e78dd8038d6dff25" +
                "3e5877da9ad32f463a4da0ad895e3de298cbce40e162202170e763754122a6cb97910a1f58e2378ee3492d" +
                "c295e1525009cccc45635308cce8575bdf373606c453ebb5eb2bec062ca197ac23810cf9d6cf31fbb9fcf5b" +
                "7d4de9bf524c89a4aa90599b0151c9e4e2fa08acb6d2fe17f30a6cfecdfd671f090787e821f844e5d36f5eac" +
                "b7dfb33d91e83b18216ad0ebeba2bef7721e10d436c3984daafd8654ed881c581d6be0bdc9ebfaee0dc5f9374" +
                "d7184d60aae5aa85385690220690e21bc93209fb8a8cc25a6abf1108d8277f7c3d38217b47744d7\",\n    " +
                "\"razorpay_account_id\": \"acc_Dhk2qDbmu6FwZH\"\n}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            OauthToken oAuthToken = oAuthTokenClient.getAccessToken(request);
            assertNotNull(oAuthToken);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testGetAccessTokenValidationFailure() {
        try {
            JSONObject request = new JSONObject("{\n    " +
                    "\"client_id\": \"8DXCMTshWSWECc\",\n    " +
                    "\"grant_type\": \"authorization_code\",\n    " +
                    "\"redirect_uri\": \"http://example.com/razorpay_callback\",\n    " +
                    "\"code\": \"def50200d844dc80cc44dce2c665d07a374d76802\",\n    " +
                    "\"mode\": \"test\"\n}");
            oAuthTokenClient.getAccessToken(request);
        } catch (RazorpayException ex) {
            assertEquals("Field client_secret cannot be empty", ex.getMessage());
        }
    }

    @Test
    public void testGetAccessTokenForClientCredentials() throws RazorpayException {
        JSONObject request = new JSONObject("{\n    " +
                "\"client_id\": \"8DXCMTshWSWECc\",\n    " +
                "\"client_secret\": \"AESSECRETKEY\",\n    " +
                "\"grant_type\": \"client_credentials\",\n    " +
                "\"redirect_uri\": \"http://example.com/razorpay_callback\",\n    " +
                "\"mode\": \"test\"\n}");
        String mockedResponseJson = "{\n    " +
                "\"public_token\": \"rzp_test_oauth_9xu1rkZqoXlClS\",\n    " +
                "\"token_type\": \"Bearer\",\n    " +
                "\"expires_in\": 7862400,\n    " +
                "\"access_token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IkY1Z0NQYkhhRzRjcUpnIn0." +
                "eyJhdWQiOiJGNFNNeEgxanMxbkpPZiIsImp0aSI6IkY1Z0NQYkhhRzRjcUpnIiwiaWF0IjoxNTkyO" +
                "DMxMDExLCJuYmYiOjE1OTI4MzEwMTEsInN1YiI6IiIsImV4cCI6MTYwMDc3OTgxMSwidXNlcl9pZC" +
                "I6IkYycVBpejJEdzRPRVFwIiwibWVyY2hhbnRfaWQiOiJGMnFQaVZ3N0lNV01GSyIsInNjb3BlcyI6" +
                "WyJyZWFkX29ubHkiXX0.Wwqt5czhoWpVzP5_aoiymKXoGj-ydo-4A_X2jf_7rrSvk4pXdqzbA5BMrHxPdP" +
                "beFQWV6vsnsgbf99Q3g-W4kalHyH67LfAzc3qnJ-mkYDkFY93tkeG-MCco6GJW-Jm8xhaV9EPUak7z9J9j" +
                "cdluu9rNXYMtd5qxD8auyRYhEgs\",\n    " +
                "\"refresh_token\": \"def50200f42e07aded65a323f6c53181d802cc797b62cc5e78dd8038d6dff25" +
                "3e5877da9ad32f463a4da0ad895e3de298cbce40e162202170e763754122a6cb97910a1f58e2378ee3492d" +
                "c295e1525009cccc45635308cce8575bdf373606c453ebb5eb2bec062ca197ac23810cf9d6cf31fbb9fcf5b" +
                "7d4de9bf524c89a4aa90599b0151c9e4e2fa08acb6d2fe17f30a6cfecdfd671f090787e821f844e5d36f5eac" +
                "b7dfb33d91e83b18216ad0ebeba2bef7721e10d436c3984daafd8654ed881c581d6be0bdc9ebfaee0dc5f9374" +
                "d7184d60aae5aa85385690220690e21bc93209fb8a8cc25a6abf1108d8277f7c3d38217b47744d7\",\n    " +
                "\"razorpay_account_id\": \"acc_Dhk2qDbmu6FwZH\"\n}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            OauthToken oAuthToken = oAuthTokenClient.getAccessToken(request);
            assertNotNull(oAuthToken);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testRefreshToken() throws RazorpayException {
        JSONObject request = new JSONObject("{\n    " +
                "\"client_id\": \"8DXCMTshWSWECc\",\n    " +
                "\"client_secret\": \"AESSECRETKEY\",\n    " +
                "\"refresh_token\": \"def5020096e1c470c901d34cd60fa53abdaf3662sa0\"\n}");
        String mockedResponseJson = "{\n    " +
                "\"public_token\": \"rzp_test_oauth_9xu1rkZqoXlClS\",\n    " +
                "\"token_type\": \"Bearer\",\n    " +
                "\"expires_in\": 7862400,\n    " +
                "\"access_token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijl4dTF\",\n    " +
                "\"refresh_token\": \"def5020096e1c470c901d34cd60fa53abdaf36620e823ffa53\"\n" +
                "}";
        try {
            mockResponseFromExternalClient(mockedResponseJson);
            mockResponseHTTPCodeFromExternalClient(200);
            OauthToken oAuthToken = oAuthTokenClient.refreshToken(request);
            assertNotNull(oAuthToken);
            assertEquals("rzp_test_oauth_9xu1rkZqoXlClS", oAuthToken.get("public_token"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testRefreshTokenValidationFailure() {
        try {
            JSONObject request = new JSONObject("{\n    " +
                    "\"client_id\": \"8DXCMTshWSWECc\",\n    " +
                    "\"client_secret\": \"AESSECRETKEY\",\n    " +
                    "}");
            oAuthTokenClient.refreshToken(request);
        } catch (RazorpayException ex) {
            assertEquals("Field refresh_token cannot be empty", ex.getMessage());
        }
    }

    @Test
    public void testRevokeToken() throws RazorpayException {
        JSONObject request = new JSONObject("{\n    " +
                "\"client_id\": \"JA1p85ntMrHJhA\",\n    " +
                "\"client_secret\": \"DcTFepYebC7FuNk39C8M3yl2\",\n    " +
                "\"token_type_hint\": \"access_token\",\n    " +
                "\"token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJKQTFwODVudE1ySEpoQ" +
                "SIsImp0aSI6IkpPZkd0aHFDTmhqQUhTIiwiaWF0IjoxNjUxMTI0NTU0LCJuYmYiOjE2NTExMjQ1NTQs" +
                "InN1YiI6IiIsImV4cCI6MTY1ODk4Njk1MiwidXNlcl9pZCI6bnVsbCwibWVyY2hhbnRfaWQiOiJKOWp" +
                "oSTdzZkM1S1V0NiIsInNjb3BlcyI6WyJyZWFkX3dyaXRlIl19.h1oL_Tik642Q18DdyEnIVziW1kgw6" +
                "k09K8ALuI4uWQBH3jE4R8p1e6ysQq-Et4E_MZd7ADfC1W6kFwe3PXlkLC6emaZAKESZghbtTBM6RYnh" +
                "ieErAOcD7ytc0P8c75aNRlC6MWwlWaH20OFYuSay7iGFyw2jp4by4xDFlYweVLc\"\n" +
                "}");
        try {
            mockResponseFromExternalClient("{\n    \"message\": \"Token Revoked\"\n}");
            mockResponseHTTPCodeFromExternalClient(200);
            OauthToken oAuthToken = oAuthTokenClient.revokeToken(request);
            assertNotNull(oAuthToken);
            assertEquals("Token Revoked", oAuthToken.get("message"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testRevokeTokenValidationFailure() {
        try {
            JSONObject request = new JSONObject("{\n    " +
                    "\"client_id\": \"JA1p85ntMrHJhA\",\n    " +
                    "\"client_secret\": \"DcTFepYebC7FuNk39C8M3yl2\",\n    " +
                    "}");
            oAuthTokenClient.revokeToken(request);
        } catch (RazorpayException ex) {
            assertEquals("Field token cannot be empty", ex.getMessage());
        }
    }
}
