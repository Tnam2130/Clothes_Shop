package com.main.asm.service;

import com.main.asm.entity.OrderItems;
import com.main.asm.entity.Orders;
import com.main.asm.entity.Users;
import com.main.asm.repository.OrderItemsRepository;
import com.main.asm.repository.OrdersRepository;
import com.main.asm.repository.UsersRepository;
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

    @Autowired
    UsersRepository usersRepository;

    public void createOrder(List<OrderItems> orderItems, Orders order, String userEmail){
//        System.out.println(userEmail);
        Users users = usersRepository.findByEmail(userEmail);
        if (users != null){
            order.setUsers(users);
            for (OrderItems orderItem: orderItems) {
                Double price=productService.getProductPrice(orderItem.getProducts().getId());
                orderItem.setOrders(order);
                orderItem.setPrice(BigDecimal.valueOf(price));
            }
            orderItemsRepository.saveAll(orderItems);
        }else {
            System.out.println("Error");
        }
    }

    public void saveOrder(Orders orders, String userEmail){
        Users users = usersRepository.findByEmail(userEmail);
        if (users != null){
            orders.setUsers(users);
            ordersRepository.save(orders);
        }
    }
    public Orders getOrderById(Long id){
        return ordersRepository.findById(id).orElseThrow(()->new RuntimeException("Order not found"));
    }
}
