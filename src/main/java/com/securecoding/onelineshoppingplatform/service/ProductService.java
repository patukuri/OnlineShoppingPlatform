package com.securecoding.onelineshoppingplatform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securecoding.onelineshoppingplatform.Model.Category;
import com.securecoding.onelineshoppingplatform.Model.Product;
import com.securecoding.onelineshoppingplatform.Model.ProductDto;
import com.securecoding.onelineshoppingplatform.Model.User;
import com.securecoding.onelineshoppingplatform.exception.CustomException;
import com.securecoding.onelineshoppingplatform.exception.ProductNotExistsException;
import com.securecoding.onelineshoppingplatform.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	AuthenticationService authenticationService;
	    @Autowired
	    ProductRepository productRepository;
	

	    public void createProduct(ProductDto productDto, Category category, String token) {
	        authenticationService.authenticate(token);
			User userfromDB = authenticationService.getUser(token);
	        Product product = new Product();
	        product.setDescription(productDto.getDescription());
	        product.setImageURL(productDto.getImageURL());
	        product.setName(productDto.getName());
	        product.setCategory(category);
	        product.setPrice(productDto.getPrice());
	        product.setQuantity(productDto.getQuantity());
	        product.setSellerId(userfromDB.getId());
	        productRepository.save(product);
	    }

	    public ProductDto getProductDto(Product product) {
	        ProductDto productDto = new ProductDto();
	        productDto.setDescription(product.getDescription());
	        productDto.setImageURL(product.getImageURL());
	        productDto.setName(product.getName());
	        productDto.setCategoryId(product.getCategory().getId());
	        productDto.setPrice(product.getPrice());
	        productDto.setId(product.getId());
	        productDto.setQuantity(productDto.getQuantity());
	        return productDto;
	    }

	    public List<ProductDto> getAllProducts() {
	        List<Product> allProducts = productRepository.findAll();
	        if(allProducts.size()==0) {
	        	throw new CustomException("Sorry , We don't have Any items in stock");
	        }
	        List<ProductDto> productDtos = new ArrayList<>();
	        for(Product product: allProducts) {
	            productDtos.add(getProductDto(product));
	        }
	        return productDtos;
	    }

	    public void updateProduct(ProductDto productDto, Integer productId) throws Exception {
	        Optional<Product> optionalProduct = productRepository.findById(productId);
	        // throw an exception if product does not exists
	        if (!optionalProduct.isPresent()) {
	            throw new Exception("product not present");
	        }
	        Product product = optionalProduct.get();
	        product.setDescription(productDto.getDescription());
	        product.setImageURL(productDto.getImageURL());
	        product.setName(productDto.getName());
	        product.setPrice(productDto.getPrice());
	        product.setQuantity(productDto.getQuantity());
	        productRepository.save(product);
	    }
	    public Product findById(Integer productId) throws ProductNotExistsException {
	        Optional<Product> optionalProduct = productRepository.findById(productId);
	        if (optionalProduct.isEmpty()) {
	            throw new ProductNotExistsException("product id is invalid: " + productId);
	        }
	        return optionalProduct.get();
	    }
	    public void deleteProduct(Integer productId, ProductDto productDto) throws Exception {
	        Optional<Product> optionalProduct = productRepository.findById(productId);
	        Product product= optionalProduct.get();
	        // throw an exception if product does not exists
	        if (!optionalProduct.isPresent()) {
	            throw new Exception("product not present");
	        }
	        productRepository.delete(product);
	    }

		public Product viewProduct(Integer productId) throws Exception {
			 Optional<Product> optionalProduct = productRepository.findById(productId);
		        // throw an exception if product does not exists
		        if (!optionalProduct.isPresent()) {
		            throw new Exception("product not present");
		        }
		        Product product = optionalProduct.get();
		        return product;
			
		}
	}
