package com.zemiak.online.service;

import com.zemiak.online.model.Outage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OutageServiceTest {
    private static final long ONE_HOUR = 1000 * 3600;
    private static final long ONE_DAY = OutageService.ONE_DAY;

    public OutageServiceTest() {
    }

    OutageService service;
    long now;

    @Before
    public void setUp() {
        service = new OutageService();
        now = new Date().getTime();
        service.em = mockEm();
    }

    @Test
    public void findLastMonth() throws Exception {
        Assert.assertEquals(2, service.findLastMonth(1L).size());
    }

    @Test
    public void findLastYear() throws Exception {
        Assert.assertEquals(2, service.findLastYear(1L).size());
    }

    @Test
    public void findEndedLastDay() throws Exception {
        Assert.assertEquals(0, service.findEndedLastDay(1L).size());
    }

    private EntityManager mockEm() {
        EntityManager em = mock(EntityManager.class);

        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(getOutages());
        when(mockedQuery.setParameter(Matchers.anyString(), Matchers.anyObject())).thenReturn(mockedQuery);
        when(em.createNamedQuery("Outage.findAllBySystem")).thenReturn(mockedQuery);

        return em;
    }

    private List<Outage> getOutages() {
        Outage o1 = new Outage();
        o1.setId(1L);
        o1.setStart(new Date(now - ONE_DAY * 15));
        o1.setEnd(new Date(now - ONE_DAY * 10));

        Outage o2 = new Outage();
        o2.setId(2L);
        o2.setStart(new Date(now - ONE_DAY * 400));
        o2.setEnd(new Date(now - ONE_DAY * 395));

        Outage o3 = new Outage();
        o3.setId(3L);
        o3.setStart(new Date(now - ONE_DAY + ONE_HOUR));

        List<Outage> res = new ArrayList<>();
        res.add(o1);
        res.add(o2);
        res.add(o3);

        return res;
    }

}