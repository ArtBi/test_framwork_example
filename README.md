# API Testing Framework Template

A comprehensive template for API testing using Java, REST Assured, and TestNG. This project provides a structured
approach to API testing with a focus on the Swagger Petstore API as an example.

## Features

- **Modular Architecture**: Clean separation of concerns with service, model, and test layers
- **Fluent Assertions**: Custom assertion mechanism for readable test validations
- **Docker Integration**: TestContainers support for isolated testing environments
- **Configurable**: Environment-specific configuration with property files
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
git clone https://github.com/artbi/web_api_template.git
cd web_api_template
```

### Available Documentation

This directory contains documentation for various aspects of the project:

- [API Testing](docs/api.md) - Documentation for API testing framework
- [Environment Variables Usage](docs/env-usage.md) - Guide for using .env files for local secrets
- [GitHub Actions](docs/github-actions.md) - Information about CI/CD with GitHub Actions
- [Spotless Code Formatting](docs/spotless.md) - Guide for using Spotless for code formatting

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

#### Clean Reports

To clean previous Allure results before a new test run:

```bash
./gradlew cleanAllureResults
```

#### Enhancing Reports

The framework uses Allure annotations to enhance reports:

- `@Feature` - Group tests by feature
- `@Story` - Define user stories within features
- `@Description` - Add detailed test descriptions
- `@Step` - Break down test steps for better visibility
- `@Severity` - Mark test importance (BLOCKER, CRITICAL, NORMAL, MINOR, TRIVIAL)

Example:

```java
@Test
@Story("Create and Retrieve Pet")
@Description("Test creating a new pet and then retrieving it by ID")
@Severity(SeverityLevel.CRITICAL)
public void testCreateAndGetPet() {
    // Test code
}
```

## Configuration Options

| Parameter                     | Description                              | Default Value                |
|-------------------------------|------------------------------------------|------------------------------|
| `api.base.url`                | Base URL for the API                     | http://localhost:8080/api/v3 |
| `logging.enabled`             | Enable/disable request/response logging  | true                         |
| `testcontainers.enabled`      | Enable/disable TestContainers            | true                         |
| `testcontainers.reuse.enable` | Enable container reuse between test runs | true                         |


## Using .env for Local Secrets

This project supports using `.env` files for managing sensitive information locally:

1. Add the dotenv-java dependency to your project
2. Create a `.env` file in your project root
3. Add `.env` to your `.gitignore` file
4. Create a `.env.example` file with the structure but without actual secrets
5. Update `ProjectConfig` to load from both `.env` and `config.properties`

For more details, see the [.env usage guide](docs/env-usage.md).

## CI/CD with GitHub Actions

This project includes GitHub Actions workflows for continuous integration and testing:

- Automated test execution on push and pull requests
- Parallel test execution for faster feedback
- Allure report generation and publishing
- Test results visualization in GitHub

To use GitHub Actions:

1. Push your changes to GitHub
2. GitHub Actions will automatically run the tests
3. View test results in the Actions tab
4. Download Allure reports from the workflow artifacts

For more details, see the [GitHub Actions guide](docs/github-actions.md).

## Code Formatting with Spotless

This project uses Spotless for code formatting and style enforcement:

- Google Java Format for Java files
- Consistent formatting for Gradle, XML, and Markdown files
- Automatic format checking during builds
- IDE integration support

To use Spotless:

```bash
# Check formatting without making changes
./gradlew spotlessCheck

# Apply formatting to all files
./gradlew spotlessApply
```

For more details, see the [Spotless guide](docs/spotless.md).

## Roadmap

### Short-term Goals

- [ ] Add more comprehensive test coverage for all Petstore API endpoints
- [ ] Implement data-driven testing data sources
- [ ] Add contract testing capabilities
- [ ] Enhance error handling and reporting

### Medium-term Goals

- [ ] Add UI testing module using Selenide
- [ ] Implement parallel test execution with improved reporting
- [ ] Add performance testing capabilities
- [x] Integrate with CI/CD pipelines (GitHub Actions)
- [ ] Integrate with additional CI/CD pipelines (Jenkins)

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
