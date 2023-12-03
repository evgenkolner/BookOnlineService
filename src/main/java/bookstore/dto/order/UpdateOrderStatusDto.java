package bookstore.dto.order;

import bookstore.model.Status;

public record UpdateOrderStatusDto(
        Status status
) {
}
