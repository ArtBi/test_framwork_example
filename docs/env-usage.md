# Using .env Files for Local Secrets

## Overview

Using `.env` files is a common practice for managing sensitive information like API keys, database credentials, and
other secrets locally without committing them to version control. This guide explains how to implement and use `.env`
files in this project.

## Implementation Steps

### 1. Add a .env-file library dependency

First, add a library that supports .env files. For Java
projects, [dotenv-java](https://github.com/cdimascio/dotenv-java) is a popular choice:

```gradle
// In build.gradle
dependencies {
    implementation 'io.github.cdimascio:dotenv-java:2.3.2'
}
```

### 2. Create a .env file

Create a `.env` file in your project root:

```
# .env
API_KEY=your_secret_api_key
DB_PASSWORD=your_database_password
API_BASE_URL=https://api.example.com
```

### 3. Add .env to .gitignore

Make sure to add `.env` to your `.gitignore` file to prevent committing secrets:

```
# .gitignore
.env
```

### 4. Create a .env.example file

Create a `.env.example` file with the structure but without actual secrets:

```
# .env.example
API_KEY=
DB_PASSWORD=
API_BASE_URL=
```

This file can be committed to the repository to show other developers what environment variables are needed.

### 5. Modify ProjectConfig to load from .env

Update your `ProjectConfig` class to load from both `.env` and `config.properties`:

```java
package com.petclinic.api;

import io.github.cdimascio.dotenv.Dotenv;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

import java.util.Properties;

@Config.Sources({"classpath:config.properties"})
public interface ProjectConfig extends Config {

    static ProjectConfig get() {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        // Create properties with values from .env
        Properties properties = new Properties();
        properties.putAll(System.getProperties());

        // Add other secrets as needed
        if (dotenv.get("API_KEY") != null) {
            properties.setProperty("api.key", dotenv.get("API_KEY"));
        }

        return ConfigFactory.create(ProjectConfig.class, properties);
    }

    @Key("api.base.url")
    @DefaultValue("http://localhost:8080/api/v3")
    String apiBaseUrl();

    @Key("logging.enabled")
    @DefaultValue("true")
    boolean loggingEnabled();

    @Key("api.key")
    @DefaultValue("")
    String apiKey();
}
```

## Using secrets in your code

Now you can access the secrets in your code:

```java
// In BaseApiService.java
private RequestSpecification setup() {
    RequestSpecification spec = RestAssured.given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON);

    // Add API key if available
    String apiKey = ProjectConfig.get().apiKey();
    if (!apiKey.isEmpty()) {
        spec.header("X-API-Key", apiKey);
    }

    return spec.filters(getFilters());
}
```

## Priority Order

With this implementation, configuration values are loaded with the following priority:

1. System properties (highest priority)
2. Environment variables from `.env` file
3. Values from `config.properties`
4. Default values specified in the `ProjectConfig` interface (lowest priority)

## Benefits

- Keeps sensitive information out of version control
- Makes it easy to use different configurations in different environments
- Provides a clear structure for required environment variables
- Works well with CI/CD pipelines where secrets can be injected as environment variables

## Additional Tips

- For production environments, use environment variables or a secure secrets manager instead of `.env` files
- Consider encrypting sensitive values in your `.env` file for additional security
- Use different `.env` files for different environments (e.g., `.env.dev`, `.env.test`)
