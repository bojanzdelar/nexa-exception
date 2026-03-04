package com.nexa.exception;

import java.time.Instant;
import java.util.Map;

public record ApiError(String message, int status, Instant timestamp, Map<String, String> errors) {}
