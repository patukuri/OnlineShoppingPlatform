package com.securecoding.onelineshoppingplatform.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	

	@Column(name="first_name",nullable = false, length = 20)
    @NotBlank(message = "Username is Invalid")
	@Size(min = 2, message = "user name should have at least 2 characters")
	private String firstName;
	@Column(name="last_name")
	private @NotNull  String lastName;
	@Column(name="email")
	private @NotNull String email;
	@Column(name="password")
	private @NotNull String password;
	@Column
	private @NotNull String role;
	@Column
	private int isConfirmed =0;
	public int isConfirmed() {
		return isConfirmed;
	}
	public void setConfirmed(int isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public User() {
		
	}
	public User(String firstName, String lastName, String email, String password,String role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role=role;
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
