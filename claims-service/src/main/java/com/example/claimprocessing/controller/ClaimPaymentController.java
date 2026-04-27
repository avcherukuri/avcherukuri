package com.example.claimprocessing.controller;

import com.example.claimprocessing.model.BatchResult;
import com.example.claimprocessing.model.ClaimPayment;
import com.example.claimprocessing.model.ClaimPaymentRequest;
import com.example.claimprocessing.service.ClaimPaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/claims")
public class ClaimPaymentController {
    private final ClaimPaymentService claimPaymentService;

    public ClaimPaymentController(ClaimPaymentService claimPaymentService) {
        this.claimPaymentService = claimPaymentService;
    }

    @PostMapping("/payments")
    @ResponseStatus(HttpStatus.CREATED)
    public ClaimPayment submit(@Valid @RequestBody ClaimPaymentRequest request) {
        return claimPaymentService.submitClaimPayment(request);
    }

    @GetMapping("/payments")
    public List<ClaimPayment> list() {
        return claimPaymentService.getAll();
    }

    @PostMapping("/payments/batch")
    public BatchResult processBatch(@RequestParam(defaultValue = "20") @Min(value = 1, message = "batchSize must be at least 1") int batchSize) {
        return claimPaymentService.processPendingPayments(batchSize);
    }
}
