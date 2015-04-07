package com.zemiak.online.model.event;

import com.zemiak.online.model.Outage;

public class OutageStopEvent {
    private final Outage outage;

    public OutageStopEvent(Outage outage) {
        this.outage = outage;
    }

    public Outage getOutage() {
        return outage;
    }
}
