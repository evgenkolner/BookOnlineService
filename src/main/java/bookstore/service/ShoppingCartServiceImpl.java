package bookstore.service;

import bookstore.dto.cartitem.CartItemRequestDto;
import bookstore.dto.shoppingcart.ShoppingCartDto;
import bookstore.dto.shoppingcart.ShoppingCartRequestDto;

public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Override
    public ShoppingCartDto findById(Long id) {
        return null;
    }

    @Override
    public ShoppingCartDto addToCart(Long id, CartItemRequestDto requestDto) {
        return null;
    }

    @Override
    public ShoppingCartDto update(Long userId,
                                  Long cartItemId, ShoppingCartRequestDto quantityDto) {
        return null;
    }

    @Override
    public ShoppingCartDto removeCartItem(Long userId, Long cartItemId) {
        return null;
    }
}
