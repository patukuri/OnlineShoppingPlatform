package com.securecoding.onelineshoppingplatform.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.securecoding.onelineshoppingplatform.Model.User;
import com.securecoding.onelineshoppingplatform.common.ApiResponse;
import com.securecoding.onelineshoppingplatform.exception.ProductNotExistsException;
import com.securecoding.onelineshoppingplatform.service.AuthenticationService;
import com.securecoding.onelineshoppingplatform.service.CartService;
import com.securecoding.onlineshoppingplatform.dto.AddtoCartDto;
import com.securecoding.onlineshoppingplatform.dto.CartDto;
//import com.securecoding.onlineshoppingplatform.validator.UserValidator;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private AuthenticationService authenticationService;
	//UserValidator userValidator= new UserValidator();
	private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

	// post cart api
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addToCart(@RequestBody AddtoCartDto addToCartDto,
			@RequestHeader("token") String token) {
		// authenticate the token
		authenticationService.authenticate(token);

		// find the user

		User user = authenticationService.getUser(token);
		//userValidator.validateAddtoCart(addToCartDto);
		try {
			cartService.addToCart(addToCartDto, user);
		} catch (ProductNotExistsException e) {
			// TODO Auto-generated catch block
			LOGGER.debug("Product Doesn't exist", e);
			e.printStackTrace();
		}

		return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
	}

	// get all cart items for a user
	@GetMapping("/")
	public ResponseEntity<CartDto> getCartItems(@RequestHeader("token") String token) {
		// authenticate the token
		authenticationService.authenticate(token);

		// find the user
		User user = authenticationService.getUser(token);

		// get cart items

		CartDto cartDto = cartService.listCartItems(user);
		return new ResponseEntity<>(cartDto, HttpStatus.OK);
	}

	// delete a cart item for a user
	/*
	 * @DeleteMapping("/delete/{cartItemId}") public ResponseEntity<ApiResponse>
	 * deleteCartItem(@PathVariable("cartItemId") Integer itemId,
	 * 
	 * @RequestParam("token") String token) {
	 * 
	 * // authenticate the token authenticationService.authenticate(token);
	 * 
	 * // find the user User user = authenticationService.getUser(token);
	 * 
	 * cartService.deleteCartItem(itemId, user);
	 * 
	 * return new ResponseEntity<>(new ApiResponse(true, "Item has been removed"),
	 * HttpStatus.OK);
	 * 
	 * }
	 */

}