package com.ericsouza;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Token {
	private Long id;
    private String token;
    private String email;

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
}
