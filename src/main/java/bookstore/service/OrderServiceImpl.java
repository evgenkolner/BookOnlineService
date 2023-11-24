package bookstore.service;

import bookstore.dto.order.OrderDto;
import bookstore.dto.order.OrderRequestDto;
import bookstore.dto.order.UpdateOrderStatusDto;
import bookstore.dto.orderitem.OrderItemDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.OrderItemMapper;
import bookstore.mapper.OrderMapper;
import bookstore.model.Order;
import bookstore.model.OrderItem;
import bookstore.model.ShoppingCart;
import bookstore.repository.OrderItemRepository;
import bookstore.repository.OrderRepository;
import bookstore.repository.ShoppingCartRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public List<OrderDto> getAllById(Pageable pageable, Long id) {
        return orderRepository.findAllByUserId(pageable, id).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public OrderDto createOrder(Long id, OrderRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Shopping cart does not exist")
        );
        Order order = orderMapper.toOrder(shoppingCart, requestDto);
        orderRepository.save(order);
        shoppingCart.clear();
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDto update(Long orderId, UpdateOrderStatusDto updateDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order does not exist")
        );
        order.setStatus(updateDto.status());
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderItemDto> getItemsByOrderId(Pageable pageable, Long orderId) {
        return orderItemRepository.findAllByOrderId(pageable, orderId).stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getItemByOrderIdAndItemId(Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository.findByIdAndOrderId(itemId, orderId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Cant find item by id "
                        + itemId
                        + " in order by id "
                        + orderId)
                );
        return orderItemMapper.toDto(orderItem);
    }
}
