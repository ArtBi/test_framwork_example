# GitHub Actions for API Tests

This document describes the GitHub Actions workflow set up for running API tests in this project.

## Workflow Overview

The API tests workflow is defined in `.github/workflows/api-tests.yml` and is configured to run:

- On push to main/master branches
- On pull requests to main/master branches
- Manually via the GitHub Actions UI (workflow_dispatch)

## Workflow Steps

1. **Checkout Code**: Fetches the latest code from the repository
2. **Set up JDK 17**: Configures the Java environment with JDK 17
3. **Build Project**: Builds the project without running tests
4. **Run API Tests**: Executes the API tests using the Gradle `apiTests` task
5. **Generate Allure Report**: Creates an Allure report from the test results
6. **Upload Allure Report**: Uploads the Allure report as an artifact
7. **Publish Test Results**: Publishes the test results in GitHub

## Viewing Test Results

After the workflow completes, you can view the test results in two ways:

1. **GitHub Actions Summary**: Basic test results are shown directly in the GitHub Actions summary
2. **Allure Report**: For detailed test reports, download the Allure report artifact from the workflow run

## Running Tests Locally vs CI

The tests are configured to run the same way in both local and CI environments. The main differences are:

- **Environment Variables**: CI uses default values from config.properties
- **Parallelization**: Tests run in parallel as defined in testng.xml

## Troubleshooting

If tests fail in CI but pass locally, check:

1. **Configuration**: Ensure config.properties has appropriate default values
2. **Dependencies**: Verify all dependencies are properly declared in build.gradle
3. **Test Stability**: Check for flaky tests that might be affected by parallel execution

## Extending the Workflow

To add more test types to the workflow:

1. Create additional Gradle tasks in build.gradle
2. Add new steps to the workflow file
3. Update the testng.xml file to include new test classes
