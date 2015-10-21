package com.zemiak.online.service;

import com.zemiak.online.model.Outage;
import com.zemiak.online.model.ProtectedSystem;
import com.zemiak.online.model.ProtectedSystemDTO;
import com.zemiak.online.model.event.OutageStartEvent;
import com.zemiak.online.model.event.OutageStopEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class OutageService {
    private static final Logger LOG = Logger.getLogger(OutageService.class.getName());
    static final long ONE_DAY = 1000 * 3600 * 24;

    @PersistenceContext
    EntityManager em;

    @Inject
    private Event<OutageStartEvent> startEvents;

    @Inject
    private Event<OutageStopEvent> stopEvents;

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

    public void checkOutages() {
        Calendar now = new GregorianCalendar();
        now.add(Calendar.MINUTE, -ProtectedSystem.OUTAGE_MINUTES);
        em.createNamedQuery("ProtectedSystem.findAll", ProtectedSystem.class).getResultList().stream()
                .forEach(system -> {
            ProtectedSystemDTO dto = new ProtectedSystemDTO(system);

            if (dto.isSystemOutage()) {
                startOutage(system);
            } else {
                stopOutage(system);
            }
        });
    }

    private void startOutage(ProtectedSystem system) {
        List<Outage> outages = em.createNamedQuery("Outage.findAliveBySystem", Outage.class).setParameter("system", system).getResultList();
        if (outages.size() > 1) {
            throw new IllegalStateException("More than 1 outage for " + system.getName());
        }

        if (outages.isEmpty()) {
            startEvents.fire(new OutageStartEvent(system));
        }
    }

    private void stopOutage(ProtectedSystem system) {
        Outage outage;

        try {
            outage = em.createNamedQuery("Outage.findAliveBySystem", Outage.class).setParameter("system", system).getSingleResult();
        } catch (NoResultException ex) {
            LOG.log(Level.INFO, "Tried to stop a non-existing exception for {0}", system.getName());
            return;
        }

        stopEvents.fire(new OutageStopEvent(outage));
    }
}
