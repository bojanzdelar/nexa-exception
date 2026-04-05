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
    <version>1.1.2</version>
</dependency>
```

## Spring Security

Spring Security errors are not handled automatically. To return consistent error responses, apply the provided
configurer in your security config:

```java
http.apply(securityExceptionConfigurer);
```

## License

This project is licensed under the **MIT License**.
