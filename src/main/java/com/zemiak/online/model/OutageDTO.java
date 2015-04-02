package com.zemiak.online.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class OutageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String start;
    private final String end;
    private final String duration;

    public OutageDTO(Outage source) {
        id = source.getId();
        start = new SimpleDateFormat("yyyy-MM-dd").format(source.getStart());
        end = null == source.getEnd() ? "" : new SimpleDateFormat("yyyy-MM-dd").format(source.getEnd());

        LocalDateTime startDate = LocalDateTime.from(source.getStart().toInstant());
        LocalDateTime endDate = null == source.getEnd() ? LocalDateTime.now() : LocalDateTime.from(source.getEnd().toInstant());

        Long seconds = ChronoUnit.SECONDS.between(startDate, endDate);
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
