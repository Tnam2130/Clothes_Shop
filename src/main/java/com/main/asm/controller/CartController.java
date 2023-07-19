package com.main.asm.controller;

import com.main.asm.entity.OrderItems;
import com.main.asm.entity.Orders;
import com.main.asm.entity.Users;
import com.main.asm.service.OrderItemService;
import com.main.asm.service.OrderService;
import com.main.asm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public String viewCart(Model model){
        List<OrderItems> orderItems = orderItemService.getAllOrderItems();
        double totalPrice = orderItems.stream()
                .mapToDouble(orderItem -> orderItem.getProducts().getPrice() * orderItem.getQuantity())
                .sum();
        model.addAttribute("orderItems",orderItems);
        model.addAttribute("title","Giỏ hàng");
        model.addAttribute("total",totalPrice);
        return "/cart/cart";
    }
    @PostMapping("/delete")
    public String deleteSelectedItems(@RequestParam("selectedItems") List<Long> selectedItems) {
        orderItemService.deleteItems(selectedItems);
        return "redirect:/cart";
    }
}
