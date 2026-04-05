package com.zdelar.nexa.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdelar.nexa.exception.api.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class ApiAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  public ApiAccessDeniedHandler(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void handle(
          HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
      throws IOException {

    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType("application/json");

    ApiError error = new ApiError("Access denied", 403, Instant.now(), null);
    objectMapper.writeValue(response.getOutputStream(), error);
  }
}
