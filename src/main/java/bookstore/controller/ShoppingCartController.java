package bookstore.controller;

import bookstore.dto.cartitem.CartItemRequestDto;
import bookstore.dto.shoppingcart.ShoppingCartDto;
import bookstore.dto.shoppingcart.ShoppingCartRequestDto;
import bookstore.model.User;
import bookstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Shopping cart management")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "Get shopping cart", description = "Get shopping cart")
    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.findById(user.getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Add book to cart",
            description = "Add book to cart")
    public ShoppingCartDto addBookToCart(Authentication authentication,
                                         @RequestBody CartItemRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addToCart(user.getId(), requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping(("/cart-items/{id}"))
    @Operation(summary = "Update cart item",
            description = "Update cart item")
    public ShoppingCartDto updateCartItem(Authentication authentication,
                                          @PathVariable @Positive Long id,
                                          @RequestBody ShoppingCartRequestDto quantity) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.update(user.getId(), id, quantity);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(("/cart-items/{id}"))
    @Operation(summary = "Delete cart item",
            description = "Delete cart item")
    public ShoppingCartDto deleteCartItem(Authentication authentication,
                                          @PathVariable @Positive Long id) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.removeCartItem(user.getId(), id);
    }
}
