package com.ericsouza;


import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TokenRepository implements PanacheRepository<Token> {

	public Optional<Token> findByToken(String token, String email){
		return find("token = ?1 and email = ?2 and verified=false", token, email).firstResultOptional();
	}
}
