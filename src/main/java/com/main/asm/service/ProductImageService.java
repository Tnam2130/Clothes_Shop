package com.main.asm.service;

import com.main.asm.entity.ProductImages;
import com.main.asm.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {
    @Autowired
    ProductImageRepository repository;
    public List<ProductImages> countImageByProductId(Long id){
        return repository.findImageUrlByProductId(id);
    }
    public List<ProductImages> findByProductId(Long id){
        return repository.findByProductsId(id);
    }
}
