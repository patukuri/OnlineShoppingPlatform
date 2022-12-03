package com.securecoding.onelineshoppingplatform.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.securecoding.onelineshoppingplatform.Model.Order;
import com.securecoding.onelineshoppingplatform.Model.User;
import com.securecoding.onelineshoppingplatform.common.ApiResponse;
import com.securecoding.onelineshoppingplatform.exception.AuthenticationFailException;
import com.securecoding.onelineshoppingplatform.repository.OrderRepository;
import com.securecoding.onelineshoppingplatform.service.AuthenticationService;
import com.securecoding.onelineshoppingplatform.service.OrderService;


@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthenticationService authenticationService;
    
    @Autowired
    private OrderRepository orderRepo;
    
    
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam("token") String token)
            throws Exception {
        // validate token
        authenticationService.authenticate(token);
        // retrieve user
        User user = authenticationService.getUser(token);
        // place the order
        orderService.placeOrder(user);
        return new ResponseEntity<>(new ApiResponse(true, "Order has been placed"), HttpStatus.CREATED);
    }
    
    @GetMapping("/view/orders")
    public ResponseEntity<Object> getAllOrders(@RequestParam("token") String token) throws AuthenticationFailException {
        // validate token
        authenticationService.authenticate(token);
        // retrieve user
        User user = authenticationService.getUser(token);
        // get orders
        List<Order> orderDtoList = orderService.listOrders(user);
        List<JSONObject> entities = new ArrayList<JSONObject>();
        //Order order=orderDtoList.get();
        for (Order order : orderDtoList) {
        	JSONObject entity = new JSONObject();
        	entity.put("id", order.getId());
        	entity.put("value", order.getTotalPrice());
        	entities.add(entity);
		}
        
        return null;
    }
}
