package com.main.asm.service;

import com.main.asm.entity.OrderItems;
import com.main.asm.repository.OrderItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderItemService {
    @Autowired
    private OrderItemsRepository orderItemsRepository;
    public List<OrderItems> getAllOrderItems(){
        return orderItemsRepository.findAll();
    }
    public void deleteItems(List<Long> itemIds){
        orderItemsRepository.deleteByIdIn(itemIds);
    }
    public int getCartItemCount() {
        List<OrderItems> orderItems = orderItemsRepository.findAll();
        return orderItems.size();
    }
}
