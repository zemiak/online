package com.zemiak.online.service;

import com.zemiak.online.model.Outage;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class OutageService {
    static final long ONE_DAY = 1000 * 3600 * 24;

    @PersistenceContext
    EntityManager em;

    public List<Outage> findByProtectedSystem(Long id) {
        Query query = em.createNamedQuery("Outage.findAllBySystem");
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<Outage> findLastMonth(Long id) {
        long ago = getTimeDaysAgo(33);

        return findByProtectedSystem(id).stream()
                .filter(outage -> outage.getStart().getTime() > ago)
                .collect(Collectors.toList());
    }

    public List<Outage> findLastYear(Long id) {
        long ago = getTimeDaysAgo(366);
        return findByProtectedSystem(id).stream()
                .filter(outage -> outage.getStart().getTime() > ago)
                .collect(Collectors.toList());
    }

    public List<Outage> findEndedLastDay(Long id) {
        return findByProtectedSystem(id).stream()
                .filter(outage -> null != outage.getEnd())
                .filter(outage -> outage.getEnd().getTime() > getTimeDaysAgo(1))
                .collect(Collectors.toList());
    }

    long getTimeDaysAgo(int days) {
        return (new Date()).getTime() - (ONE_DAY * days);
    }
}
