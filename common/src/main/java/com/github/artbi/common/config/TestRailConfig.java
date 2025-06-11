package com.github.artbi.common.config;

import org.aeonbits.owner.Config;

/**
 * Configuration interface for TestRail integration.
 * This interface defines properties needed to connect to TestRail API.
 */
@Config.Sources({"classpath:testrail.properties", "system:properties", "system:env"})
public interface TestRailConfig extends Config {

    /**
     * TestRail API URL.
     *
     * @return the TestRail API URL
     */
    @Key("testrail.url")
    String url();

    /**
     * TestRail username.
     *
     * @return the TestRail username
     */
    @Key("testrail.username")
    String username();

    /**
     * TestRail API key or password.
     *
     * @return the TestRail API key or password
     */
    @Key("testrail.apiKey")
    String apiKey();

    /**
     * TestRail project ID.
     *
     * @return the TestRail project ID
     */
    @Key("testrail.projectId")
    int projectId();

    /**
     * TestRail suite ID.
     *
     * @return the TestRail suite ID
     */
    @Key("testrail.suiteId")
    int suiteId();

    /**
     * Flag to enable/disable TestRail integration.
     *
     * @return true if TestRail integration is enabled, false otherwise
     */
    @Key("testrail.enabled")
    @DefaultValue("false")
    boolean enabled();
}