package com.example.cshop.services.implementations;

import com.example.cshop.dtos.order.OrderCreateDto;
import com.example.cshop.dtos.order.OrderDto;
import com.example.cshop.dtos.order.OrderUpdateDto;
import com.example.cshop.mappers.OrderMapper;
import com.example.cshop.models.*;
import com.example.cshop.repositories.OrderRepository;
import com.example.cshop.repositories.ProductRepository;
import com.example.cshop.repositories.UserRepository;
import com.example.cshop.services.interfaces.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository repository, OrderMapper mapper, UserRepository userRepository, ProductRepository productRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto findById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapper.toDto(order);
    }

    @Override
    public OrderDto create(OrderCreateDto dto) {
        Order order = mapper.toEntity(dto);
        return mapper.toDto(repository.save(order));
    }

    @Transactional
    @Override
    public OrderDto createOrderFromCart(String username, Map<Long, Integer> cart) {
        if (cart.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            Product product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + entry.getKey()));

            Integer quantity = entry.getValue();

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setPrice(product.getPrice());
            item.setOrder(order);

            orderItems.add(item);

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }

        order.setItems(orderItems);
        order.setTotal(total);

        repository.save(order); // сохраняем заказ вместе с OrderItems через cascade

        return mapper.toDto(order);
    }



    @Override
    public OrderDto update(Long id, OrderUpdateDto dto) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        mapper.updateEntity(dto, order);
        return mapper.toDto(repository.save(order));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<OrderDto> getOrdersForUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        return repository.findByUser(user).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<OrderDto> getOrdersForUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return repository.findByUser(user)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }


}
