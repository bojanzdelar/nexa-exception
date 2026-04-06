# Nexa Exception

Shared exception handling library for Nexa Spring Boot RESTful microservices.

## Overview

`nexa-exception` provides a consistent error handling mechanism across Nexa microservices.
It includes base exception classes, a global exception handler, and standardized API error responses.

## Installation

Add the dependency (use the latest available version):

```xml
<dependency>
    <groupId>com.zdelar.nexa</groupId>
    <artifactId>nexa-exception</artifactId>
    <version>1.1.4</version>
</dependency>
```

## Usage

The `RestExceptionHandler` is auto-configured and will handle standard application exceptions out of the box.

### Spring Security

Spring Security errors are not handled automatically. To return consistent error responses, apply the provided
customizer in your security config:

```java
http.exceptionHandling(exceptionHandlingCustomizer);
```

### OAuth2 Resource Server

If using an OAuth2 Resource Server, configure the same handlers explicitly:

```java
http.oauth2ResourceServer(
    oauth2 ->
        oauth2
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler));
```

Without this configuration, default Spring Security responses will be returned instead of standardized API errors.

## License

This project is licensed under the **MIT License**.
