package com.razorpay.dto;

import java.util.Arrays;
import java.util.List;

public class RevokeTokenRequestDTO {

    public static final String ID_PATTERN = "[A-Za-z0-9]{1,14}";

    private String client_id;

    private String client_secret;

    private String token;

    private String token_type_hint;

    public RevokeTokenRequestDTO(String client_id, String client_secret, String token, String token_type_hint) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.token = token;
        this.token_type_hint = token_type_hint;
    }

    public boolean isValidTokenTypeHint() {
        List<String> validModes = Arrays.asList("access_token", "refresh_token");
        return validModes.contains(token_type_hint);
    }
}
