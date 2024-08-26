package com.ShoppingManagement.OrderService.Repository;

import com.ShoppingManagement.OrderService.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
