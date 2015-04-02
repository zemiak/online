package com.zemiak.online.service;

import com.zemiak.online.model.ProtectedSystem;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class ProtectedSystemService {

    @PersistenceContext
    private EntityManager em;

    public List<ProtectedSystem> all() {
        return em.createNamedQuery("ProtectedSystem.findAll", ProtectedSystem.class).getResultList();
    }

    public ProtectedSystem find(Integer id) {
        return em.find(ProtectedSystem.class, id);
    }
}
