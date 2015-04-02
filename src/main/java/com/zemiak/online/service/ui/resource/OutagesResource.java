package com.zemiak.online.service.ui.resource;

import com.zemiak.online.model.DataTablesAjaxData;
import com.zemiak.online.model.Outage;
import com.zemiak.online.model.OutageDTO;
import com.zemiak.online.service.OutageService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("outages")
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public class OutagesResource {
    @Inject
    private OutageService service;

    @GET
    @Path("{id}")
    public DataTablesAjaxData<OutageDTO> all(@PathParam("id") Integer id) {
        List<Outage> outages = service.findByProtectedSystem(id);

        List<Outage> sorted = outages.stream().filter(outage -> null != outage.getEnd()).collect(Collectors.toList());
        Collections.sort(sorted, (Outage o1, Outage o2) -> o1.getStart().compareTo(o2.getStart()));
        outages.stream().filter(outage -> null == outage.getEnd()).findFirst().ifPresent(outage -> sorted.add(0, outage));

        return new DataTablesAjaxData<>(sorted.stream().map(e -> new OutageDTO(e)).collect(Collectors.toList()));
    }
}
