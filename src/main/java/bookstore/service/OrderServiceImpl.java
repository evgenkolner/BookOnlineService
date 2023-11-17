package bookstore.service;

import bookstore.dto.order.OrderDto;
import bookstore.dto.order.OrderRequestDto;
import bookstore.dto.order.UpdateOrderStatusDto;
import bookstore.dto.orderitem.OrderItemDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public class OrderServiceImpl implements OrderService {
    @Override
    public List<OrderDto> getAllById(Pageable pageable, Long id) {
        return null;
    }

    @Override
    public OrderDto createOrder(Long id, OrderRequestDto requestDto) {
        return null;
    }

    @Override
    public OrderDto update(Long orderId, UpdateOrderStatusDto updateDto) {
        return null;
    }

    @Override
    public List<OrderItemDto> getItemsByOrderId(Pageable pageable, Long orderId) {
        return null;
    }

    @Override
    public OrderItemDto getItemByOrderIdAndItemId(Long orderId, Long itemId) {
        return null;
    }
}
