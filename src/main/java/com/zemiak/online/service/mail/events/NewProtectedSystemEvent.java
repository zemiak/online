package com.zemiak.online.service.mail.events;

import com.zemiak.online.model.ProtectedSystem;

public class NewProtectedSystemEvent {
    private final ProtectedSystem system;
    
    public NewProtectedSystemEvent(ProtectedSystem system) {
        this.system = system;
    }
    
    public ProtectedSystem get() {
        return system;
    }
}
