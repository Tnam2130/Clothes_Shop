package com.main.asm.repository;

import com.main.asm.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
    void deleteByIdIn(List<Long> ids);
}
