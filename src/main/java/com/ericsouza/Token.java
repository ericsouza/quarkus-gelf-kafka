package com.ericsouza;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Token {
	@Column(name = "id")
	private Long id;
	@Column(name = "token")
    private String token;
    @Column(name = "email")
    private String email;
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    @Column(name = "verified")
    private boolean verified = false;

    @Id
    @SequenceGenerator(name = "tokenSeq", sequenceName = "token_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "tokenSeq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
}
