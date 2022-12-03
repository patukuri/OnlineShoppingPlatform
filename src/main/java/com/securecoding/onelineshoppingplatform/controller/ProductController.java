package com.securecoding.onelineshoppingplatform.controller;

import java.util.List;
import java.util.Optional;

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

import com.securecoding.onelineshoppingplatform.Model.Category;
import com.securecoding.onelineshoppingplatform.Model.Product;
import com.securecoding.onelineshoppingplatform.Model.ProductDto;
import com.securecoding.onelineshoppingplatform.Model.User;
import com.securecoding.onelineshoppingplatform.common.ApiResponse;
import com.securecoding.onelineshoppingplatform.exception.CustomException;
import com.securecoding.onelineshoppingplatform.repository.CategoryRepository;
import com.securecoding.onelineshoppingplatform.service.AuthenticationService;
import com.securecoding.onelineshoppingplatform.service.CategoryService;
import com.securecoding.onelineshoppingplatform.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	CategoryRepository categoryRepo;
	@Autowired
	AuthenticationService authenticationService;

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDto productDto, @RequestParam String token) {
		authenticationService.authenticate(token);
		User userfromDB = authenticationService.getUser(token);

		if (!userfromDB.getRole().equalsIgnoreCase("SELLER")  ) {
			throw new CustomException(userfromDB.getFirstName() + "you are not authorized to Addproducts");
		}
		Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
		if (!optionalCategory.isPresent()) {
			return new ResponseEntity<>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
		}
		Category category = optionalCategory.get();
		productService.createProduct(productDto, category,token);
		return new ResponseEntity<>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
	}

	// list all the products
	@GetMapping("/")
	public ResponseEntity<List<ProductDto>> getProducts(@RequestHeader String token) {
		authenticationService.authenticate(token);
		User userfromDB = authenticationService.getUser(token);
		if (userfromDB.getRole().equalsIgnoreCase("SELLER")) {
			throw new CustomException(userfromDB.getFirstName() + "you are not authorized to See all the products as Seller,"
					+ " PLease login as a user");
			
		}
		List<ProductDto> productDtos = productService.getAllProducts();
		return new ResponseEntity<>(productDtos, HttpStatus.OK);
	}

	@PostMapping("/update/{productId}")
	public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Integer productId,
			@RequestBody ProductDto productDto, @RequestHeader String token) throws Exception {
		authenticationService.authenticate(token);
		User userfromDB = authenticationService.getUser(token);
		if (!userfromDB.getRole().equalsIgnoreCase("SELLER")) {
			throw new CustomException(userfromDB.getFirstName() + "you are not authorized to Addproducts");
		}
		if (userfromDB.getRole().equals("SELLER")){
			Product product=productService.findById(productId);
			if(product.getSellerId()!=userfromDB.getId()) {
				throw new CustomException("you are not authorized to allowed this item as you are not the owner of this item");
			}
		}
		Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
		if (!optionalCategory.isPresent()) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category does not exists"),
					HttpStatus.BAD_REQUEST);
		}
		productService.updateProduct(productDto, productId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "product has been updated"), HttpStatus.OK);
	}

	@GetMapping("/view/{productId}")
    public ResponseEntity<Product> viewProduct(@PathVariable("productId") Integer productId, @RequestHeader String token) throws Exception {
    	authenticationService.authenticate(token);
		User userfromDB = authenticationService.getUser(token);
		
		if (userfromDB.getRole().equals("SELLER")){
			Product product=productService.findById(productId);
			if(product.getSellerId()!=userfromDB.getId()) {
				throw new CustomException("you are not authorized to view this item as youa re not the owner of this item");
			}
		}
		
        Product product=productService.viewProduct(productId);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

}