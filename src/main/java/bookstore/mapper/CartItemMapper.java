package bookstore.mapper;

import bookstore.dto.cartitem.CartItemDto;
import bookstore.dto.cartitem.CartItemRequestDto;
import bookstore.model.CartItem;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl",
        uses = BookMapper.class)
public interface CartItemMapper {
    //CartItemRequestDto toCartItem(CartItemRequestDto requestDto);
    CartItem toCartItem(CartItemRequestDto requestDto);

    @Mapping(source = "book.title", target = "bookTitle")
    @Mapping(source = "book.id", target = "bookId")
    CartItemDto toDto(CartItem cartItem);
}
