package com.example.claimprocessing.model;

public record BatchResult(int processedCount, int paidCount, int rejectedCount) {
}
