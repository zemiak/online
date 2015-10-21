package com.zemiak.online.service;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("pings")
public class PingsResource {
    @Inject
    private PingsControl control;

    @POST
    public Response pingService(@QueryParam("service") @NotNull String service) {
        control.ping(service);

        return Response.ok().build();
    }
}
