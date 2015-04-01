package com.zemiak.online.service.ui;

import com.zemiak.online.service.ui.resources.ProtectedSystemsResource;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SessionScoped
@Named("protectedSystemForm")
public class ProtectedSystemForm implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject private ProtectedSystemsResource systems;

    public ProtectedSystemForm() {
    }

    public long getFailures() {
        return systems.all().getData().stream().map(system -> system.getOutage() == 1).count();
    }
    
    public long getSystemCount() {
        return systems.all().getData().size();
    }
}
