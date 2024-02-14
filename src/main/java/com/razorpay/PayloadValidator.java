package com.razorpay;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class PayloadValidator {

    public void validate(JSONObject request, List<ValidationConfig> validationConfig) throws RazorpayException {
        for (ValidationConfig config : validationConfig) {
            String fieldName = config.getFieldName();
            for (ValidationType validationType : config.getValidations()) {
                applyValidation(request, fieldName, validationType);
            }
        }
    }

    private void applyValidation(JSONObject payload, String field, ValidationType validationType) throws RazorpayException {
        switch (validationType) {
            case NON_NULL:
                validateNonNull(payload, field);
                break;
            case NON_EMPTY_STRING:
                validateNonEmptyString(payload, field);
                break;
            case URL:
                validateUrl(payload, field);
                break;
            case ID:
                validateID(payload, field);
                break;
            case MODE:
                validateMode(payload, field);
                break;
            case TOKEN_GRANT:
                validateGrantType(payload, field);
            default:
                break;
        }
    }

    private void validateMode(JSONObject payload, String field) throws RazorpayException {
        validateNonNull(payload, field);
        String mode = payload.getString(field);
        if (!Arrays.asList("test", "live").contains(mode)) {
            String errorMessage = "Invalid value provided for field %s";
            throw new RazorpayException(String.format(errorMessage, field));
        }
    }

    private void validateNonNull(JSONObject payload, String field) throws RazorpayException {
        if (!payload.has(field) || payload.isNull(field)) {
            String errorMessage = "Field %s cannot be null";
            throw new RazorpayException(String.format(errorMessage, field));
        }
    }

    private void validateNonEmptyString(JSONObject payload, String field) throws RazorpayException {
        if (payload.optString(field).trim().isEmpty()) {
            String errorMessage = "Field %s cannot be empty";
            throw new RazorpayException(String.format(errorMessage, field));
        }
    }

    private void validateUrl(JSONObject payload, String field) throws RazorpayException {
        String url = payload.optString(field);
        String urlRegex = "^(http[s]?):\\/\\/[^\\s/$.?#].[^\\s]*$";
        if (!url.matches(urlRegex)) {
            String errorMessage = "Field %s is not a valid URL";
            throw new RazorpayException(String.format(errorMessage, field));
        }
    }

    private void validateID(JSONObject payload, String field) throws RazorpayException {
        validateNonNull(payload, field);
        validateNonEmptyString(payload, field);
        String value = payload.getString(field);
        String idRegex = "[A-Za-z0-9]{1,14}";
        if (!value.matches(idRegex)) {
            String errorMessage = "Field %s is not a valid ID";
            throw new RazorpayException(String.format(errorMessage, field));
        }
    }

    private void validateGrantType(JSONObject payload, String field) throws RazorpayException {
        validateNonNull(payload, field);
        String grantType = payload.getString(field);
        switch (grantType)
        {
            case "authorization_code":
                validateNonNull(payload, "code");
                break;
            case "refresh_token":
                validateNonNull(payload, "refresh_token");
                break;
            default:
                break;
        }
    }
}
