package com.zemiak.online.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ProtectedSystemDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int OUTAGE_MINUTES = 30;

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

        if ("".equals(lastSeen)) {
            outage = "YES";
        } else {
            long diff = (new Date().getTime()) - source.getLastSeen().getTime();
            outage = TimeUnit.MILLISECONDS.toMinutes(diff) > OUTAGE_MINUTES ? "YES" : "no";
        }
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
}
