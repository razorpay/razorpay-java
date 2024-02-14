package com.razorpay;

import java.util.List;

public class ValidationConfig {

    private final String fieldName;

    private final List<ValidationType> validations;

    public ValidationConfig(String fieldName, List<ValidationType> validations) {
        this.fieldName = fieldName;
        this.validations = validations;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public List<ValidationType> getValidations() {
        return this.validations;
    }
}
