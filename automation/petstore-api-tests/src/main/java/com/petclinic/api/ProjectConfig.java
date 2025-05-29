package com.petclinic.api;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources({"classpath:config.properties"})
public interface ProjectConfig extends Config {

    static ProjectConfig get() {
        return ConfigFactory.create(ProjectConfig.class, System.getProperties());
    }

    @Key("api.base.url")
    @DefaultValue("http://localhost:8080/api/v3")
    String apiBaseUrl();

    @Key("logging.enabled")
    @DefaultValue("true")
    boolean loggingEnabled();
}
