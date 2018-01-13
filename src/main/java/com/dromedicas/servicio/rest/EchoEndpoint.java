package com.dromedicas.servicio.rest;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.dromedicas.web.filter.JWTTokenNeeded;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/echo")
@Produces(TEXT_PLAIN)
public class EchoEndpoint {
	
	@GET
    public Response echo(@QueryParam("message") String message) {
        return Response.ok().entity(message == null ? "no message" : message).build();
    }

    @GET
    @Path("jwt")
    @JWTTokenNeeded
    public Response echoWithJWTToken(@QueryParam("message") String message) {
        return Response.ok().entity(message == null ? "no message" : message).build();
    }

}
