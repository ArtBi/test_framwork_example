package com.petclinic.api;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources({"classpath:config.properties"})
public interface ProjectConfig extends Config {

    static ProjectConfig get() {
        return ConfigFactory.create(ProjectConfig.class, System.getProperties());
    }

    String env();

    @Key("{env}.api.base.url")
    String apiBaseUrl();

    @Key("logging.enabled")
    @DefaultValue("true")
    boolean loggingEnabled();

    @Key("locale")
    String locale();

    @Key("testcontainers.enabled")
    boolean testContainersEnabled();

    @Key("testcontainers.reuse.enable")
    boolean reuseContainersEnabled();
}
