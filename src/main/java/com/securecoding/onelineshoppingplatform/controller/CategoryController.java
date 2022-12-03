package com.securecoding.onelineshoppingplatform.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.securecoding.onelineshoppingplatform.Model.AuthenticationToken;
import com.securecoding.onelineshoppingplatform.Model.Category;
import com.securecoding.onelineshoppingplatform.Model.Product;
import com.securecoding.onelineshoppingplatform.Model.User;
import com.securecoding.onelineshoppingplatform.common.ApiResponse;
import com.securecoding.onelineshoppingplatform.exception.CustomException;
import com.securecoding.onelineshoppingplatform.service.AuthenticationService;
import com.securecoding.onelineshoppingplatform.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	AuthenticationService authenticationService;
	 private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
	@PostMapping("/create")
	public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category, @RequestParam String token) {
		authenticationService.authenticate(token);
		User userfromDB = authenticationService.getUser(token);
		if (!userfromDB.getRole().equalsIgnoreCase("ADMIN")) {
			throw new CustomException(userfromDB.getFirstName() + "you are not authorized to Add Categories");
		}
		
		
		if (Objects.nonNull(categoryService.readCategory(category.getCategoryName()))) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category already exists"),
					HttpStatus.CONFLICT);
		}
		categoryService.createCategory(category);
		return new ResponseEntity<>(new ApiResponse(true, "Created a new Category"), HttpStatus.CREATED);
	}

	@GetMapping("/list")
	public ResponseEntity<List<Category>> listCategory(@RequestHeader String token) {
		authenticationService.authenticate(token);
		User userfromDB = authenticationService.getUser(token);
		List<Category> body = categoryService.listCategory();
		// return new ResponseEntity<>(category, HttpStatus.OK);
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	@PostMapping("/update/{categoryId}")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") int categoryId,
			@RequestBody Category category, @RequestHeader String token) {
		authenticationService.authenticate(token);
		User userfromDB = authenticationService.getUser(token);
		if (!userfromDB.getRole().equalsIgnoreCase("ADMIN")) {
			throw new CustomException(userfromDB.getFirstName() + "you are not authorized to Add Categories");
		}
		Optional<Category> optionalCategory = categoryService.readCategory(categoryId);
		if (optionalCategory.isPresent()) {
			// If the category exists then update it.
			categoryService.edit(categoryId, category);
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "updated the category"), HttpStatus.OK);
		}
		return new ResponseEntity<>(new ApiResponse(false, "category does not exist"), HttpStatus.NOT_FOUND);
	}

}