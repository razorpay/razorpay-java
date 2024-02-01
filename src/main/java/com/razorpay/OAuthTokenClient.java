package com.razorpay;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OAuthTokenClient extends ApiClient {

    static final String CLIENT_ID = "client_id";
    static final String CLIENT_SECRET = "client_secret";
    static final String GRANT_TYPE = "grant_type";
    private final PayloadValidator payloadValidator;

    OAuthTokenClient(String auth) {
        super(auth);
        this.payloadValidator = new PayloadValidator();
    }

    public String getAuthURL(JSONObject request) throws RazorpayException {
        validateAuthURLRequest(request);

        String clientId = request.getString("client_id");
        String redirectUri = request.getString("redirect_uri");
        String state = request.getString("state");
        String[] scopes = jsonArrayToStringArray(request.getJSONArray("scopes"));

        String scopesArray = scopes.length > 0 ?
                "&scope[]=" + String.join("&scope[]=", scopes) : "";

        String AuthorizeUrl = "https://"
                + Constants.AUTH_HOSTNAME
                + "/" + Constants.AUTHORIZE
                + "?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + scopesArray
                + "&state=" + state;

        return AuthorizeUrl;
    }

    public OauthToken getAccessToken(JSONObject request) throws RazorpayException {
        validateAccessTokenRequest(request);
        return post(null, Constants.TOKEN, request, Constants.AUTH);
    }

    public OauthToken refreshToken(JSONObject request) throws RazorpayException {
        validateRefreshTokenRequest(request);
        request.put("grant_type", "refresh_token");
        return post(null, Constants.TOKEN, request, Constants.AUTH);
    }

    public OauthToken revokeToken(JSONObject request) throws RazorpayException {
        validateRevokeTokenRequest(request);
        return patch(null, Constants.REVOKE, request, Constants.AUTH);
    }

    private String[] jsonArrayToStringArray(JSONArray jsonArray) {
        String[] array = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            array[i] = jsonArray.getString(i);
        }
        return array;
    }

    private void validateAuthURLRequest(JSONObject request) throws RazorpayException {
        payloadValidator.validate(request, getValidationsForAuthRequestURL());
    }

    private void validateAccessTokenRequest(JSONObject request) throws RazorpayException {
        payloadValidator.validate(request, getValidationsForAccessTokenRequest());
    }

    private void validateRefreshTokenRequest(JSONObject request) throws RazorpayException {
        payloadValidator.validate(request, getValidationsForRefreshTokenRequest());
    }

    private void validateRevokeTokenRequest(JSONObject request) throws RazorpayException {
        payloadValidator.validate(request, getValidationsForRevokeTokenRequest());
    }

    private List<ValidationConfig> getValidationsForAuthRequestURL() {
        return Arrays.asList(
                new ValidationConfig("client_id", Collections.singletonList(ValidationType.ID)),
                new ValidationConfig("redirect_uri", Arrays.asList(ValidationType.NON_EMPTY_STRING, ValidationType.URL)),
                new ValidationConfig("scopes", Collections.singletonList(ValidationType.NON_NULL)),
                new ValidationConfig("state", Collections.singletonList(ValidationType.NON_EMPTY_STRING))
        );
    }

    private List<ValidationConfig> getValidationsForAccessTokenRequest() {
        return Arrays.asList(
                new ValidationConfig("client_id", Collections.singletonList(ValidationType.ID)),
                new ValidationConfig("client_secret", Collections.singletonList(ValidationType.NON_EMPTY_STRING)),
                new ValidationConfig("redirect_uri", Arrays.asList(ValidationType.NON_EMPTY_STRING, ValidationType.URL)),
                new ValidationConfig("mode", Collections.singletonList(ValidationType.NON_EMPTY_STRING))
        );
    }

    private List<ValidationConfig> getValidationsForRefreshTokenRequest() {
        return Arrays.asList(
                new ValidationConfig("client_id", Collections.singletonList(ValidationType.ID)),
                new ValidationConfig("client_secret", Collections.singletonList(ValidationType.NON_EMPTY_STRING)),
                new ValidationConfig("refresh_token", Collections.singletonList(ValidationType.NON_EMPTY_STRING))
        );
    }

    private List<ValidationConfig> getValidationsForRevokeTokenRequest() {
        return Arrays.asList(
                new ValidationConfig("client_id", Collections.singletonList(ValidationType.ID)),
                new ValidationConfig("client_secret", Collections.singletonList(ValidationType.NON_EMPTY_STRING)),
                new ValidationConfig("token", Collections.singletonList(ValidationType.NON_EMPTY_STRING)),
                new ValidationConfig("token_type_hint", Collections.singletonList(ValidationType.NON_EMPTY_STRING))
        );
    }
}
