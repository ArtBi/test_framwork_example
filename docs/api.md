# API Testing

## Overview

API tests are written using REST Assured and TestNG. They cover all the main endpoints of our web petstore application.

## Test Structure

### Base Class

`BaseApiTest` contains the basic configuration for all API tests:

- REST Assured setup
- Authentication
- Logging
- Response validation

### Testing Modules

1. **Authentication** (`auth/`)
    - User registration
    - Login
    - Token refresh
    - Logout

2. **Book Catalog** (`catalog/`)
    - Getting a list of books
    - Book search
    - Filtering by categories
    - Book details

3. **Shopping Cart** (`cart/`)
    - Adding items
    - Updating quantities
    - Removing items
    - Clearing the cart

4. **Orders** (`order/`)
    - Creating an order
    - Viewing history
    - Canceling an order
    - Order status

5. **Profile** (`profile/`)
    - Updating data
    - Changing password
    - Delivery addresses
    - Order history

## Running Tests

### Local Execution

```bash
./gradlew :automation:api-tests:test
```

### With Parameters

```bash
./gradlew :automation:api-tests:test \
  -Dapi.base.url=http://localhost:8080 \
  -Dapi.username=testuser \
  -Dapi.password=password123
```

### Running a Specific Test

```bash
./gradlew :automation:api-tests:test --tests "com.bookstore.api.tests.auth.AuthenticationTest"
```

## Validation

### JSON Schema

JSON schemas are used for response validation. They are located in:

```
automation/api-tests/src/test/resources/schemas/
```

### Status Codes

The following status codes are checked:

- 200 OK
- 201 Created
- 400 Bad Request
- 401 Unauthorized
- 403 Forbidden
- 404 Not Found
- 500 Internal Server Error

### Data Validation

- Checking required fields
- Format validation (email, dates, etc.)
- Constraint validation (min/max length, ranges)
- Business rule validation

## Logging

### Configuration

Logging is configured in `log4j2.xml`:

- Console output
- File logs
- Level separation (info, error)

### Logs

- `api-tests.log` - general log
- `api-tests-error.log` - errors
- All logs are stored in `logs/`

## Reporting

### Allure

Allure is used for report generation:

```bash
./gradlew allureReport
./gradlew allureServe
```

### Annotations

The following Allure annotations are used:

- `@Epic`
- `@Feature`
- `@Story`
- `@Severity`
- `@Description`

## Security

### Authentication

- JWT tokens
- Token refresh
- Expiration validation

### Validation

- Input data validation
- SQL injection protection
- Header validation

### Security Testing

- Access verification
- Token validation
- Boundary testing

## Best Practices

1. **Test Organization**
    - Grouping by functionality
    - Clear test names
    - Test documentation

2. **Data Preparation**
    - Using test data
    - Cleanup after tests
    - Test isolation

3. **Error Handling**
    - Testing error scenarios
    - Error message validation
    - Error logging

4. **Parallel Execution**
    - Configuration in `testng.xml`
    - Test isolation
    - Resource management
