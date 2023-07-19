package com.main.asm.repository;

import com.main.asm.entity.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImages,Long>, JpaSpecificationExecutor<ProductImages> {
    @Query("SELECT count(img.url) from ProductImages img INNER JOIN img.products pd where pd.id = ?1")
    List<ProductImages> findImageUrlByProductId(Long productId);
    List<ProductImages> findByProductsId(Long productId);
}
