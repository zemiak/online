package com.zemiak.online.service.data;

import com.zemiak.online.model.ProtectedSystem;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import javax.ejb.Stateless;


@Stateless
public class ProtectedSystemService {

    @Inject
    private EntityManager em;

    public List<ProtectedSystem> all() {
        return em.createNamedQuery("ProtectedSystem.findAll", ProtectedSystem.class).getResultList();
    }
}
