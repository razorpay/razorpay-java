package com.razorpay.dto;

public class AuthURLRequestDTO {

    public static final String ID_PATTERN = "[A-Za-z0-9]{1,14}";

    private String client_id;

    private String redirect_uri;

    private String[] scopes;

    private String state;

    public AuthURLRequestDTO(String client_id, String redirect_uri, String[] scopes, String state) {
        this.client_id = client_id;
        this.redirect_uri = redirect_uri;
        this.scopes = scopes;
        this.state = state;
    }
}

