package com.zemiak.online.service.jsf;

import com.zemiak.online.service.mail.MailChecker;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@ManagedBean
public class SystemView {
    @Inject
    MailChecker checker;

    public void checkMail(ActionEvent event) {
        checker.check();
        addMessage("Mail check finished.");
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
