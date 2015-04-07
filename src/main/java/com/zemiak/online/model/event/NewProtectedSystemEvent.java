package com.zemiak.online.model.event;

import java.util.Date;

public class NewProtectedSystemEvent {
    private final String name;
    private final Date seen;

    public NewProtectedSystemEvent(String name, Date seen) {
        this.name = name;
        this.seen = seen;
    }

    public String getName() {
        return name;
    }

    public Date getSeen() {
        return seen;
    }
}
