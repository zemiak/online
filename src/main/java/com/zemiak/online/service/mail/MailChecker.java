package com.zemiak.online.service.mail;

import com.zemiak.online.model.AliveMailMessage;
import com.zemiak.online.model.Outage;
import com.zemiak.online.model.ProtectedSystem;
import com.zemiak.online.model.ProtectedSystemDTO;
import com.zemiak.online.service.mail.events.NewProtectedSystemEvent;
import com.zemiak.online.service.mail.events.OutageStartEvent;
import com.zemiak.online.service.mail.events.OutageStopEvent;
import java.util.*;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class MailChecker {
    private static final Logger LOG = Logger.getLogger(MailChecker.class.getName());
    private static final int COUNT = 15;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private AliveMailFolder folder;

    @Inject
    private Event<NewProtectedSystemEvent> systemEvents;

    @Inject
    private Event<OutageStartEvent> startEvents;

    @Inject
    private Event<OutageStopEvent> stopEvents;

    public void check() {
        int size = folder.size();

        System.out.println("Folder size: " + size);

        for (int i = size; (size - i < COUNT); i--) {
            System.out.println(String.format("Checking message %d", i));
            check(folder.get(i));
        }

        checkOutages();
    }

    private void check(AliveMailMessage message) {
        ProtectedSystem system;

        System.out.println("Checking mail: " + message.getSystem() + " " + message.getSent());

        try {
            system = em.createNamedQuery("ProtectedSystem.findByName", ProtectedSystem.class)
                    .setParameter("name", message.getSystem())
                    .getSingleResult();
            System.out.println("... found system");
        } catch (NoResultException ex) {
            system = createProtectedSystem(message.getSystem());
            System.out.println("... created system");
        }

        if (system.getLastSeen().before(message.getSent())) {
            system.setLastSeen(message.getSent());
        }
    }

    private ProtectedSystem createProtectedSystem(String name) {
        ProtectedSystem system = ProtectedSystem.create();
        system.setName(name);
        em.persist(system);

        systemEvents.fire(new NewProtectedSystemEvent(system));

        return system;
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
            createOutage(system);
            System.out.println(".... created outage");
        } else {
            System.out.println(".... kept outage");
        }
    }

    private void stopOutage(ProtectedSystem system) {
        Outage outage;

        try {
            outage = em.createNamedQuery("Outage.findAliveBySystem", Outage.class).setParameter("system", system).getSingleResult();
        } catch (NoResultException ex) {
            System.out.println(".... tried to stop non-existing outage");
            return;
        }

        outage.setEnd(new Date());
        stopEvents.fire(new OutageStopEvent(outage, outage.getSystem()));
        System.out.println(".... stopped outage");
    }

    private Outage createOutage(ProtectedSystem system) {
        Outage outage = new Outage();
        outage.setSystem(system);
        em.persist(outage);

        Set<Outage> outages = system.getOutages();
        if (null == outages) {
            outages = new HashSet<>();
            system.setOutages(outages);
        }
        outages.add(outage);

        startEvents.fire(new OutageStartEvent(outage, outage.getSystem()));

        return outage;
    }
}
