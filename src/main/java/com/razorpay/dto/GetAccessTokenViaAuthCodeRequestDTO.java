package com.razorpay.dto;

import java.util.Arrays;
import java.util.List;

public class GetAccessTokenViaAuthCodeRequestDTO {

    public static final String ID_PATTERN = "[A-Za-z0-9]{1,14}";

    private String client_id;

    private String client_secret;

    private String redirect_uri;
    private String code;

    private String mode;

    public GetAccessTokenViaAuthCodeRequestDTO(String client_id, String client_secret, String redirect_uri, String code, String mode) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.redirect_uri = redirect_uri;
        this.code = code;
        this.mode = mode;
    }

    public boolean isValidMode() {
        List<String> validModes = Arrays.asList("test", "live");
        return validModes.contains(mode);
    }
}
