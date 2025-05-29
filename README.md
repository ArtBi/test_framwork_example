# API Testing Framework Template

A comprehensive template for API testing using Java, REST Assured, and TestNG. This project provides a structured
approach to API testing with a focus on the Swagger Petstore API as an example.

## Features

- **Modular Architecture**: Clean separation of concerns with service, model, and test layers
- **Fluent Assertions**: Custom assertion mechanism for readable test validations
- **Docker Integration**: TestContainers support for isolated testing environments
- **Configurable**: Environment-specific configuration with property files and .env support
- **Reporting**: Allure reports for comprehensive test results visualization
- **Logging**: Detailed logging with Logback
- **Data Generation**: Realistic test data generation with JavaFaker

## Tech Stack

- Java 17
- Gradle 8.5+
- TestNG 7.9.0
- REST Assured 5.4.0
- Allure 2.25.0
- Logback 1.4.14
- TestContainers 1.19.7
- JavaFaker 1.0.2

## Project Structure

```
.
├── automation/
│   ├── common/                # Common utilities and shared code
│   └── petstore-api-tests/    # API tests for Petstore API
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/
│       │   │   │   └── com/petclinic/api/
│       │   │   │       ├── assertions/     # Custom assertion classes
│       │   │   │       ├── conditions/     # Test conditions
│       │   │   │       ├── model/          # Data models and DTOs
│       │   │   │       ├── service/        # API service clients
│       │   │   │       └── ProjectConfig.java  # Configuration
│       │   │   └── resources/
│       │   │       ├── config.properties   # Configuration properties
│       │   │       └── logback.xml         # Logging configuration
│       │   └── test/
│       │       ├── java/
│       │       │   └── com/petclinic/api/
│       │       │       ├── containers/     # TestContainers setup
│       │       │       └── tests/          # Test classes
│       │       └── resources/
│       │           └── testng.xml          # TestNG configuration
└── docs/                      # Documentation
```

## Prerequisites

- Java 17 or higher
- Gradle 8.5 or higher
- Docker (for TestContainers)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/yourusername/api-testing-framework.git
cd api-testing-framework
```

### Environment Setup

1. Create a `.env` file in the project root (optional, for local secrets):

```
API_KEY=your_secret_api_key
API_BASE_URL=http://custom-api-url.com/api/v3
```

2. Configure `config.properties` for environment-specific settings:

```properties
testcontainers.enabled=true
testcontainers.reuse.enable=true
api.base.url=http://localhost:8080/api/v3
logging.enabled=true
locale=eng
```

## Running Tests

### Basic Test Execution

Run all tests with default settings:

```bash
./gradlew clean test
```

### Running with Specific Parameters

Disable logging for cleaner output:

```bash
./gradlew clean test -Dlogging.enabled=false
```

Enable TestContainers for isolated testing:

```bash
./gradlew clean test -Dtestcontainers.enabled=true
```

Custom API base URL:

```bash
./gradlew clean test -Dapi.base.url=http://your-api-url.com/api/v3
```

Combined parameters:

```bash
./gradlew clean test -Dlogging.enabled=false -Dtestcontainers.enabled=true
```

### Generating Reports

Generate and open Allure reports:

```bash
./gradlew allureReport
./gradlew allureServe
```

## Configuration Options

| Parameter                     | Description                              | Default Value                |
|-------------------------------|------------------------------------------|------------------------------|
| `api.base.url`                | Base URL for the API                     | http://localhost:8080/api/v3 |
| `logging.enabled`             | Enable/disable request/response logging  | true                         |
| `testcontainers.enabled`      | Enable/disable TestContainers            | true                         |
| `testcontainers.reuse.enable` | Enable container reuse between test runs | true                         |
| `locale`                      | Locale for test data generation          | eng                          |

## Using .env for Local Secrets

This project supports using `.env` files for managing sensitive information locally:

1. Add the dotenv-java dependency to your project
2. Create a `.env` file in your project root
3. Add `.env` to your `.gitignore` file
4. Create a `.env.example` file with the structure but without actual secrets
5. Update `ProjectConfig` to load from both `.env` and `config.properties`

For more details, see the [.env usage guide](docs/env-usage.md).

## Roadmap

### Short-term Goals

- [ ] Add more comprehensive test coverage for all Petstore API endpoints
- [ ] Implement data-driven testing with CSV/Excel data sources
- [ ] Add contract testing capabilities
- [ ] Enhance error handling and reporting

### Medium-term Goals

- [ ] Add UI testing module using Selenide
- [ ] Implement parallel test execution with improved reporting
- [ ] Add performance testing capabilities
- [ ] Integrate with CI/CD pipelines (GitHub Actions, Jenkins)

### Long-term Goals

- [ ] Add Playwright for cross-browser UI testing
- [ ] Implement visual testing capabilities
- [ ] Add mobile testing integration
- [ ] Develop comprehensive reporting dashboard

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
