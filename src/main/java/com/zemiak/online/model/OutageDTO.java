package com.zemiak.online.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class OutageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String start;
    private final String end;

    public OutageDTO(Outage source) {
        id = source.getId();
        start = null == source.getStart() ? "" : new SimpleDateFormat("yyyy-MM-dd").format(source.getStart());
        end = null == source.getEnd() ? "" : new SimpleDateFormat("yyyy-MM-dd").format(source.getEnd());
    }

    public Long getId() {
        return id;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
