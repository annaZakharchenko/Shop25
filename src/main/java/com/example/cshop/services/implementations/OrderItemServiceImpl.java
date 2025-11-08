package com.example.cshop.services.implementations;

import com.example.cshop.dtos.orderitemdto.OrderItemCreateDto;
import com.example.cshop.dtos.orderitemdto.OrderItemDto;
import com.example.cshop.dtos.orderitemdto.OrderItemUpdateDto;
import com.example.cshop.mappers.OrderItemMapper;
import com.example.cshop.models.OrderItem;
import com.example.cshop.repositories.OrderItemRepository;
import com.example.cshop.services.interfaces.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository repository;
    private final OrderItemMapper mapper;

    public OrderItemServiceImpl(OrderItemRepository repository, OrderItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<OrderItemDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemDto findById(Long id) {
        OrderItem item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));
        return mapper.toDto(item);
    }

    @Override
    public OrderItemDto create(OrderItemCreateDto dto) {
        OrderItem item = mapper.toEntity(dto);
        return mapper.toDto(repository.save(item));
    }

    @Override
    public OrderItemDto update(Long id, OrderItemUpdateDto dto) {
        OrderItem item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));
        mapper.updateEntity(dto, item);
        return mapper.toDto(repository.save(item));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
