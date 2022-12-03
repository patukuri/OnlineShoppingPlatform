package com.securecoding.onelineshoppingplatform.Model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;


@Entity
public class ConfirmationToken {

    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;

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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}

	public void setConfirmedAt(LocalDateTime confirmedAt) {
		this.confirmedAt = confirmedAt;
	}

	public void setAppUser(User appUser) {
		this.appUser = appUser;
	}

	@Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id")
    
    private User appUser;

   

	public Object getConfirmedAt() {
		// TODO Auto-generated method stub
		return confirmedAt;
	}

	public LocalDateTime getExpiresAt() {
		// TODO Auto-generated method stub
		return expiresAt;
	}


	public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt,
			 User appUser) {
		super();
		
		this.token = token;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
		
		this.appUser = appUser;
	}

	public User getAppUser() {
		// TODO Auto-generated method stub
		return appUser;
	}

	public ConfirmationToken() {
		super();
	}
}
