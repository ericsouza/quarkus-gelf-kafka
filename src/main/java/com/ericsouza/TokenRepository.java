package com.ericsouza;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TokenRepository implements PanacheRepository<Token>{
	public Uni<Token> findByToken(String token){
		return find("token", token).firstResult();
	}
}
