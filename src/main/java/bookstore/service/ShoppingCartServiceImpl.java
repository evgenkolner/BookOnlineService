package bookstore.service;

import bookstore.dto.cartitem.CartItemRequestDto;
import bookstore.dto.shoppingcart.ShoppingCartDto;
import bookstore.dto.shoppingcart.ShoppingCartRequestDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.ShoppingCartMapper;
import bookstore.model.Book;
import bookstore.model.CartItem;
import bookstore.model.ShoppingCart;
import bookstore.model.User;
import bookstore.repository.BookRepository;
import bookstore.repository.CartItemRepository;
import bookstore.repository.ShoppingCartRepository;
import bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional(readOnly = true)
    @Override
    public ShoppingCartDto findById(Long id) {
        return shoppingCartMapper.toDto(shoppingCartRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cannot find shopping cart")));
    }

    @Transactional
    @Override
    public ShoppingCartDto addToCart(Long userId, CartItemRequestDto requestDto) {
        Book book = bookRepository.getBookById(requestDto.bookId()).orElseThrow(
                () -> new EntityNotFoundException("Cannot find book"));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find user"));
        ShoppingCart shoppingCart = shoppingCartRepository.findById(userId).orElseGet(
                () -> createShoppingCartForUser(user));
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setQuantity(requestDto.quantity());
        shoppingCart.getCartItems().add(cartItem);
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Transactional
    @Override
    public ShoppingCartDto update(Long userId,
                                  Long cartItemId, ShoppingCartRequestDto quantityDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart by id " + userId)
                );
        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(cartItemId, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cart item by id " + cartItemId + "for user by id " + userId)
                );
        cartItem.setQuantity(quantityDto.quantity());
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(cartItem.getShoppingCart());
    }

    @Transactional
    @Override
    public ShoppingCartDto removeCartItem(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart by id " + userId)
                );
        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(cartItemId, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cart item by id " + cartItemId + "for user by id " + userId)
                );
        cartItemRepository.delete(cartItem);
        shoppingCart.removeCartItem(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    private ShoppingCart createShoppingCartForUser(User user) {
        ShoppingCart newShoppingCart = new ShoppingCart();
        newShoppingCart.setUser(user);
        return shoppingCartRepository.save(newShoppingCart);
    }
}
