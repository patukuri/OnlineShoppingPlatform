package com.securecoding.onelineshoppingplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securecoding.onelineshoppingplatform.Model.Cart;
import com.securecoding.onelineshoppingplatform.Model.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
    void deleteByUser(User user);
    
}
