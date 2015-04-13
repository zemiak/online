package com.zemiak.online.service;

import com.zemiak.online.model.Outage;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class OutageService {

    @PersistenceContext
    private EntityManager em;

    public List<Outage> findByProtectedSystem(Long id) {
        return em.createNamedQuery("Outage.findAllBySystem", Outage.class).setParameter("id", id).getResultList();
    }

    public List<Outage> findLastMonth(Long id) {
        return findByProtectedSystem(id).stream()
                .filter(system -> (system.getStart().getTime() > (1000 * 3600 * 24 * 31)))
                .collect(Collectors.toList());
    }

    public List<Outage> findLastYear(Long id) {
        return findByProtectedSystem(id).stream()
                .filter(system -> (system.getStart().getTime() > (1000 * 3600 * 24 * 366)))
                .collect(Collectors.toList());
    }
}
