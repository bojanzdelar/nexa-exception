package com.zdelar.nexa.exception.config;

import com.zdelar.nexa.exception.handler.ApiAccessDeniedHandler;
import com.zdelar.nexa.exception.handler.ApiAuthenticationEntryPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@ConditionalOnClass(HttpSecurity.class)
public class SecurityExceptionConfig {

  @Bean
  public SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>
      securityExceptionConfigurer(
          ApiAuthenticationEntryPoint entryPoint, ApiAccessDeniedHandler accessDeniedHandler) {
    return new SecurityConfigurerAdapter<>() {
      @Override
      public void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling(
            ex -> ex.authenticationEntryPoint(entryPoint).accessDeniedHandler(accessDeniedHandler));
      }
    };
  }
}
