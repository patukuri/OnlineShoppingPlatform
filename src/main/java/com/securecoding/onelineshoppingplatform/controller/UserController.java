package com.securecoding.onelineshoppingplatform.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.securecoding.onelineshoppingplatform.Model.Order;
import com.securecoding.onelineshoppingplatform.Model.OrderItem;
import com.securecoding.onelineshoppingplatform.Model.Product;
import com.securecoding.onelineshoppingplatform.Model.User;
import com.securecoding.onelineshoppingplatform.common.ApiResponse;
import com.securecoding.onelineshoppingplatform.exception.CustomException;
import com.securecoding.onelineshoppingplatform.repository.ConfirmationTokenRepo;
import com.securecoding.onelineshoppingplatform.repository.OrderItemRepository;
import com.securecoding.onelineshoppingplatform.repository.OrderRepository;
import com.securecoding.onelineshoppingplatform.repository.UserRepository;
import com.securecoding.onelineshoppingplatform.service.AuthenticationService;
import com.securecoding.onelineshoppingplatform.service.UserService;
import com.securecoding.onlineshoppingplatform.dto.SignInDto;
import com.securecoding.onlineshoppingplatform.dto.SignResponseDto;
import com.securecoding.onlineshoppingplatform.dto.SignUpDto;
import com.securecoding.onlineshoppingplatform.dto.UserDto;
import com.securecoding.onlineshoppingplatform.dto.UserResponseDto;
//import com.securecoding.onlineshoppingplatform.validator.UserValidator;

@RequestMapping("user")
@RestController
public class UserController {
	
	@Autowired
	
	UserService userService;
	@Autowired
	AuthenticationService authenticationService;
	@Autowired
	OrderRepository orderRepo;
	@Autowired
	OrderItemRepository orderItemRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	ConfirmationTokenRepo confirmationToken;
	@Autowired
	OrderItemRepository orderItemRepository;

	//UserValidator userValidator = new UserValidator();
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@PostMapping("/signup")
	public UserResponseDto signup(@RequestBody SignUpDto signUpDto) throws NoSuchAlgorithmException {

		//userValidator.validateuser(signUpDto);

		return userService.signup(signUpDto);
	}

	@PostMapping("/signin")
	public SignResponseDto signIn(@RequestBody SignInDto signInDto) {
		//userValidator.validateuser(signInDto);
		return userService.signin(signInDto);
	}

	@GetMapping("/signout")
	public ResponseEntity<SignResponseDto> signout(@RequestHeader String token) {

		return userService.signout(token);
	}

	@PostMapping("/edituser")
	public User editUser(@RequestBody UserDto user, @RequestHeader String token) {
		authenticationService.authenticate(token);
		User userfromDB = authenticationService.getUser(token);
		return userService.editUserDetails(userfromDB, user);
	}

	@GetMapping("/viewAccount")
	public User viewAccount(@RequestHeader String token) {
		authenticationService.authenticate(token);
		User userfromDB = authenticationService.getUser(token);
		return userfromDB;
	}

	@GetMapping("/vieworders")
	public List<Map<String, String>> viewAllOrders(@RequestParam String token) {
		authenticationService.authenticate(token);
		User userfromDB = authenticationService.getUser(token);
		if (!userfromDB.getRole().equalsIgnoreCase("USER")) {
			throw new CustomException(userfromDB.getFirstName() + "you are not authorized to Add Orders");
		}
		//System.out.println();

		// List<Order> orders = orderRepo.findByUserId(userfromDB.getId());

		List<OrderItem> orderItems = orderItemRepository.findAll();

		List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
		Map<String, String> hashmap = null;
		for (OrderItem orderItem : orderItems) {
			hashmap = new HashMap<>();
			hashmap.put("OrderId", orderItem.getOrder().getId().toString());
			hashmap.put("Quantity", Integer.toString(orderItem.getProduct().getQuantity()));
			hashmap.put("Category", orderItem.getProduct().getCategory().getCategoryName());
			hashmap.put("Image", orderItem.getProduct().getImageURL());

			hashmap.put("Description", orderItem.getProduct().getDescription());

			hashmap.put("Name", orderItem.getProduct().getName());
			hashmap.put("Price", Double.toString(orderItem.getProduct().getPrice()));

			ls.add(hashmap);
		}
		return ls;
	}

