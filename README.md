# Comprehensive AQA Framework - test_framwork_example (v1.0-SNAPSHOT)

A modern, scalable Automated Quality Assurance framework combining API and UI testing capabilities. This project
showcases best practices for test automation framework architecture, with a focus on maintainability, readability, and
extensibility.

## Framework Overview

This framework represents a collection of best practices and approaches from professional QA automation experience,
designed to provide:

- **Comprehensive Testing**: Combined API and UI test automation in a single framework
- **Modular Architecture**: Clean separation of concerns with service, model, and test layers
- **Flexible Configuration**: Environment-specific settings with property files
- **Robust Reporting**: Integrated reporting with Allure and TestRail
- **Containerization**: Isolated testing environments with Docker and TestContainers
- **CI/CD Integration**: Ready-to-use GitHub Actions workflows
- **Data Management**: Realistic test data generation and management

## Key Features

### API Testing

- **REST Assured Integration**: Fluent API for HTTP requests and responses
- **Custom Assertions**: Domain-specific assertions for readable validations
- **Contract Testing**: Verify API contracts and schemas (planned)
- **Performance Testing**: Basic load and performance testing capabilities (planned)

### UI Testing (planned)

- **Selenide Implementation**: Concise and stable UI test automation (planned)
- **Page Object Pattern**: Maintainable page abstractions (planned)
- **Cross-Browser Testing**: Support for multiple browsers (planned)
- **Playwright Integration**: Modern browser automation (planned)
- **Visual Testing**: Screenshot comparison capabilities (planned)

### Framework Infrastructure

- **TestRail Integration**: Automatic test case synchronization and result reporting
- **Allure Reporting**: Rich test execution reports with screenshots and logs
- **Docker Support**: Containerized test environments
- **Parallel Execution**: Optimized test execution speed
- **Data Factories**: Flexible test data generation
- **Configuration Management**: Environment-specific settings

## Tech Stack

- Java 17
- Gradle 8.5+
- TestNG 7.9.0
- REST Assured 5.4.0
- Selenide 6.x (planned)
- Allure 2.25.0
- Logback 1.4.14
- TestContainers 1.19.7
- JavaFaker 1.0.2
- Playwright (planned)

## Project Structure

```
.
├── common/                      # Common utilities and shared code
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/github/artbi/common/
│   │   │   │       ├── assertions/     # Custom assertion classes
│   │   │   │       ├── conditions/     # Test conditions
│   │   │   │       ├── config/         # Base configuration including TestRail config
│   │   │   │       ├── service/        # Base service classes including TestRail service
│   │   │   │       └── listeners/      # TestNG listeners including TestRail listener
├── api-tests/                   # API testing module
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/github/artbi/api/
│   │   │   │       ├── model/          # API data models
│   │   │   │       ├── service/        # API service clients
│   │   │   │       └── config/         # API configuration
│   │   │   └── resources/
│   │   │       ├── config.properties   # Configuration properties
│   │   │       └── logback.xml         # Logging configuration
│   │   └── test/
│   │       ├── java/
│   │       │   └── com/github/artbi/api/
│   │       │       ├── containers/     # TestContainers setup
│   │       │       └── tests/          # API test classes
│   │       └── resources/
│   │           └── testng.xml          # TestNG configuration
├── ui-tests/                    # UI testing module (planned)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/github/artbi/ui/
│   │   │   │       ├── pages/          # Page objects
│   │   │   │       ├── components/     # Reusable UI components
│   │   │   │       └── config/         # UI configuration
│   │   └── test/
│   │       ├── java/
│   │       │   └── com/github/artbi/ui/
│   │       │       └── tests/          # UI test classes
│   │       └── resources/
│   │           └── testng.xml          # TestNG configuration
└── docs/                        # Documentation
```

## Prerequisites

- Java 17 or higher
- Gradle 8.5 or higher
- Docker (for TestContainers)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/artbi/test_framwork_example.git
cd test_framwork_example
```

### Available Documentation

This directory contains documentation for various aspects of the project:

- [API Testing](docs/api.md) - Documentation for API testing framework
- [Environment Variables Usage](docs/env-usage.md) - Guide for using .env files for local secrets
- [GitHub Actions](docs/github-actions.md) - Information about CI/CD with GitHub Actions
- [Spotless Code Formatting](docs/spotless.md) - Guide for using Spotless for code formatting
- [TestRail Integration](docs/testrail-integration.md) - Guide for using TestRail integration for test reporting

### Environment Setup

1. Configure `config.properties` for environment-specific settings:

```properties
testcontainers.enabled=true
testcontainers.reuse.enable=true
api.base.url=http://localhost:8080/api/v3
logging.enabled=true
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

This project uses Allure for comprehensive test reporting. Allure provides rich visual reports with detailed test
execution information, including:

- Test execution timeline
- Test suite and case breakdown
- Detailed failure information with screenshots and logs
- Environment and configuration details
- Custom attachments and steps

#### Generate HTML Report

To generate an Allure HTML report after running tests:

```bash
./gradlew allureReport
```

This creates a report in `build/allure-report`. Open `index.html` in this directory to view the report in your browser.

#### Serve Report Locally

To generate and automatically open the report in your default browser:

```bash
./gradlew allureServe
```

This starts a local web server and opens the report in your browser.

## TestRail Integration

The framework includes integration with TestRail for test case management and reporting:

- Automatic test result reporting to TestRail
- Test case mapping using `@TmsLink` annotations
- Custom TestNG listeners for TestRail integration
- Configurable through properties or environment variables

To configure TestRail integration:

1. Add TestRail credentials to your environment or properties:

```properties
testrail.enabled=true
testrail.url=https://your-instance.testrail.io
testrail.username=your_username
testrail.apiKey=your_api_key
testrail.projectId=1
testrail.suiteId=1
```

2. Use `@TmsLink` annotations in your test classes:

```java
@Test
@TmsLink("C12345")
public void testExample() {
    // Test code
}
```

For detailed information about TestRail integration, see
the [TestRail Integration Documentation](docs/testrail-integration.md).

## Best Practices Implemented

This framework demonstrates several best practices for test automation:

### Architecture

- **Separation of Concerns**: Clear separation between test data, test logic, and page/API objects
- **Reusable Components**: Common functionality extracted into reusable components
- **Configuration Management**: Externalized configuration for different environments

### Test Design

- **Atomic Tests**: Each test verifies a single piece of functionality
- **Independent Tests**: Tests can run in any order without dependencies
- **Data-Driven Approach**: Tests use dynamic data generation
- **Explicit Waits**: Smart waiting strategies instead of hard-coded sleeps

### Code Quality

- **Code Formatting**: Consistent code style with Spotless
- **Logging**: Comprehensive logging for troubleshooting
- **Documentation**: Well-documented code and framework usage
- **Version Control**: Proper branching and commit strategies

## Roadmap

### Short-term Goals

- [x] Implement Allure reporting
- [x] Implement common subproject structure
- [x] Implement Spotless code formatting
- [x] Implement TestContainers support
- [ ] Complete API testing framework for all endpoints
- [ ] Implement data-driven testing with external data sources
- [ ] Add contract testing capabilities
- [ ] Enhance error handling and reporting

### Medium-term Goals

- [ ] Add UI testing module using Selenide
- [x] Implement parallel test execution with improved reporting
- [ ] Add performance testing capabilities
- [x] Integrate with CI/CD pipelines (GitHub Actions)
- [ ] Add publishing Allure report as GitHub Pages (GitHub Actions)
- [x] Implement TestRail integration

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
