package com.zdelar.nexa.exception.config;

import com.zdelar.nexa.exception.handler.ApiAccessDeniedHandler;
import com.zdelar.nexa.exception.handler.ApiAuthenticationEntryPoint;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;

@AutoConfiguration
@ConditionalOnClass(HttpSecurity.class)
public class SecurityExceptionAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public Customizer<ExceptionHandlingConfigurer<HttpSecurity>> securityExceptionConfigurer(
      ApiAuthenticationEntryPoint entryPoint, ApiAccessDeniedHandler accessDeniedHandler) {
    return ex -> ex.authenticationEntryPoint(entryPoint).accessDeniedHandler(accessDeniedHandler);
  }
}
