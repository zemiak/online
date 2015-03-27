package com.zemiak.online.service.data;

import com.zemiak.online.model.ProtectedSystem;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import javax.ejb.Stateless;


@Stateless
public class ProtectedSystemService {

    @Inject
    private EntityManager em;

    public List<ProtectedSystem> all() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProtectedSystem> criteria = cb.createQuery(ProtectedSystem.class);
        Root<ProtectedSystem> member = criteria.from(ProtectedSystem.class);
        criteria.select(member).orderBy(cb.asc(member.get("name")));
        return em.createQuery(criteria).getResultList();
    }
}
