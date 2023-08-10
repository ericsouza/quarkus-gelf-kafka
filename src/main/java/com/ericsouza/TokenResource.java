package com.ericsouza;

import java.util.Optional;

import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/token")
public class TokenResource {

	private static final Logger LOG = Logger.getLogger(TokenResource.class);

	@Inject
	private TokenService service;

	@POST
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Token create(NewTokenRequest request) {
        LOG.info("Log INFO level");
        return service.createToken(request.getEmail());
    }

	@GET
	@Path("/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Token getByToken(@PathParam(value = "token") String token) {
        Optional<Token> tokenObj = service.getByToken(token, "email@email.com");
        if(!tokenObj.isPresent()) throw new NotFoundException();
        
        return tokenObj.get();
    }
	
	@PATCH
	@Path("/{token}/verify")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifyToken(@PathParam(value = "token") String token) {
		Optional<VerificationResultStatus> verificationResult = service.verify(token, "email@email.com");
		if (!verificationResult.isPresent()) throw new NotFoundException();
		
		VerificationResultStatus verificationResultStatus = verificationResult.get();
		
		if (VerificationResultStatus.EXPIRED.equals(verificationResultStatus)) {
			throw new WebApplicationException(Response.status(422).build());
		}

		return Response.noContent().build();
    }

}
