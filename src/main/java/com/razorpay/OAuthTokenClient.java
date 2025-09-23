package com.razorpay;

import okhttp3.HttpUrl;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OAuthTokenClient extends ApiClient {

    static final String CLIENT_ID = "client_id";
    static final String CLIENT_SECRET = "client_secret";
    static final String GRANT_TYPE = "grant_type";
    static final String REFRESH_TOKEN = "refresh_token";
    static final String TOKEN = "token";
    static final String TOKEN_TYPE_HINT = "token_type_hint";
    static final String REDIRECT_URI = "redirect_uri";
    static final String SCOPES = "scopes";
    static final String STATE = "state";
    static final String MODE = "mode";
    private final PayloadValidator payloadValidator;

    public OAuthTokenClient() throws RazorpayException {
        super();
        ApiUtils.createHttpClientInstance(false);
        this.payloadValidator = new PayloadValidator();
    }

    public String getAuthURL(JSONObject request) throws RazorpayException {
        validateAuthURLRequest(request);

        String clientId = request.getString("client_id");
        String redirectUri = request.getString("redirect_uri");
        String state = request.getString("state");
        String[] scopes = jsonArrayToStringArray(request.getJSONArray("scopes"));

        HttpUrl.Builder httpUrlBuilder = new HttpUrl.Builder()
                .scheme("https")
                .host(Constants.AUTH_HOSTNAME)
                .addPathSegment(Constants.AUTHORIZE)
                .addQueryParameter("response_type", "code")
                .addQueryParameter("client_id", clientId)
                .addQueryParameter("redirect_uri", redirectUri)
                .addQueryParameter("state", state);

        for (String scope : scopes) {
            httpUrlBuilder.addQueryParameter("scope[]=", scope);
        }

        if (request.has("onboarding_signature")) {
            httpUrlBuilder.addQueryParameter("onboarding_signature", request.getString("onboarding_signature"));
        }
        return httpUrlBuilder.build().toString();
    }

    public OauthToken getAccessToken(JSONObject request) throws RazorpayException {
        validateAccessTokenRequest(request);
        return post(null, Constants.TOKEN, request, Constants.AUTH);
    }

    public OauthToken refreshToken(JSONObject request) throws RazorpayException {
        request.put(GRANT_TYPE, REFRESH_TOKEN);
        validateRefreshTokenRequest(request);
        return post(null, Constants.TOKEN, request, Constants.AUTH);
    }

    public OauthToken revokeToken(JSONObject request) throws RazorpayException {
        validateRevokeTokenRequest(request);
        return post(null, Constants.REVOKE, request, Constants.AUTH);
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
                new ValidationConfig(CLIENT_ID, Collections.singletonList(ValidationType.ID)),
                new ValidationConfig(REDIRECT_URI, Arrays.asList(ValidationType.NON_EMPTY_STRING, ValidationType.URL)),
                new ValidationConfig(SCOPES, Collections.singletonList(ValidationType.NON_NULL)),
                new ValidationConfig(STATE, Collections.singletonList(ValidationType.NON_EMPTY_STRING))
        );
    }

    private List<ValidationConfig> getValidationsForAccessTokenRequest() {
        return Arrays.asList(
                new ValidationConfig(CLIENT_ID, Collections.singletonList(ValidationType.ID)),
                new ValidationConfig(CLIENT_SECRET, Collections.singletonList(ValidationType.NON_EMPTY_STRING)),
                new ValidationConfig(REDIRECT_URI, Arrays.asList(ValidationType.NON_EMPTY_STRING, ValidationType.URL)),
                new ValidationConfig(GRANT_TYPE, Collections.singletonList(ValidationType.TOKEN_GRANT))
        );
    }

    private List<ValidationConfig> getValidationsForRefreshTokenRequest() {
        return Arrays.asList(
                new ValidationConfig(CLIENT_ID, Collections.singletonList(ValidationType.ID)),
                new ValidationConfig(CLIENT_SECRET, Collections.singletonList(ValidationType.NON_EMPTY_STRING)),
                new ValidationConfig(REFRESH_TOKEN, Collections.singletonList(ValidationType.NON_EMPTY_STRING)),
                new ValidationConfig(GRANT_TYPE, Collections.singletonList(ValidationType.TOKEN_GRANT))
        );
    }

    private List<ValidationConfig> getValidationsForRevokeTokenRequest() {
        return Arrays.asList(
                new ValidationConfig(CLIENT_ID, Collections.singletonList(ValidationType.ID)),
                new ValidationConfig(CLIENT_SECRET, Collections.singletonList(ValidationType.NON_EMPTY_STRING)),
                new ValidationConfig(TOKEN, Collections.singletonList(ValidationType.NON_EMPTY_STRING)),
                new ValidationConfig(TOKEN_TYPE_HINT, Collections.singletonList(ValidationType.NON_EMPTY_STRING))
        );
    }
}
