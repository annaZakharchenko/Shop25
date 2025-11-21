package com.example.cshop.repositories;

import com.example.cshop.models.Order;
import com.example.cshop.models.OrderStatus;
import com.example.cshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
    List<Order> findByUserUsername(String username);
    List<Order> findByStatus(OrderStatus status);

}
