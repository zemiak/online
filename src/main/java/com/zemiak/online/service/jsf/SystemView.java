package com.zemiak.online.service.jsf;

import com.zemiak.online.service.mail.MailChecker;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@ManagedBean
public class SystemView {
    @Inject
    MailChecker checker;

    public void checkMail(ActionEvent event) {
        checker.check();
    }
}
