package com.securecoding.onelineshoppingplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securecoding.onelineshoppingplatform.Model.AuthenticationToken;
import com.securecoding.onelineshoppingplatform.Model.User;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {
	
	
	  AuthenticationToken findByUser(User user);
	  AuthenticationToken findByToken(String token);

}
