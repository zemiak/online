package com.zemiak.online.service;

import com.zemiak.online.model.Outage;
import com.zemiak.online.model.ProtectedSystem;
import com.zemiak.online.model.event.NewProtectedSystemEvent;
import com.zemiak.online.model.event.OutageStartEvent;
import com.zemiak.online.model.event.OutageStopEvent;
import com.zemiak.online.model.event.ProtectedSystemSeenEvent;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EventPersister {
    @PersistenceContext
    private EntityManager em;

    public void newSystem(@Observes NewProtectedSystemEvent event) {
        ProtectedSystem system = ProtectedSystem.create();
        system.setName(event.getName());
        em.persist(system);
    }

    public void startOutage(@Observes OutageStartEvent event) {
        ProtectedSystem system = event.getProtectedSystem();
        em.merge(system);

        Outage outage = new Outage();
        outage.setProtectedSystem(system);
        em.persist(outage);

        Set<Outage> outages = system.getOutages();
        if (null == outages) {
            outages = new HashSet<>();
            system.setOutages(outages);
        }

        outages.add(outage);
    }

    public void stopOutage(@Observes OutageStopEvent event) {
        Outage outage = event.getOutage();
        em.merge(outage);
        
        outage.setEnd(new Date());
    }

    public void seen(@Observes ProtectedSystemSeenEvent event) {
        ProtectedSystem system = event.getProtectedSystem();
        em.merge(system);

        if (system.getLastSeen().before(event.getSeen())) {
            system.setLastSeen(event.getSeen());
        }
    }
}
