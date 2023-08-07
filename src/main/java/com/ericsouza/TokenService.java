package com.ericsouza;

import java.util.Random;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TokenService {
	@Inject
	private TokenRepository repository;

	@WithTransaction
	public Uni<Token> createToken(String email) {
		Token token = new Token();
		token.setEmail(email);
		token.setToken(getRandomToken());
		return repository.persist(token);
	}
	
	@WithSession
	public Uni<Token> getByToken(String token) {
		return repository.findByToken(token);
	}

	private String getRandomToken() {
		int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 4;
	    Random random = new Random();

	    return random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	}
}
