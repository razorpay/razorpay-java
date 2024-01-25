package com.razorpay.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;

public class RevokeTokenRequestDTO {

    public static final String ID_PATTERN = "[A-Za-z0-9]{1,14}";

    @NotNull
    @Size(max = 14, message = "Invalid client_id")
    @Pattern(regexp = ID_PATTERN, message = "Invalid client_id")
    private String client_id;

    @NotNull
    private String client_secret;

    @NotNull
    private String token;

    @NotNull
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
