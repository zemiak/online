package com.zemiak.online.service;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@Singleton
@Startup
public class ConfigurationProvider {
    private final Map<String, String> configuration = new HashMap<>();
    private static final String[] CONFIG_FILES = new String[]{"config", "mail", "account"};

    @PostConstruct
    public void readConfiguration() {

        for (String config: CONFIG_FILES) {
            ResourceBundle props = ResourceBundle.getBundle(config);

            props.keySet().stream().forEach(key -> {
                configuration.put(key, props.getString(key));
            });
        }
    }

    @Produces
    public String getString(InjectionPoint point) {
        String fieldName = point.getMember().getName();
        return configuration.get(fieldName);
    }

    @Produces
    public int getInt(InjectionPoint point) {
        String stringValue = getString(point);
        if (stringValue == null) {
            return 0;
        }

        return Integer.parseInt(stringValue);
    }
}
