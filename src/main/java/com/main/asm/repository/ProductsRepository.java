package com.main.asm.repository;

import com.main.asm.entity.Category;
import com.main.asm.entity.Products;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long>, JpaSpecificationExecutor<Products> {
    @Query("SELECT c.category_name FROM Category c INNER JOIN c.products p WHERE p.id = ?1")
    String findCategoryNameByProductId(Long productId);

    @Query("SELECT p.name FROM Products p WHERE p.id = ?1")
    String findNameProductById(Long productId);
    @Query("SELECT p FROM Products p WHERE p.name LIKE %:searchTerm%")
    List<Products> findBySearchTerm(@Param("searchTerm") String searchTerm);
    @Query("SELECT p FROM Products p WHERE p.name LIKE %:searchTerm% AND p.category = :category")
    List<Products> findBySearchTermAndCategory(@Param("searchTerm") String searchTerm, @Param("category") String category);
}
