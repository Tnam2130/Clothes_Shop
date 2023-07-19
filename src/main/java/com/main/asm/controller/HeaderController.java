package com.main.asm.controller;

import com.main.asm.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class HeaderController {
    @Autowired
    private OrderItemService orderItemService;

    @ModelAttribute("cartItemCount")
    public int getCartItemCount() {
        return orderItemService.getCartItemCount();
    }
}
