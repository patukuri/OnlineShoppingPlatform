package com.securecoding.onelineshoppingplatform.Model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="tokens")
public class AuthenticationToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	private String token;
	@Column(name="created_date")
	private Date createdDate;
	
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name="user_id")
	private User user;
	
	
	  public LocalDateTime getExpiredDate() { return expiredDate; } public void
	  setExpiredDate(LocalDateTime expiredDate) { this.expiredDate = expiredDate; }
	  
	  
	  @Column(name="expiry_date")
	  private LocalDateTime expiredDate;
	 
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public AuthenticationToken(User user) {
		
		this.user = user;
		
		this.createdDate= new Date();
		this.token= UUID.randomUUID().toString();
		this.expiredDate=LocalDateTime.now().plusMinutes(60);
		
	}
	
	
	  public AuthenticationToken() { 
		  super();
	   }
	 
	
	
	
	
	

}