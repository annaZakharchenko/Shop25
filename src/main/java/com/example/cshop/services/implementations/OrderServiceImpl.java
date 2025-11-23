package com.example.cshop.services.implementations;

import com.example.cshop.dtos.order.OrderCreateDto;
import com.example.cshop.dtos.order.OrderDto;
import com.example.cshop.dtos.order.OrderUpdateDto;
import com.example.cshop.dtos.orderitemdto.OrderItemDto;
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
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository repository, OrderMapper mapper, UserRepository userRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Преобразуем Order в OrderDto
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setUserEmail(order.getUser().getEmail());
        orderDto.setUsername(order.getUser().getUsername());
        orderDto.setStatus(order.getStatus());
        orderDto.setTotal(order.getTotal());


        List<OrderItemDto> itemsDto = order.getItems().stream().map(item -> {
            OrderItemDto dto = new OrderItemDto();
            dto.setId(item.getId());
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());        // название продукта
            dto.setProductImageUrl(item.getProduct().getImageUrl());// ссылка на картинку
            dto.setQuantity(item.getQuantity());
            dto.setUnitPrice(item.getUnitPrice());
            dto.getSubtotal();

            return dto;
        }).toList();

        orderDto.setItems(itemsDto);

        BigDecimal total = itemsDto.stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderDto.setTotal(total);

        return orderDto;
    }


    @Override
    public OrderDto create(OrderCreateDto dto) {
        Order order = mapper.toEntity(dto);
        return mapper.toDto(repository.save(order));
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
    public OrderDto createOrderFromCart(String userEmail, Map<Long, Integer> cart) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));

        Order order = new Order();
        order.setUser(user);

        BigDecimal total = BigDecimal.ZERO;

        // объединяем одинаковые продукты
        Map<Long, Integer> combinedCart = cart.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum
                ));

        for (Map.Entry<Long, Integer> entry : combinedCart.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

            // Проверяем наличие на складе
            if (product.getStock() < quantity) {
                throw new RuntimeException("Недостаточно товаров на складе: " + product.getName());
            }

            // уменьшаем stock
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setOrder(order);

            order.getItems().add(orderItem);

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }

        order.setTotal(total);
        Order savedOrder = repository.save(order);
        return mapper.toDto(savedOrder);
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
