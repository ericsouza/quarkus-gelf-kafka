package com.ericsouza;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TokenService {
	@Inject
	private TokenRepository repository;
	
	@Transactional
	public Token createToken(String email) {
		Token token = new Token();
		token.setEmail(email);
		token.setToken(getRandomToken());
		token.setExpiresAt(LocalDateTime.now().plusMinutes(1));
		repository.persist(token);
		return token;
	}
	
	public Optional<Token> getByToken(String token, String email) {
		return repository.findByToken(token, email);
	}
	
	@Transactional
	public Optional<VerificationResultStatus> verify(String tokenStr, String email) {
		Optional<Token> token = getByToken(tokenStr, email);
		if (!token.isPresent()) return Optional.empty();
		if (isExpired(token.get())) return Optional.of(VerificationResultStatus.EXPIRED);
		token.get().setVerified(true);
		repository.persist(token.get());
		return Optional.of(VerificationResultStatus.SUCCESS);
	}
	
	private boolean isExpired(Token token) {
		return token.getExpiresAt().isBefore(LocalDateTime.now());
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
	      .toString().toUpperCase();
	}
}