	@GetMapping("/veiwRevenue")
	public Map<String, Double> veiwRevenue(@RequestParam String token) {
		authenticationService.authenticate(token);
		User userfromDB = authenticationService.getUser(token);
		if (!userfromDB.getRole().equalsIgnoreCase("ADMIN")) {
			throw new CustomException(userfromDB.getFirstName() + "you are not authorized to REVENVUE");
		}
		System.out.println();

		// List<Order> orders = orderRepo.findByUserId(userfromDB.getId());

		List<Order> orderItems = orderRepo.findAll();

		Map<String, Double> ls = new HashMap<String, Double>();
		Map<String, Double> hashmap = null;
		double totalsum = 0;
		for (Order order : orderItems) {
			// hashmap = new HashMap<>();
			totalsum = totalsum + order.getTotalPrice();

		}

		ls.put("TotalRevenue", totalsum);

		/*
		 * for (Order order : orders) {
		 * 
		 * System.out.println(order.getOrderItems()); }
		 */

		return ls;
	}

	@GetMapping("/viewUserOrder")
	public List<Map<String, String>> viewOrdersofUser(@RequestParam String token) {
		authenticationService.authenticate(token);
		User userfromDB = authenticationService.getUser(token);
		if (!userfromDB.getRole().equalsIgnoreCase("USER")) {
			throw new CustomException(userfromDB.getFirstName() + "you are not authorized to View Orders");
		}
		//System.out.println();

		// List<Order> orders = orderRepo.findByUserId(userfromDB.getId());
		Map<String, String> map = null;
		List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
		List<Order> orders = orderRepo.findAll();
		for (Order order : orders) {
			// orderItemRepo.fi

			List<OrderItem> list = orderItemRepo.findAll();

			if (userfromDB.getId() == order.getUser().getId()) {
				map = new HashMap<String, String>();
				map.put("order", order.getId().toString());
				map.put("price", order.getTotalPrice().toString());
				listmap.add(map);

			}

		}
		/*
		 * List<OrderItem> orderItems = orderItemRepository.findAll(); int userId =
		 * userfromDB.getId(); Map<String, Double> ls = new HashMap<String, Double>();
		 * Map<String, Double> hashmap = null; double totalsum = 0; for (Order order :
		 * orderItems) { // hashmap = new HashMap<>(); totalsum = totalsum +
		 * order.getTotalPrice();
		 * 
		 * }
		 * 
		 * ls.put("TotalRevenue", totalsum);
		 * 
		 * 
		 * for (Order order : orders) {
		 * 
		 * System.out.println(order.getOrderItems()); }
		 */

		return listmap;
	}

	@GetMapping("/itemsearch")
	public List<Product> itemSearch(@RequestParam String item) {
		// authenticationService.authenticate(token);
		// userfromDB = authenticationService.getUser(token);

		return userService.getItems(item);
	}

	@GetMapping(path = "registration/confirm")
	public String confirm(@RequestParam("token") String token) {
		System.out.println(token);
		return userService.confirmToken(token);
	}

	/*
	 * @DeleteMapping(path = "delete/user") public ApiResponse
	 * deleteUser(@RequestParam("token") String token, @RequestHeader int userId) {
	 * authenticationService.authenticate(token); User userfromDB =
	 * authenticationService.getUser(token); if
	 * (!userfromDB.getRole().equalsIgnoreCase("ADMIN")) {
	 * LOGGER.debug(userfromDB.getFirstName() +
	 * "you are not authorized to Delete USers"); throw new
	 * CustomException(userfromDB.getFirstName() +
	 * "you are not authorized to Delete USers"); } Optional<User> user =
	 * userRepo.findById(userId); User users = user.get(); //
	 * confirmationToken.update(userId); userRepo.delete(users); //
	 * LOGGER.debug(msg); return new ApiResponse(true,
	 * "user Deleted from the Platform"); }
	 */

	/*
	 * @GetMapping("/getallItems") public List<Product> getItems(@RequestParam
	 * String item){ //authenticationService.authenticate(token); // userfromDB =
	 * authenticationService.getUser(token);
	 * 
	 * return userService.searchItems(item); }
	 */

}
