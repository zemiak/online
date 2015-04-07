package com.zemiak.online.service;

import com.zemiak.online.model.AliveMailMessage;
import com.zemiak.online.model.Outage;
import com.zemiak.online.model.ProtectedSystem;
import com.zemiak.online.model.ProtectedSystemDTO;
import com.zemiak.online.model.event.NewProtectedSystemEvent;
import com.zemiak.online.model.event.OutageStartEvent;
import com.zemiak.online.model.event.OutageStopEvent;
import com.zemiak.online.model.event.ProtectedSystemSeenEvent;
import java.util.*;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class MailChecker {
    private static final int COUNT = 15;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private AliveMailFolder folder;

    @Inject
    private Event<NewProtectedSystemEvent> newSystemEvents;

    @Inject
    private Event<ProtectedSystemSeenEvent> seenEvents;

    @Inject
    private Event<OutageStartEvent> startEvents;

    @Inject
    private Event<OutageStopEvent> stopEvents;

    public void check() {
        int size = folder.size();

        for (int i = size; (size - i < COUNT); i--) {
            check(folder.get(i));
        }

        checkOutages();
    }

    private void check(AliveMailMessage message) {
        ProtectedSystem system;

        try {
            system = em.createNamedQuery("ProtectedSystem.findByName", ProtectedSystem.class)
                    .setParameter("name", message.getSystem())
                    .getSingleResult();
        } catch (NoResultException ex) {
            newSystemEvents.fire(new NewProtectedSystemEvent(message.getSystem(), message.getSent()));

            system = em.createNamedQuery("ProtectedSystem.findByName", ProtectedSystem.class)
                    .setParameter("name", message.getSystem())
                    .getSingleResult();
        }

        seenEvents.fire(new ProtectedSystemSeenEvent(system, message.getSent()));


    }

    private void checkOutages() {
        Calendar now = new GregorianCalendar();
        now.add(Calendar.MINUTE, -ProtectedSystemDTO.OUTAGE_MINUTES);
        em.createNamedQuery("ProtectedSystem.findAll", ProtectedSystem.class).getResultList().stream().forEach((system) -> {
            if (now.after(system.getLastSeen())) {
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
            return;
        }

        stopEvents.fire(new OutageStopEvent(outage));
    }
}
