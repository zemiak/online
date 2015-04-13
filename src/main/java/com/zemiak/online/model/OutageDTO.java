package com.zemiak.online.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OutageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String start;
    private final String end;
    private final String duration;

    public OutageDTO(Outage source) {
        id = source.getId();
        start = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(source.getStart());
        end = null == source.getEnd() ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm").format(source.getEnd());

        Date startDate = source.getStart();
        Date endDate = null == source.getEnd() ? new Date() : source.getEnd();

        Long seconds = (endDate.getTime() - startDate.getTime()) / 1000;
        Long hours = seconds / 3600;
        seconds -= (seconds / 3600) * 3600;
        Long minutes = seconds / 60;

        duration = String.format("%02d:%02d", hours, minutes);
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

    public String getDuration() {
        return duration;
    }
}
