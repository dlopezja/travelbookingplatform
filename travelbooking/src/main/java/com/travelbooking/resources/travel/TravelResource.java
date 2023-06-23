package com.travelbooking.resources.travel;

import io.dropwizard.hibernate.UnitOfWork;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

@Path("/travels")
@Produces(MediaType.APPLICATION_JSON)
public class TravelResource {

    @POST
    @UnitOfWork
    public Response sendMessage() {
        Map<String, String> response = new HashMap<>();
        return Response.ok(response).build();
    }
}
