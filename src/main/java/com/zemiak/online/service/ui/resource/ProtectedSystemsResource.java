package com.zemiak.online.service.ui.resource;

import com.zemiak.online.model.DataTablesAjaxData;
import com.zemiak.online.model.ProtectedSystemDTO;
import com.zemiak.online.service.ProtectedSystemService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("protected-systems")
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public class ProtectedSystemsResource {
    @Inject
    private ProtectedSystemService service;

    @GET
    public DataTablesAjaxData<ProtectedSystemDTO> all() {
        return new DataTablesAjaxData<>(service.all().stream().map(ProtectedSystemDTO::new).collect(Collectors.toList()));
    }

    @GET
    @Path("last-refresh")
    public String getLastRefreshDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
    }
}
