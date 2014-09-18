package com.zemiak.online.service.mail;

import com.zemiak.online.model.AliveMailMessage;
import com.zemiak.online.model.Outage;
import com.zemiak.online.model.ProtectedSystem;
import java.util.*;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class MailChecker {
    private static final int MAX_COUNT = 60;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private AliveMailFolder folder;

    public void check() {
        int count = folder.size() > MAX_COUNT ? MAX_COUNT : folder.size();

        for (int i = 1; i <= count; i++) {
            System.out.println(String.format("Checking message %d of %d", i, count));
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
            system = createProtectedSystem(message.getSystem());
        }

        if (system.getLastSeen().before(message.getSent())) {
            system.setLastSeen(message.getSent());
        }
    }

    private ProtectedSystem createProtectedSystem(String name) {
        ProtectedSystem system = new ProtectedSystem();
        system.setName(name);
        em.persist(system);

        return system;
    }

    private void checkOutages() {
        Calendar now = new GregorianCalendar();
        now.add(Calendar.MINUTE, -30);
        em.createNamedQuery("ProtectedSystem.findAll", ProtectedSystem.class).getResultList().stream().forEach((system) -> {
            if (now.after(system.getLastSeen())) {
                startOutage(system);
            } else {
                stopOutage(system);
            }
        });
    }

    private void startOutage(ProtectedSystem system) {
        List<Outage> outages = em.createNamedQuery("Outage.findAliveBySystem").setParameter("system", system).getResultList();
        if (outages.size() > 1) {
            throw new IllegalStateException("More than 1 outage for " + system.getName());
        }

        if (outages.isEmpty()) {
            createOutage(system);
        }
    }

    private void stopOutage(ProtectedSystem system) {
        Outage outage;

        try {
            outage = em.createNamedQuery("Outage.findAliveBySystem", Outage.class).setParameter("system", system).getSingleResult();
        } catch (NoResultException ex) {
            return;
        }

        outage.setEnd(new Date());
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

        return outage;
    }
}
