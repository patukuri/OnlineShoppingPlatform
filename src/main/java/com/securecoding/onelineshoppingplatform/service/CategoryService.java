package com.securecoding.onelineshoppingplatform.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securecoding.onelineshoppingplatform.Model.Category;
import com.securecoding.onelineshoppingplatform.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
	public void createCategory(Category category) {
		categoryRepository.save(category);
	}
	public List<Category> listCategory() {
		return categoryRepository.findAll();
	}
	public void edit(int categoryId, Category updatedcategory) {
		@SuppressWarnings("deprecation")
		Category category= categoryRepository.getById(categoryId);
		category.setCategoryName(updatedcategory.getCategoryName());
		category.setDescription(updatedcategory.getDescription());
		category.setImageUrl(updatedcategory.getImageUrl());
		categoryRepository.save(category);
		
	}
	public Category readCategory(String categoryName) {
		return categoryRepository.findByCategoryName(categoryName);
	}

	public Optional<Category> readCategory(Integer categoryId) {
		return categoryRepository.findById(categoryId);
	}

}
