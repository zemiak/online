package com.zemiak.online.service;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
@DependsOn("ConfigurationProvider")
public class CheckMailScheduler {
    @Inject
    private OutageService service;

    @Schedule(minute = "*/10", hour="*", second="0")
    public void check() {
        service.checkOutages();
    }

    @PostConstruct
    public void firstCheck() {
        service.checkOutages();
    }
}
