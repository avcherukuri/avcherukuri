package com.example.claimprocessing.service;

import com.example.claimprocessing.model.BatchResult;
import com.example.claimprocessing.model.ClaimPayment;
import com.example.claimprocessing.model.ClaimPaymentRequest;
import com.example.claimprocessing.model.ClaimStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ClaimPaymentService {
    private static final Logger log = LoggerFactory.getLogger(ClaimPaymentService.class);

    private final Map<String, ClaimPayment> payments = new ConcurrentHashMap<>();

    public ClaimPayment submitClaimPayment(ClaimPaymentRequest request) {
        ClaimPayment payment = new ClaimPayment(
                request.claimId(),
                request.memberId(),
                request.amount(),
                request.currency().toUpperCase()
        );
        payments.put(payment.getPaymentId(), payment);
        return payment;
    }

    public List<ClaimPayment> getAll() {
        return payments.values().stream()
                .sorted(Comparator.comparing(ClaimPayment::getCreatedAt).reversed())
                .toList();
    }

    public BatchResult processPendingPayments(int batchSize) {
        List<ClaimPayment> pending = payments.values().stream()
                .filter(p -> p.getStatus() == ClaimStatus.PENDING_PAYMENT)
                .limit(batchSize)
                .toList();

        int paid = 0;
        int rejected = 0;

        for (ClaimPayment payment : pending) {
            if (isEligibleForPayment(payment)) {
                payment.markPaid();
                paid++;
            } else {
                payment.reject();
                rejected++;
            }
        }

        return new BatchResult(pending.size(), paid, rejected);
    }

    @Scheduled(cron = "${claims.batch.cron:0 */2 * * * *}")
    public void runScheduledBatch() {
        BatchResult result = processPendingPayments(20);
        if (result.processedCount() > 0) {
            log.info("Scheduled claim batch processed={} paid={} rejected={}",
                    result.processedCount(), result.paidCount(), result.rejectedCount());
        }
    }

    private boolean isEligibleForPayment(ClaimPayment payment) {
        return payment.getAmount().compareTo(new BigDecimal("10000.00")) <= 0;
    }
}
