# Nexa Exception

Shared exception handling library for Spring Boot RESTful microservices.

## Overview

`nexa-exception` provides a consistent error handling mechanism across Nexa microservices.
It includes base exception classes, a global exception handler, and standardized API error responses.

## Installation

Add the GitHub Packages repository:

```xml
<repositories>
  <repository>
    <id>github</id>
    <url>https://maven.pkg.github.com/bojanzdelar/nexa-exception</url>
  </repository>
</repositories>
```

Add the dependency (use the latest available version):

```xml
<dependency>
  <groupId>com.nexa</groupId>
  <artifactId>exception</artifactId>
  <version>1.1.1</version>
</dependency>
```

## License

This project is licensed under the **MIT License**.
