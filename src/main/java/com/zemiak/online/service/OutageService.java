package com.zemiak.online.service;

import com.zemiak.online.model.Outage;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class OutageService {
    private static final int ONE_DAY = 1000 * 3600 * 24;

    @PersistenceContext
    private EntityManager em;

    public List<Outage> findByProtectedSystem(Long id) {
        return em.createNamedQuery("Outage.findAllBySystem", Outage.class).setParameter("id", id).getResultList();
    }

    public List<Outage> findLastMonth(Long id) {
        return findByProtectedSystem(id).stream()
                .filter(system -> system.getStart().getTime() > getTimeDaysAgo(33))
                .collect(Collectors.toList());
    }

    public List<Outage> findLastYear(Long id) {
        return findByProtectedSystem(id).stream()
                .filter(system -> system.getStart().getTime() > getTimeDaysAgo(366))
                .collect(Collectors.toList());
    }

    public List<Outage> findLastDayByEnd(Long id) {
        return findByProtectedSystem(id).stream()
                .filter(system -> null != system.getEnd())
                .filter(system -> system.getEnd().getTime() > getTimeDaysAgo(1))
                .collect(Collectors.toList());
    }

    private long getTimeDaysAgo(int days) {
        return (new Date()).getTime() - (ONE_DAY * days);
    }
}
