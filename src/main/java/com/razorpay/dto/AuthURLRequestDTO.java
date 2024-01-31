package com.razorpay.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AuthURLRequestDTO {

    public static final String ID_PATTERN = "[A-Za-z0-9]{1,14}";

    @NotNull
    @Size(max = 14, message = "Invalid client_id")
    @Pattern(regexp = ID_PATTERN, message = "Invalid client_id")
    private String client_id;

    @NotNull
    private String redirect_uri;

    @NotNull
    @Size(min = 1, max = 10, message = "Number of scopes passed must be between 1 and 10")
    private String[] scopes;

    @NotNull
    private String state;

    public AuthURLRequestDTO(String client_id, String redirect_uri, String[] scopes, String state) {
        this.client_id = client_id;
        this.redirect_uri = redirect_uri;
        this.scopes = scopes;
        this.state = state;
    }
}

