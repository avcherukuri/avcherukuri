package com.example.claimprocessing.controller;

import java.util.List;

public record ValidationErrorResponse(String message, List<String> errors) {
}
