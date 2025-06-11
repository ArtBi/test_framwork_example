# TestRail Integration

This project includes integration with TestRail for reporting test results. The integration allows test results to be
automatically uploaded to TestRail after test execution.

## How It Works

The integration uses the TestRail API to create test runs and upload test results. It relies on the `@TmsLink`
annotation in test methods to map tests to TestRail test cases.

For example:

```java

@Test(groups = "smoke")
@TmsLink("C2072")  // Maps to TestRail case ID 2072
public void testCreateAndGetPet() {
    // Test implementation
}
```

## Configuration

The TestRail integration is configured through properties that can be set in different ways:

1. In the `testrail.properties` file
2. As system properties
3. As environment variables

### Configuration Properties

| Property           | Description                                          | Default |
|--------------------|------------------------------------------------------|---------|
| testrail.enabled   | Enable/disable TestRail integration                  | false   |
| testrail.url       | TestRail URL (e.g., https://yourcompany.testrail.io) | -       |
| testrail.username  | TestRail username                                    | -       |
| testrail.apiKey    | TestRail API key or password                         | -       |
| testrail.projectId | TestRail project ID                                  | -       |
| testrail.suiteId   | TestRail suite ID                                    | -       |

### GitHub Actions Configuration

For GitHub Actions, the TestRail integration is configured using secrets:

1. Go to your GitHub repository
2. Navigate to Settings > Secrets and variables > Actions
3. Add the following secrets:
    - `TESTRAIL_ENABLED`: Set to `true` to enable TestRail integration
    - `TESTRAIL_URL`: Your TestRail URL
    - `TESTRAIL_USERNAME`: Your TestRail username
    - `TESTRAIL_API_KEY`: Your TestRail API key
    - `TESTRAIL_PROJECT_ID`: Your TestRail project ID
    - `TESTRAIL_SUITE_ID`: Your TestRail suite ID

## Local Development

For local development, you can enable TestRail integration by:

1. Creating a `testrail.properties` file in the `src/main/resources` directory with your TestRail configuration
2. Setting system properties when running tests:

```bash
./gradlew apiTests -Dtestrail.enabled=true -Dtestrail.url=https://yourcompany.testrail.io -Dtestrail.username=your_username -Dtestrail.apiKey=your_api_key -Dtestrail.projectId=1 -Dtestrail.suiteId=1
```

## Test Case Mapping

To map a test to a TestRail test case:

1. Create a test case in TestRail and note its ID (e.g., C2072)
2. Add the `@TmsLink` annotation to your test method with the case ID:

```java

@Test
@TmsLink("C2072")
public void myTest() {
    // Test implementation
}
```

## Test Run Naming

Test runs in TestRail are named using the following format:

```
<Suite Name> - <Test Name> (GitHub Actions #<Run ID>) - <Timestamp>
```

For example:

```
Petstore API Test Suite - Smoke API Tests (GitHub Actions #123456) - 1620000000000
```

This naming convention helps identify test runs and their source.