package com.main.asm.service;

import com.main.asm.entity.OrderItems;
import com.main.asm.entity.Orders;
import com.main.asm.repository.OrderItemsRepository;
import com.main.asm.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderItemsRepository orderItemsRepository;
    @Autowired
    ProductService productService;
    @Autowired
    OrdersRepository ordersRepository;

    public void createOrder(List<OrderItems> orderItems){
//        Orders savedOrder= ordersRepository.save(order);
        for (OrderItems orderItem: orderItems) {
            Double price=productService.getProductPrice(orderItem.getProducts().getId());
//            orderItem.setOrders(savedOrder);
            orderItem.setPrice(BigDecimal.valueOf(price));
        }
        orderItemsRepository.saveAll(orderItems);
    }

    public void saveOrder(Orders orders){
        ordersRepository.save(orders);
    }
    public Orders getOrderById(Long id){
        return ordersRepository.findById(id).orElseThrow(()->new RuntimeException("Order not found"));
    }
}
