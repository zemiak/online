package com.zemiak.online.service;

import com.zemiak.online.model.ProtectedSystem;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class SystemRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<ProtectedSystem> events;

    public void register(ProtectedSystem system) throws Exception {
        log.info("Registering " + system.getName());
        em.persist(system);
        events.fire(system);
    }
}
