package com.razorpay;

import org.json.JSONArray;
import org.json.JSONObject;

public class OAuthTokenClient extends ApiClient {

    OAuthTokenClient(String auth) {
        super(auth);
    }

    public String getAuthURL(JSONObject request) throws RazorpayException {
        String clientId = request.getString("client_id");
        String redirectUri = request.getString("redirect_uri");
        String state = request.getString("state");
        String[] scopes = jsonArrayToStringArray(request.getJSONArray("scopes"));

        String scopesArray = scopes.length > 0 ?
                "&scope[]=" + String.join("&scope[]=", scopes) : "";

        return "https://"
                + Constants.AUTH_HOSTNAME
                + "/" + Constants.AUTHORIZE
                + "?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + scopesArray
                + "&state=" + state;
    }

    public OAuthToken getAccessToken(JSONObject request) throws RazorpayException {
        return post(null, Constants.TOKEN, request, Constants.AUTH);
    }

    public OAuthToken getAccessTokenFromRefreshToken(JSONObject request) throws RazorpayException {
        request.put("grant_type", "refresh_token");
        return post(null, Constants.TOKEN, request, Constants.AUTH);
    }

    public OAuthToken revokeToken(JSONObject request) throws RazorpayException {
        return patch(null, Constants.REVOKE, request, Constants.AUTH);
    }

    private String[] jsonArrayToStringArray(JSONArray jsonArray) {
        String[] array = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            array[i] = jsonArray.getString(i);
        }
        return array;
    }
}
