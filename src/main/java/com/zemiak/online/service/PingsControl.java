package com.zemiak.online.service;

import com.zemiak.online.model.ProtectedSystem;
import com.zemiak.online.model.event.NewProtectedSystemEvent;
import com.zemiak.online.model.event.ProtectedSystemSeenEvent;
import java.util.Date;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class PingsControl {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private Event<NewProtectedSystemEvent> newSystemEvents;

    @Inject
    private Event<ProtectedSystemSeenEvent> seenEvents;

    public void ping(String service) {
        ProtectedSystem system;
        Date now = new Date();

        try {
            system = em.createNamedQuery("ProtectedSystem.findByName", ProtectedSystem.class)
                    .setParameter("name", service)
                    .getSingleResult();

            if (system.isDisabled()) {
                system.setDisabled(false);
            }
        } catch (NoResultException ex) {
            newSystemEvents.fire(new NewProtectedSystemEvent(service, now));

            system = em.createNamedQuery("ProtectedSystem.findByName", ProtectedSystem.class)
                    .setParameter("name", service)
                    .getSingleResult();
        }

        seenEvents.fire(new ProtectedSystemSeenEvent(system, now));
    }



}
