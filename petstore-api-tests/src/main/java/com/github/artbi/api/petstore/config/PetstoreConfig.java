/*
 * Copyright (c) 2025
 */
package com.github.artbi.api.petstore.config;

import com.github.artbi.common.config.BaseConfig;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources({"classpath:config.properties"})
public interface PetstoreConfig extends BaseConfig {

    static PetstoreConfig get() {
        return ConfigFactory.create(PetstoreConfig.class, System.getProperties());
    }

    @Key("testcontainers.enabled")
    boolean testContainersEnabled();

    @Key("testcontainers.reuse.enable")
    boolean reuseContainersEnabled();
}
