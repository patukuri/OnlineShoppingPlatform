package com.securecoding.onelineshoppingplatform.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.securecoding.onelineshoppingplatform.Model.Order;
import com.securecoding.onelineshoppingplatform.Model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	List<Order> findAllByUserOrderByCreatedDateDesc(User user);
	
	List<Order> findByUserId(int userId);

	List<Order> findAllById(Integer id);
	



}
