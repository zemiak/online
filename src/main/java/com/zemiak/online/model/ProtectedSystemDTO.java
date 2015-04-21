package com.zemiak.online.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class ProtectedSystemDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String name;
    private final String created;
    private final String lastSeen;
    private final String disabled;
    private final String outage;

    public ProtectedSystemDTO(ProtectedSystem source) {
        id = source.getId();
        name = source.getName();
        disabled = source.isDisabled() ? "1" : "0";
        created = null == source.getCreated() ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm").format(source.getCreated());
        lastSeen = null == source.getLastSeen() ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm").format(source.getLastSeen());
        outage = source.isOutage() ? "YES" : "no";
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreated() {
        return created;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public String getDisabled() {
        return disabled;
    }

    public String getOutage() {
        return outage;
    }

    public boolean isSystemOutage() {
        return "YES".equals(outage);
    }
}
