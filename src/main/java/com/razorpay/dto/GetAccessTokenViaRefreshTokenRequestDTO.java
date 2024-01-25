package com.razorpay.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class GetAccessTokenViaRefreshTokenRequestDTO {
    public static final String ID_PATTERN = "[A-Za-z0-9]{1,14}";

    @NotNull
    @Size(max = 14, message = "Invalid client_id")
    @Pattern(regexp = ID_PATTERN, message = "Invalid client_id")
    private String client_id;

    @NotNull
    private String client_secret;

    @NotNull
    private String refresh_token;

    public GetAccessTokenViaRefreshTokenRequestDTO(String client_id, String client_secret, String refresh_token) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.refresh_token = refresh_token;
    }
}
