package com.razorpay.dto;

public class GetAccessTokenViaRefreshTokenRequestDTO {
    public static final String ID_PATTERN = "[A-Za-z0-9]{1,14}";

    private String client_id;

    private String client_secret;
    private String refresh_token;

    public GetAccessTokenViaRefreshTokenRequestDTO(String client_id, String client_secret, String refresh_token) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.refresh_token = refresh_token;
    }
}
