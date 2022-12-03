	package com.securecoding.onlineshoppingplatform.dto;
	
	import javax.validation.constraints.NotBlank;
	import javax.validation.constraints.Size;
	
	public class SignUpDto {
		
		@NotBlank(message = "Username is Invalid")
		@Size(min = 2, message = "user name should have at least 2 characters")
		private String firstName;
		
		private String lastName;
		
		private String email;
		
		private String password;
		
		private String role;
	
		public String getRole() {
			return role;
		}
	
		public void setRole(String role) {
			this.role = role;
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
