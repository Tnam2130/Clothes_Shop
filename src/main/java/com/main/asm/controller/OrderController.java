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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/place-order")
    public String placeOrder(@ModelAttribute("order") Orders order, @RequestParam("productId") List<Long> productIds,
                             @RequestParam("quantity") List<Integer> quantities, Model model) {
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
            orderService.createOrder(orderItems);
            model.addAttribute("successMessage", "Đặt hàng");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Đặt hàng");
        }
        return "redirect:/cart";
    }
    @PostMapping("/order")
    public String placeOrder(@ModelAttribute("order") Orders order, @RequestParam("total") BigDecimal total, Authentication authentication){
        System.out.println(order);
        String username = authentication.getName();
        Users user = userService.findByEmail(username);
        Orders newOrder= new Orders();
        newOrder.setUsers(user);
        newOrder.setTotal(total);
        orderService.saveOrder(newOrder);
        System.out.println(newOrder);
        System.out.println(username);
        return "redirect:/cart?success";
    }
}
