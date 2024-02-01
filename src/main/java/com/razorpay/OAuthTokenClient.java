package com.razorpay;

import com.razorpay.dto.AuthURLRequestDTO;
import com.razorpay.dto.GetAccessTokenViaAuthCodeRequestDTO;
import com.razorpay.dto.GetAccessTokenViaRefreshTokenRequestDTO;
import com.razorpay.dto.RevokeTokenRequestDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class OAuthTokenClient extends ApiClient {

    OAuthTokenClient(String auth) {
        super(auth);
    }

    public String getAuthURL(JSONObject request) throws RazorpayException {
        AuthURLRequestDTO authURLRequestDTO = convertToAuthRequestDTO(request);
        validateAuthRequestDTO(authURLRequestDTO);

        String clientId = request.getString("client_id");
        String redirectUri = request.getString("redirect_uri");
        String state = request.getString("state");
        String[] scopes = jsonArrayToStringArray(request.getJSONArray("scopes"));

        String scopesArray = (scopes != null && scopes.length > 0) ?
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

    public OAuthToken getAccessToken(JSONObject request) throws RazorpayException {
        GetAccessTokenViaAuthCodeRequestDTO getAccessTokenViaAuthCodeRequestDTO = convertToGetAccessTokenViaAuthCodeRequestDTO(request);
        validateGetAccessTokenViaAuthCodeRequestDTO(getAccessTokenViaAuthCodeRequestDTO);
        return post(null, Constants.TOKEN, request, Constants.AUTH);
    }

    public OAuthToken getAccessTokenFromRefreshToken(JSONObject request) throws RazorpayException {
        GetAccessTokenViaRefreshTokenRequestDTO getAccessTokenViaRefreshTokenRequestDTO = convertToGetAccessTokenViaRefreshTokenRequestDTO(request);
        validateGetAccessTokenViaRefreshTokenRequestDTO(getAccessTokenViaRefreshTokenRequestDTO);

        request.put("grant_type", "refresh_token");
        return post(null, Constants.TOKEN, request, Constants.AUTH);
    }

    public OAuthToken revokeToken(JSONObject request) throws RazorpayException {
        RevokeTokenRequestDTO revokeTokenRequestDTO = convertToRevokeTokenRequestDTORequestDTO(request);
        validateRevokeTokenRequestDTO(revokeTokenRequestDTO);

        return patch(null, Constants.REVOKE, request, Constants.AUTH);
    }

    private AuthURLRequestDTO convertToAuthRequestDTO(JSONObject request) {
        return new AuthURLRequestDTO(
                request.getString("client_id"),
                request.getString("redirect_uri"),
                jsonArrayToStringArray(request.getJSONArray("scopes")),
                request.getString("state")
        );
    }

    private void validateAuthRequestDTO(AuthURLRequestDTO authRequestDTO) throws RazorpayException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AuthURLRequestDTO>> violations = validator.validate(authRequestDTO);
        if (!violations.isEmpty()) {
            throw new RazorpayException("Invalid request parameters");
        }
    }

    private GetAccessTokenViaAuthCodeRequestDTO convertToGetAccessTokenViaAuthCodeRequestDTO(JSONObject request) {
        return new GetAccessTokenViaAuthCodeRequestDTO(
                request.getString("client_id"),
                request.getString("client_secret"),
                request.getString("redirect_uri"),
                request.getString("code"),
                request.getString("mode")
        );
    }

    private void validateGetAccessTokenViaAuthCodeRequestDTO(GetAccessTokenViaAuthCodeRequestDTO getAccessTokenViaAuthCodeRequestDTO) throws RazorpayException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<GetAccessTokenViaAuthCodeRequestDTO>> violations = validator.validate(getAccessTokenViaAuthCodeRequestDTO);
        if (!violations.isEmpty()) {
            throw new RazorpayException("Invalid request parameters");
        }
    }

    private GetAccessTokenViaRefreshTokenRequestDTO convertToGetAccessTokenViaRefreshTokenRequestDTO(JSONObject request) {
        return new GetAccessTokenViaRefreshTokenRequestDTO(
                request.getString("client_id"),
                request.getString("client_secret"),
                request.getString("refresh_token")
        );
    }

    private void validateGetAccessTokenViaRefreshTokenRequestDTO(GetAccessTokenViaRefreshTokenRequestDTO getAccessTokenViaRefreshTokenRequestDTO) throws RazorpayException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<GetAccessTokenViaRefreshTokenRequestDTO>> violations = validator.validate(getAccessTokenViaRefreshTokenRequestDTO);
        if (!violations.isEmpty()) {
            throw new RazorpayException("Invalid request parameters");
        }
    }

    private RevokeTokenRequestDTO convertToRevokeTokenRequestDTORequestDTO(JSONObject request) {
        return new RevokeTokenRequestDTO(
                request.getString("client_id"),
                request.getString("client_secret"),
                request.getString("token"),
                request.getString("token_type_hint")
        );
    }

    private void validateRevokeTokenRequestDTO(RevokeTokenRequestDTO revokeTokenRequestDTO) throws RazorpayException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<RevokeTokenRequestDTO>> violations = validator.validate(revokeTokenRequestDTO);
        if (!violations.isEmpty()) {
            throw new RazorpayException("Invalid request parameters");
        }
    }

    private String[] jsonArrayToStringArray(JSONArray jsonArray) {
        String[] array = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            array[i] = jsonArray.getString(i);
        }
        return array;
    }
}
