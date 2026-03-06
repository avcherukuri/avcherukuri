package com.example.claimprocessing.service;

import com.example.claimprocessing.model.BatchResult;
import com.example.claimprocessing.model.ClaimPayment;
import com.example.claimprocessing.model.ClaimPaymentRequest;
import com.example.claimprocessing.model.ClaimStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClaimPaymentServiceTest {

    @Test
    void shouldPayEligibleAndRejectIneligibleClaims() {
        ClaimPaymentService service = new ClaimPaymentService();
        ClaimPaymentRequest eligible = new ClaimPaymentRequest("C1", "M1", new BigDecimal("500.00"), "usd");
        ClaimPaymentRequest ineligible = new ClaimPaymentRequest("C2", "M2", new BigDecimal("15000.00"), "usd");

        ClaimPayment p1 = service.submitClaimPayment(eligible);
        ClaimPayment p2 = service.submitClaimPayment(ineligible);

        BatchResult result = service.processPendingPayments(10);

        assertEquals(2, result.processedCount());
        assertEquals(1, result.paidCount());
        assertEquals(1, result.rejectedCount());
        assertEquals(ClaimStatus.PAID, service.getAll().stream().filter(p -> p.getPaymentId().equals(p1.getPaymentId())).findFirst().orElseThrow().getStatus());
        assertEquals(ClaimStatus.REJECTED, service.getAll().stream().filter(p -> p.getPaymentId().equals(p2.getPaymentId())).findFirst().orElseThrow().getStatus());
    }
}
