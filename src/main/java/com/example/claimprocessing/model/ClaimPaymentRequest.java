package com.example.claimprocessing.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record ClaimPaymentRequest(
        @NotBlank(message = "claimId is required") String claimId,
        @NotBlank(message = "memberId is required") String memberId,
        @NotNull(message = "amount is required") @DecimalMin(value = "0.01", message = "amount must be > 0") BigDecimal amount,
        @NotBlank(message = "currency is required")
        @Pattern(regexp = "^[A-Za-z]{3}$", message = "currency must be a 3-letter ISO code")
        String currency
) {
}
