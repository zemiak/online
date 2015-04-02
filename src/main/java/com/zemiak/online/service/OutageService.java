package com.zemiak.online.service;

import com.zemiak.online.model.Outage;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import javax.ejb.Stateless;


@Stateless
public class OutageService {

    @Inject
    private EntityManager em;

    public List<Outage> findByProtectedSystem(Integer id) {
        return em.createNamedQuery("Outage.findAllBySystem", Outage.class).setParameter("id", id).getResultList();
    }

    public List<Outage> findByProtectedSystemAndInterval(Integer id, String interval) {
        return em.createNativeQuery("SELECT * FROM outage WHERE id = ? AND start > NOW() - INTERVAL ?", Outage.class)
                .setParameter(1, id)
                .setParameter(2, interval)
                .getResultList();
    }
}
