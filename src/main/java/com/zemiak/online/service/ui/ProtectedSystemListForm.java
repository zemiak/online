package com.zemiak.online.service.ui;

import com.zemiak.online.service.ui.resource.ProtectedSystemsResource;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SessionScoped
@Named("protectedSystemListForm")
public class ProtectedSystemListForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject private ProtectedSystemsResource systems;

    public ProtectedSystemListForm() {
    }

    public long getFailures() {
        return systems.all().getData().stream()
                .filter(system -> "YES".equals(system.getOutage()))
                .count();
    }

    public long getSystemCount() {
        return systems.all().getData().size();
    }
}