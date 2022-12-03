package com.securecoding.onelineshoppingplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securecoding.onelineshoppingplatform.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	User findByEmail(String email);

	//User  findByToken(String token);
}
