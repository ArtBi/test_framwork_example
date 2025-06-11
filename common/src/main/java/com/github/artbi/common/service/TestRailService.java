package com.github.artbi.common.service;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.Result;
import com.codepine.api.testrail.model.ResultField;
import com.codepine.api.testrail.model.Run;
import com.github.artbi.common.config.TestRailConfig;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service for interacting with TestRail API.
 * This class provides methods to create test runs and upload test results to TestRail.
 */
@Slf4j
public class TestRailService {
    private static final Pattern TMS_LINK_PATTERN = Pattern.compile("C(\\d+)");
    private static final int PASSED_STATUS_ID = 1;
    private static final int FAILED_STATUS_ID = 5;
    private static final int SKIPPED_STATUS_ID = 2;

    private final TestRailConfig config;
    private final TestRail testRail;
    private final Map<String, List<Result>> resultsByRunName;
    private final Map<String, Integer> runIdsByName;

    /**
     * Constructor for TestRailService.
     */
    public TestRailService() {
        this.config = ConfigFactory.create(TestRailConfig.class);
        this.resultsByRunName = new HashMap<>();
        this.runIdsByName = new HashMap<>();

        if (config.enabled()) {
            log.info("Initializing TestRail client with URL: {}", config.url());
            this.testRail = TestRail.builder(config.url(), config.username(), config.apiKey()).build();
        } else {
            log.info("TestRail integration is disabled");
            this.testRail = null;
        }
    }

    /**
     * Add a test result to be uploaded to TestRail.
     *
     * @param runName    the name of the test run
     * @param tmsLink    the TMS link (TestRail case ID)
     * @param status     the test status (passed, failed, skipped)
     * @param message    the test message or error
     * @param elapsedSec the elapsed time in seconds
     */
    public void addResult(String runName, String tmsLink, String status, String message, int elapsedSec) {
        if (!config.enabled() || testRail == null) {
            log.debug("TestRail integration is disabled, skipping result for case: {}", tmsLink);
            return;
        }

        Integer caseId = extractCaseId(tmsLink);
        if (caseId == null) {
            log.warn("Could not extract case ID from TMS link: {}", tmsLink);
            return;
        }

        Result result = new Result();
        result.setCaseId(caseId);
        result.setStatusId(getStatusId(status));
        result.setComment(message);
        result.setElapsed(elapsedSec + "s");

        resultsByRunName.computeIfAbsent(runName, k -> new ArrayList<>()).add(result);
        log.info("Added result for case C{}: status={}, message={}", caseId, status, message);
    }

    /**
     * Upload all collected results to TestRail.
     */
    public void uploadResults() {
        if (!config.enabled() || testRail == null) {
            log.info("TestRail integration is disabled, skipping result upload");
            return;
        }

        try {
            List<ResultField> customResultFields = testRail.resultFields().list().execute();

            for (Map.Entry<String, List<Result>> entry : resultsByRunName.entrySet()) {
                String runName = entry.getKey();
                List<Result> results = entry.getValue();

                if (results.isEmpty()) {
                    log.info("No results to upload for run: {}", runName);
                    continue;
                }

                Integer runId = getOrCreateRun(runName);
                if (runId == null) {
                    log.error("Failed to create or get run ID for: {}", runName);
                    continue;
                }

                log.info("Uploading {} results to TestRail run {} (ID: {})", results.size(), runName, runId);
                testRail.results().addForCases(runId, results, customResultFields).execute();
                log.info("Successfully uploaded results to TestRail");
            }
        } catch (Exception e) {
            log.error("Error uploading results to TestRail", e);
        }
    }

    private Integer getOrCreateRun(String runName) {
        if (runIdsByName.containsKey(runName)) {
            return runIdsByName.get(runName);
        }

        try {
            Run run = new Run();
            run.setName(runName);
            run.setSuiteId(config.suiteId());
            run.setIncludeAll(false);

            // Get case IDs from results
            List<Integer> caseIds = resultsByRunName.get(runName).stream()
                    .map(Result::getCaseId)
                    .distinct()
                    .toList();
            run.setCaseIds(caseIds);

            Run createdRun = testRail.runs().add(config.projectId(), run).execute();
            int runId = createdRun.getId();
            runIdsByName.put(runName, runId);
            log.info("Created TestRail run: {} (ID: {})", runName, runId);
            return runId;
        } catch (Exception e) {
            log.error("Error creating TestRail run: {}", runName, e);
            return null;
        }
    }

    private Integer extractCaseId(String tmsLink) {
        if (tmsLink == null || tmsLink.isEmpty()) {
            return null;
        }

        Matcher matcher = TMS_LINK_PATTERN.matcher(tmsLink);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return null;
    }

    private int getStatusId(String status) {
        return switch (status.toLowerCase()) {
            case "passed" -> PASSED_STATUS_ID;
            case "failed" -> FAILED_STATUS_ID;
            case "skipped" -> SKIPPED_STATUS_ID;
            default -> FAILED_STATUS_ID;
        };
    }
}