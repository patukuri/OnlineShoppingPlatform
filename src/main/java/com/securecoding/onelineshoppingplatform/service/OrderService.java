package com.securecoding.onelineshoppingplatform.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securecoding.onelineshoppingplatform.Model.Order;
import com.securecoding.onelineshoppingplatform.Model.OrderItem;
import com.securecoding.onelineshoppingplatform.Model.Product;
import com.securecoding.onelineshoppingplatform.Model.User;
import com.securecoding.onelineshoppingplatform.repository.OrderItemRepository;
import com.securecoding.onelineshoppingplatform.repository.OrderRepository;
import com.securecoding.onelineshoppingplatform.repository.ProductRepository;
import com.securecoding.onlineshoppingplatform.dto.CartDto;
@Service
@Transactional
public class OrderService {
	@Autowired
    private CartService cartService;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderItemRepository orderItemsRepository;
	public void placeOrder(User user) throws Exception {
        // first let get cart items for the user
        CartDto cartDto = cartService.listCartItems(user);

        List<CartItemDto> cartItemDtoList = cartDto.getCartItems();

        // create the order and save it
        Order newOrder = new Order();
        newOrder.setCreatedDate(new Date());
      //  newOrder.setSessionId(sessionId);
        newOrder.setUser(user);
        newOrder.setTotalPrice(cartDto.getTotalCost());
        orderRepository.save(newOrder);

        for (CartItemDto cartItemDto : cartItemDtoList) {
            // create orderItem and save each one
        	 Optional<Product> optionalProduct = productRepository.findById(cartItemDto.getProduct().getId());
             // throw an exception if product does not exists
             if (!optionalProduct.isPresent()) {
                 throw new Exception("product not present");
             }
            OrderItem orderItem = new OrderItem();
            orderItem.setCreatedDate(new Date());
            orderItem.setPrice(cartItemDto.getProduct().getPrice());
            orderItem.setProduct(cartItemDto.getProduct());
            orderItem.setQuantity(cartItemDto.getQuantity());
            orderItem.setOrder(newOrder);
            // add to order item list
            orderItemsRepository.save(orderItem);
            
           
            Product product = optionalProduct.get();
            product.setQuantity(product.getQuantity()-cartItemDto.getQuantity());
            productRepository.save(product);
        }
        
       

        //
        cartService.deleteUserCartItems(user);
    }
	/*
	 * public List<Order> viewOrders(User user) { List<Order>
	 * orders=orderRepository.findAll(); return orders;
	 * 
	 * }
	 */
	 public List<Order> listOrders(User user) {
		 System.out.println(user.getId()+"********");
        return orderRepository.findAllByUserOrderByCreatedDateDesc(user);
    }
}
