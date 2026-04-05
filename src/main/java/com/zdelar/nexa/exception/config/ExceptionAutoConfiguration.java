package com.zdelar.nexa.exception.config;

import com.zdelar.nexa.exception.handler.RestExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ExceptionAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public RestExceptionHandler restExceptionHandler() {
    return new RestExceptionHandler();
  }
}
