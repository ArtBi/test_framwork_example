package com.github.artbi.common.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources({"classpath:config.properties"})
public interface BaseConfig extends Config {

    static BaseConfig get() {
        return ConfigFactory.create(BaseConfig.class, System.getProperties());
    }

    @Key("api.base.url")
    String apiBaseUrl();

    @Key("logging.enabled")
    @DefaultValue("false")
    boolean loggingEnabled();
}
