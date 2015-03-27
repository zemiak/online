package com.zemiak.online.service.ui;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@SessionScoped
@Named("logoutForm")
public class LogoutForm implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private FacesContext context;
    
    public String logout() {
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        if (null != session) {
            session.invalidate();
        }
        
        return "../login.xhtml";
    }
}
