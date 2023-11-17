package bookstore.service;

import bookstore.dto.cartitem.CartItemRequestDto;
import bookstore.dto.shoppingcart.ShoppingCartDto;
import bookstore.dto.shoppingcart.ShoppingCartRequestDto;

public interface ShoppingCartService {
    ShoppingCartDto findById(Long id);

    ShoppingCartDto addToCart(Long id, CartItemRequestDto requestDto);

    ShoppingCartDto update(Long userId, Long cartItemId, ShoppingCartRequestDto quantityDto);

    ShoppingCartDto removeCartItem(Long userId, Long cartItemId);
}
