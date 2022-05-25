package com.razorpay.payout.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payout {
    private String id;
    private String entity;
    private String fundAccountId;

    private String amount;
    private String currency;

    private List<String> notes;
    private double fees;
    private double tax;
    private String status;
    private String purpose;
    private String utr;
    private String mode;
    private String referenceId;
    private String narration;
    private String batchId;
    private String failureReason;
    private long createdAt;
    private String feeType;
    private long scheduledAt;
    private StatusDetails statusDetails;
    private String merchantId;
    private String statusDetailsId;
}
