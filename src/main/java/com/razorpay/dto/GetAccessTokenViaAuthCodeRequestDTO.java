package com.razorpay.dto;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;

public class GetAccessTokenViaAuthCodeRequestDTO {

    public static final String ID_PATTERN = "[A-Za-z0-9]{1,14}";

    @NotNull
    @Size(max = 14, message = "Invalid client_id")
    @Pattern(regexp = ID_PATTERN, message = "Invalid client_id")
    private String client_id;

    @NotNull
    private String client_secret;

    @NotNull
    @URL(message = "Invalid redirect_uri")
    private String redirect_uri;

    @NotNull
    private String code;

    @NotNull
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
