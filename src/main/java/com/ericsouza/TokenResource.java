package com.ericsouza;

import jakarta.inject.Inject;

import org.jboss.logging.Logger;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/token")
public class TokenResource {
	
	private static final Logger LOG = Logger.getLogger(TokenResource.class);
	
	@Inject
	private TokenService service;
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Uni<Token> create(NewTokenRequest request) {
        LOG.info("Log INFO level");
        return service.createToken(request.getEmail());
    }
	
	@GET()
	@Path("/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Token> getByToken(@PathParam(value = "token") String token) {
        LOG.info("Log INFO level");
        return service.getByToken(token);
    }

}
