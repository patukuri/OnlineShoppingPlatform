package com.securecoding.onelineshoppingplatform.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securecoding.onelineshoppingplatform.Model.Cart;
import com.securecoding.onelineshoppingplatform.Model.Product;
import com.securecoding.onelineshoppingplatform.Model.User;
import com.securecoding.onelineshoppingplatform.exception.CustomException;
import com.securecoding.onelineshoppingplatform.exception.ProductNotExistsException;
import com.securecoding.onelineshoppingplatform.repository.CartRepository;
import com.securecoding.onlineshoppingplatform.dto.AddtoCartDto;
import com.securecoding.onlineshoppingplatform.dto.CartDto;

@Service
public class CartService {

    @Autowired
    ProductService productService;

    @Autowired
    CartRepository cartRepository;

    public void addToCart(AddtoCartDto addToCartDto, User user) throws ProductNotExistsException {

        // validate if the product id is valid
        Product product = productService.findById(addToCartDto.getProductId());

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());


        // save the cart
        cartRepository.save(cart);

    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart: cartList) {
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }

        CartDto cartDto = new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItems);
        return  cartDto;
    }

    public void deleteUserCartItems(User user) {
        cartRepository.deleteByUser(user);
    }
}
