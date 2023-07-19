package com.main.asm.service;

import com.main.asm.entity.Category;
import com.main.asm.entity.Products;
import com.main.asm.repository.CategoryRepository;
import com.main.asm.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductsRepository productsRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id){
        return categoryRepository.findById(id).orElse(null);
    }
}
