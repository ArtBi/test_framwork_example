package com.github.artbi.common.listeners;

import com.github.artbi.common.service.TestRailService;
import io.qameta.allure.TmsLink;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.concurrent.TimeUnit;

/**
 * TestNG listener for TestRail integration.
 * This listener collects test results and sends them to TestRail.
 */
@Slf4j
public class TestRailListener implements ITestListener {
    private final TestRailService testRailService;

    public TestRailListener() {
        this.testRailService = new TestRailService();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String tmsLink = extractTmsLink(result);
        if (tmsLink != null) {
            long durationMs = result.getEndMillis() - result.getStartMillis();
            int durationSec = (int) TimeUnit.MILLISECONDS.toSeconds(durationMs);
            testRailService.addResult(
                    getRunName(result.getTestContext()),
                    tmsLink,
                    "passed",
                    "Test passed successfully",
                    durationSec
            );
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String tmsLink = extractTmsLink(result);
        if (tmsLink != null) {
            long durationMs = result.getEndMillis() - result.getStartMillis();
            int durationSec = (int) TimeUnit.MILLISECONDS.toSeconds(durationMs);

            String errorMessage = result.getThrowable() != null
                    ? result.getThrowable().getMessage()
                    : "Test failed without exception details";

            testRailService.addResult(
                    getRunName(result.getTestContext()),
                    tmsLink,
                    "failed",
                    errorMessage,
                    durationSec
            );
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String tmsLink = extractTmsLink(result);
        if (tmsLink != null) {
            testRailService.addResult(
                    getRunName(result.getTestContext()),
                    tmsLink,
                    "skipped",
                    "Test was skipped",
                    0
            );
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        testRailService.uploadResults();
    }

    private String extractTmsLink(ITestResult result) {
        TmsLink tmsLink = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(TmsLink.class);
        if (tmsLink != null) {
            return tmsLink.value();
        }

        log.debug("No TmsLink annotation found for test: {}", result.getName());
        return null;
    }

    private String getRunName(ITestContext context) {
        String suiteName = context.getSuite().getName();
        String testName = context.getName();

        // Add timestamp to make run name unique
        String timestamp = String.valueOf(System.currentTimeMillis());

        // Add CI build information if available
        String ciInfo = System.getenv("GITHUB_RUN_ID") != null
                ? " (GitHub Actions #" + System.getenv("GITHUB_RUN_ID") + ")"
                : "";

        return suiteName + " - " + testName + ciInfo + " - " + timestamp;
    }

    // Other ITestListener methods with empty implementations
    @Override
    public void onTestStart(ITestResult result) {
        // Not needed for TestRail integration
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Treat as failure
        onTestFailure(result);
    }

    @Override
    public void onStart(ITestContext context) {
        // Not needed for TestRail integration
    }
}