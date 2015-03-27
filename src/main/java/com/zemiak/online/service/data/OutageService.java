package com.zemiak.online.service.data;

import com.zemiak.online.model.Outage;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import javax.ejb.Stateless;


@Stateless
public class OutageService {

    @Inject
    private EntityManager em;

    public List<Outage> all() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Outage> criteria = cb.createQuery(Outage.class);
        Root<Outage> member = criteria.from(Outage.class);
        criteria.select(member).orderBy(cb.desc(member.get("start")));
        return em.createQuery(criteria).getResultList();
    }
}
