package com.securecoding.onelineshoppingplatform.service;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securecoding.onelineshoppingplatform.Model.AuthenticationToken;
import com.securecoding.onelineshoppingplatform.Model.User;
import com.securecoding.onelineshoppingplatform.exception.AuthenticationFailException;
import com.securecoding.onelineshoppingplatform.exception.CustomException;
import com.securecoding.onelineshoppingplatform.repository.TokenRepository;

@Service
public class AuthenticationService {

	
	@Autowired
	TokenRepository tokenRepository;
	
	public void saveToken(AuthenticationToken authToken) {
		
		tokenRepository.save(authToken);
		
		
	}
	 public AuthenticationToken getToken(User user) {
	        return tokenRepository.findByUser(user);
	    }
	 public User getUser(String token) {
		 System.out.println();
	         AuthenticationToken authenticationToken = tokenRepository.findByToken(token);
	         //System.out.println("+++++"+authenticationToken.getToken());
	         if(Objects.isNull(authenticationToken)) {
		            throw new CustomException("Invalid token used, please try to Login again or use Correct token");
		            
		        }
				  LocalDateTime expiredAts = authenticationToken.getExpiredDate();
				  
				  if (expiredAts.isBefore(LocalDateTime.now())) {
				  tokenRepository.deleteById(authenticationToken.getId()); 
				  throw new
				  CustomException("Token Life Time expired, So Please Login again"); }
				 
	        
	        // authenticationToken is not null
	        return authenticationToken.getUser();
	    }

	public void authenticate(String token) {
		// TODO Auto-generated method stub
		
	    if(Objects.isNull(token) ) {
            // throw an exception
	    	
            throw new AuthenticationFailException("token not present");
        }
        if(Objects.isNull(getUser(token))) {
            throw new AuthenticationFailException("token not valid");
        }
        
    }
	

}
