package com.main.asm.controller;

import com.main.asm.entity.Category;
import com.main.asm.entity.ProductImages;
import com.main.asm.entity.Products;
import com.main.asm.repository.specification.ProductSpecification;
import com.main.asm.service.CategoryService;
import com.main.asm.service.ProductImageService;
import com.main.asm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/collection")
    public String getAllProduct(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("title","Tất cả sản phẩm");
        model.addAttribute("categories", categories);
        model.addAttribute("productList", productService.findAll());
        return "products/products";
    }

    @RequestMapping(value = "/collection/loc-san-pham", method = {RequestMethod.GET, RequestMethod.POST})
    public String showProduct(@RequestParam(name = "categoryId", required = false) Long categoryId,
                              @RequestParam(name = "priceSort", required = false) String priceSort,
                              Model model, RedirectAttributes redirectAttributes) {
/*        C1
       Page<Products> page = productService.filterProducts(requestParam);
       List<Products> productsList = page.getContent();*/
        if (categoryId != null && priceSort != null) {
            redirectAttributes.addAttribute("categoryId", categoryId);
            redirectAttributes.addAttribute("priceSort", priceSort);
            return "redirect:/collection/loc-san-pham";
        }
        List<Products> productsList = productService.filterProducts(categoryId, priceSort);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("title","Tất cả sản phẩm");
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sort", priceSort);
        model.addAttribute("categories", categories);
        model.addAttribute("productList", productsList);
        return "products/products";
    }

    @GetMapping("/products/productId={productId}&categoryId={categoryId}")
    public String getProductDetail(@PathVariable("productId") Long productId,
                                   @PathVariable("categoryId") Long categoryId,
                                   Model model) {
//        Get categoryId
        Specification<Products> spec = Specification.where(null);
        if (categoryId != null) {
            spec = spec.and(ProductSpecification.hasCategory(categoryId));
        }
        List<Products> relatedProduct=productService.findAll(spec);
        model.addAttribute("relatedProduct", relatedProduct);
//
        String productName=productService.getNameProductByProductId(productId);
        model.addAttribute("title",productName);

        Products products = productService.findById(productId);
        String categoryName = productService.getCategoryNameByProductId(productId);
        List<ProductImages> productImages = productImageService.findByProductId(productId);
        model.addAttribute("productImages", productImages);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("product", products);
        return "/products/product-detail";
    }
}
