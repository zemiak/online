package com.zemiak.online.model.event;

import com.zemiak.online.model.ProtectedSystem;
import java.util.Date;

public class ProtectedSystemSeenEvent {
    private final ProtectedSystem system;
    private final Date seen;

    public ProtectedSystemSeenEvent(ProtectedSystem system, Date seen) {
        this.system = system;
        this.seen = seen;
    }

    public ProtectedSystem getProtectedSystem() {
        return system;
    }

    public Date getSeen() {
        return seen;
    }
}
