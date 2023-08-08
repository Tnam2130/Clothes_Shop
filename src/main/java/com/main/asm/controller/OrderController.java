package com.main.asm.controller;

import com.main.asm.entity.OrderItems;
import com.main.asm.entity.Orders;
import com.main.asm.entity.Products;
import com.main.asm.entity.Users;
import com.main.asm.service.OrderService;
import com.main.asm.service.ProductService;
import com.main.asm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    ProductService productService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/place-order")
    public String placeOrder(@ModelAttribute("order") Orders order, @RequestParam("productId") List<Long> productIds,
                             @RequestParam("quantity") List<Integer> quantities, Model model, Principal principal) {
        List<OrderItems> orderItems = new ArrayList<>();
        Long productId = (long) productIds.size();
        Products products = productService.findById(productId);
        for (int i = 0; i < productIds.size(); i++) {
            OrderItems orderItem = new OrderItems();
            Products product = new Products();
            product.setId(productIds.get(i));
            orderItem.setProducts(product);
            orderItem.setQuantity(quantities.get(i));
            orderItems.add(orderItem);
        }
        System.out.println(productIds);
        try {
            model.addAttribute("product", products);
            if (principal != null) {
                String userEmail = principal.getName();
                System.out.println(userEmail);
                orderService.createOrder(orderItems, order, userEmail);
            }
            model.addAttribute("successMessage", "Đặt hàng");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Đặt hàng");
        }
        return "redirect:/cart";
    }

    @PostMapping("/order")
    public String placeOrder(@ModelAttribute("order") Orders order, @RequestParam("total") BigDecimal total,
                             Principal principal) {
        System.out.println(order + "abca");

        if (principal != null) {
            String userEmail = principal.getName();
            order.setTotal(total);
            orderService.saveOrder(order, userEmail);
        }
        return "redirect:/cart?success";
    }
}
