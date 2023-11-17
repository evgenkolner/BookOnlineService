package bookstore.service;

import bookstore.dto.order.OrderDto;
import bookstore.dto.order.OrderRequestDto;
import bookstore.dto.order.UpdateOrderStatusDto;
import bookstore.dto.orderitem.OrderItemDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    List<OrderDto> getAllById(Pageable pageable, Long id);

    OrderDto createOrder(Long id, OrderRequestDto requestDto);

    OrderDto update(Long orderId, UpdateOrderStatusDto updateDto);

    List<OrderItemDto> getItemsByOrderId(Pageable pageable, Long orderId);

    OrderItemDto getItemByOrderIdAndItemId(Long orderId, Long itemId);
}
