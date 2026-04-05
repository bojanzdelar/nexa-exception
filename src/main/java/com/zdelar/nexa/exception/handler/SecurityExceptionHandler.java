package com.zdelar.nexa.exception.handler;

import com.zdelar.nexa.exception.api.ApiError;
import java.time.Instant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ConditionalOnClass(name = "org.springframework.security.core.AuthenticationException")
@RestControllerAdvice
public class SecurityExceptionHandler {

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ApiError> handleAuth(AuthenticationException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new ApiError("Unauthorized", 401, Instant.now(), null));
  }

  @ExceptionHandler({AccessDeniedException.class, AuthorizationDeniedException.class})
  public ResponseEntity<ApiError> handleForbidden(Exception ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(new ApiError("Access denied", 403, Instant.now(), null));
  }
}
