package com.zemiak.online.model.event;

import com.zemiak.online.model.Outage;

public class OutageStopEvent {
    private final Outage outage;

    public OutageStopEvent(Outage outage) {
        this.outage = outage;
        outage.getProtectedSystem().getName(); // lazy load force
    }

    public Outage getOutage() {
        return outage;
    }
}
