package com.zemiak.online.service.ui;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@SessionScoped
@Named("logoutForm")
public class LogoutForm implements Serializable {
    private static final long serialVersionUID = 1L;

    public void logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}
