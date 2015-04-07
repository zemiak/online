package com.zemiak.online.model.event;

import com.zemiak.online.model.ProtectedSystem;

public class OutageStartEvent {
    private final ProtectedSystem system;

    public OutageStartEvent(ProtectedSystem system) {
        this.system = system;
    }

    public ProtectedSystem getProtectedSystem() {
        return system;
    }
}
