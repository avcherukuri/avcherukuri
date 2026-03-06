package com.example.claimprocessing.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class ClaimPayment {
    private final String paymentId;
    private final String claimId;
    private final String memberId;
    private final BigDecimal amount;
    private final String currency;
    private ClaimStatus status;
    private final Instant createdAt;
    private Instant paidAt;

    public ClaimPayment(String claimId, String memberId, BigDecimal amount, String currency) {
        this.paymentId = UUID.randomUUID().toString();
        this.claimId = claimId;
        this.memberId = memberId;
        this.amount = amount;
        this.currency = currency;
        this.status = ClaimStatus.PENDING_PAYMENT;
        this.createdAt = Instant.now();
    }

    public String getPaymentId() { return paymentId; }
    public String getClaimId() { return claimId; }
    public String getMemberId() { return memberId; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public ClaimStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getPaidAt() { return paidAt; }

    public void markPaid() {
        this.status = ClaimStatus.PAID;
        this.paidAt = Instant.now();
    }

    public void reject() {
        this.status = ClaimStatus.REJECTED;
    }
}
