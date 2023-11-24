package bookstore.controller;

import bookstore.dto.order.OrderDto;
import bookstore.dto.order.OrderRequestDto;
import bookstore.dto.order.UpdateOrderStatusDto;
import bookstore.dto.orderitem.OrderItemDto;
import bookstore.model.User;
import bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Order management")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping
    @Operation(summary = "Get orders", description = "Get orders")
    public List<OrderDto> getOrders(Authentication authentication,
                                    @PageableDefault Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.getAllById(pageable, user.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Create order", description = "Create order")
    public OrderDto placeOrder(Authentication authentication,
                               @RequestBody @Valid OrderRequestDto orderRequestDto) {
        User user = (User) authentication.getPrincipal();
        return orderService.createOrder(user.getId(), orderRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id}")
    @Operation(summary = "Update order", description = "Update order")
    public OrderDto updateOrder(@RequestBody @Valid UpdateOrderStatusDto updateOrderStatusDto,
                                @PathVariable @Positive Long id) {
        return orderService.update(id, updateOrderStatusDto);
    }

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/{id}/items")
    @Operation(summary = "Get order items by order id", description = "Get order items by order id")
    public List<OrderItemDto> getItemsByOrderId(@PageableDefault Pageable pageable,
                                                @PathVariable @Positive Long id) {
        return orderService.getItemsByOrderId(pageable, id);
    }

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Get order items by order id and item id",
            description = "Get order items by order id and item id")
    public OrderItemDto getItemByOrderIdAndItemId(@PathVariable @Positive Long orderId,
                                                  @PathVariable @Positive Long itemId) {
        return orderService.getItemByOrderIdAndItemId(orderId, itemId);
    }
}
