package com.zemiak.online.service.ui.resources;

import com.zemiak.online.model.DataTablesAjaxData;
import com.zemiak.online.model.OutageDTO;
import com.zemiak.online.service.data.OutageService;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
    public DataTablesAjaxData<OutageDTO> all() {
        return new DataTablesAjaxData<>(service.all().stream().map(e -> new OutageDTO(e)).collect(Collectors.toList()));
    }
}
