package com.zemiak.online.service.mail;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class CheckMailsScheduled {
    @Inject
    private MailChecker checker;

    @Schedule(minute = "*/15")
    public void check() {
        checker.check();
    }
}
