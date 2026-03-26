package com.nexa.exception.handler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import com.nexa.exception.ApiException;
import com.nexa.exception.api.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiError> handleApi(ApiException exception) {
    int status = exception.getStatus().value();

    return ResponseEntity.status(status)
        .body(new ApiError(exception.getMessage(), status, Instant.now(), null));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException exception) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
      errors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return ResponseEntity.badRequest()
        .body(
            new ApiError(
                "Validation failed", HttpStatus.BAD_REQUEST.value(), Instant.now(), errors));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ApiError> handleAuth(AuthenticationException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ApiError("Unauthorized", 401, Instant.now(), null));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiError> handleForbidden(Exception ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(new ApiError("Access denied", 403, Instant.now(), null));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleUnknown(Exception exception) {
    log.error("Unexpected system error", exception);
    return ResponseEntity.status(500)
        .body(
            new ApiError(
                "Internal server error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Instant.now(),
                null));
  }
}
