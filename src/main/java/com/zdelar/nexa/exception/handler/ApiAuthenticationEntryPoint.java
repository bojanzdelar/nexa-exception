package com.zdelar.nexa.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdelar.nexa.exception.api.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  public ApiAuthenticationEntryPoint(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void commence(
          HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
      throws IOException {

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType("application/json");

    ApiError error = new ApiError("Unauthorized", 401, Instant.now(), null);
    objectMapper.writeValue(response.getOutputStream(), error);
  }
}
