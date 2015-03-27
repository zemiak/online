package com.zemiak.online.service.mail.events;

import com.zemiak.online.model.Outage;
import com.zemiak.online.model.ProtectedSystem;

public class OutageStartEvent {
    private final Outage outage;
    private final ProtectedSystem system;
    
    public OutageStartEvent(Outage outage, ProtectedSystem system) {
        this.outage = outage;
        this.system = system;
    }
    
    public Outage getOutage() {
        return outage;
    }
    
    public ProtectedSystem getSystem() {
        return system;
    }
}
