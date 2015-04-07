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
    private MailChecker checker;

    @Schedule(minute = "*/10", hour="*", second="0")
    public void check() {
        checker.check();
    }

    @PostConstruct
    public void firstCheck() {
        checker.check();
    }
}
