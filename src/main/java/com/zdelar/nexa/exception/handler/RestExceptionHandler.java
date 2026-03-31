package com.zdelar.nexa.exception.handler;

import com.zdelar.nexa.exception.ApiException;
import com.zdelar.nexa.exception.api.ApiError;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiError> handleApi(ApiException ex) {
    int status = ex.getStatus().value();

    return ResponseEntity.status(status)
        .body(new ApiError(ex.getMessage(), status, Instant.now(), null));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      errors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return ResponseEntity.badRequest()
        .body(
            new ApiError(
                "Validation failed", HttpStatus.BAD_REQUEST.value(), Instant.now(), errors));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    return ResponseEntity.badRequest()
        .body(
            new ApiError(
                "Invalid parameter type",
                HttpStatus.BAD_REQUEST.value(),
                Instant.now(),
                Map.of(
                    "parameter", ex.getName(),
                    "value", String.valueOf(ex.getValue()),
                    "expectedType",
                        ex.getRequiredType() != null
                            ? ex.getRequiredType().getSimpleName()
                            : "unknown")));
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ApiError> handleMissingParam(MissingServletRequestParameterException ex) {
    return ResponseEntity.badRequest()
        .body(
            new ApiError(
                "Missing request parameter",
                HttpStatus.BAD_REQUEST.value(),
                Instant.now(),
                Map.of("parameter", ex.getParameterName())));
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(NoResourceFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ApiError("Endpoint not found", 404, Instant.now(), null));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleUnknown(Exception ex) {
    log.error("Unexpected system error", ex);
    return ResponseEntity.status(500)
        .body(
            new ApiError(
                "Internal server error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Instant.now(),
                null));
  }
}
