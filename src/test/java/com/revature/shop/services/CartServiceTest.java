package com.revature.shop.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.revature.shop.daos.CartDao;
import com.revature.shop.models.Cart;

public class CartServiceTest {
    private CartService cartService;
    private CartDao cartDao;

    @Before
    public void setup() {
        cartDao = Mockito.mock(CartDao.class);
        cartService = new CartService(cartDao);
    }

    @Test
    public void getCartIdByUserIdShouldReturnCartIdIfUserIsValid() {
        List<Cart> mockedCarts = List.of(
                new Cart("1", "1"),
                new Cart("2", "2"));
        Mockito.when(cartDao.findAll()).thenReturn(mockedCarts);

        String actual = cartService.getCartIdByUserId("1");

        assertEquals(actual, "1");
    }

    @Test
    public void getCartIdByUserIdShouldReturnNewCartIdIfNoCartForUserExists() {
        List<Cart> mockedCarts = List.of(
                new Cart("1", "1"),
                new Cart("2", "2"));
        Mockito.when(cartDao.findAll()).thenReturn(mockedCarts);

        String actual = cartService.getCartIdByUserId("3");

        assertFalse(actual.equals("1"));
        assertFalse(actual.equals("2"));
    }

    @Test
    public void getCartByUserIdShouldReturnCartWhenUserHasACart() {
        List<Cart> mockedCarts = List.of(
                new Cart("1", "1"),
                new Cart("2", "2"));
        Mockito.when(cartDao.findAll()).thenReturn(mockedCarts);

        Cart cart = cartService.getCartByUserId("1");

        assertEquals(cart.getId(), "1");
        assertEquals(cart.getUserId(), "1");
    }

    // @Test
    // public void getCartByUserIdShouldReturnNewCartWhenUserDoesNotHaveACart() {
    // List<Cart> mockedCarts = List.of(
    // new Cart("1", "1", 15),
    // new Cart("2", "2", 15));
    // Mockito.when(cartDao.findAll()).thenReturn(mockedCarts);
    // Cart cart = new Cart("3");
    // Cart mockedCart = new Cart("3", "3", 0);
    // Mockito.when(cartDao.save(cart)).thenReturn(mockedCart);

    // cart = cartService.getCartByUserId("3");

    // assertFalse(cart.getId().equals("1"));
    // assertFalse(cart.getId().equals("2"));
    // assertTrue(cart.getUserId().equals("3"));
    // assertTrue(cart.getTotalCost() == 0);
    // }
}
