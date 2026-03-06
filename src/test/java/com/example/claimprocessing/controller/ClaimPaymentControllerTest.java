package com.example.claimprocessing.controller;

import com.example.claimprocessing.model.ClaimPaymentRequest;
import com.example.claimprocessing.service.ClaimPaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClaimPaymentController.class)
@Import(ClaimPaymentService.class)
class ClaimPaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateClaimPayment() throws Exception {
        ClaimPaymentRequest request = new ClaimPaymentRequest("C100", "M10", new BigDecimal("101.10"), "USD");

        mockMvc.perform(post("/api/v1/claims/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.claimId").value("C100"))
                .andExpect(jsonPath("$.status").value("PENDING_PAYMENT"));
    }

    @Test
    void shouldRejectInvalidCurrencyCode() throws Exception {
        ClaimPaymentRequest request = new ClaimPaymentRequest("C100", "M10", new BigDecimal("101.10"), "US");

        mockMvc.perform(post("/api/v1/claims/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    @Test
    void shouldRejectInvalidBatchSize() throws Exception {
        mockMvc.perform(post("/api/v1/claims/payments/batch")
                        .param("batchSize", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

}
