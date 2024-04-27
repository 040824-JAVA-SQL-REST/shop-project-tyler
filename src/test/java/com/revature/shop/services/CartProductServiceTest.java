package com.revature.shop.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.revature.shop.daos.CartProductDao;
import com.revature.shop.models.CartProduct;

public class CartProductServiceTest {
    private CartProductService cartProductService;
    private CartProductDao cartProductDao;

    @Before
    public void setup() {
        cartProductDao = Mockito.mock(CartProductDao.class);
        cartProductService = new CartProductService(cartProductDao);
    }

    @Test
    public void findCartProductByCartAndProductIdShouldReturnCartProductWithValidInput() {
        List<CartProduct> mockedCartProducts = List.of(
                new CartProduct("1", "1", 1, 15),
                new CartProduct("1", "2", 2, 5));
        Mockito.when(cartProductDao.findAll()).thenReturn(mockedCartProducts);

        Optional<CartProduct> actual = cartProductService.findCartProductByCartAndProductId("1", "1");

        assertEquals(actual.get(), mockedCartProducts.get(0));
    }

    @Test
    public void findCartProductByCartAndProductIdShouldReturnNonetWithInvalidInput() {
        List<CartProduct> mockedCartProducts = List.of(
                new CartProduct("1", "1", 1, 15),
                new CartProduct("1", "2", 2, 5));
        Mockito.when(cartProductDao.findAll()).thenReturn(mockedCartProducts);

        Optional<CartProduct> actual = cartProductService.findCartProductByCartAndProductId("3", "1");

        assertTrue(actual.isEmpty());
    }

    @Test
    public void findCartProductWithCertainCartIdShouldReturnCartProductsWithValidCartId() {
        List<CartProduct> mockedCartProducts = List.of(
                new CartProduct("1", "1", 1, 15),
                new CartProduct("1", "2", 2, 5));
        Mockito.when(cartProductDao.findAll()).thenReturn(mockedCartProducts);

        List<CartProduct> actual = cartProductService.findCartProductWithCertainCartId("1");

        assertEquals(actual, mockedCartProducts);
    }

    @Test
    public void getTotalCostByCartIdShouldReturnTotalCostWithValidCartId() {
        List<CartProduct> mockedCartProducts = List.of(
                new CartProduct("1", "1", 1, 15),
                new CartProduct("1", "2", 2, 5));
        Mockito.when(cartProductDao.findAll()).thenReturn(mockedCartProducts);
        Mockito.when(cartProductDao.getCartPrice("1")).thenReturn(25.0f);

        float actual = cartProductService.getTotalCostByCartId("1");

        assertTrue(actual == 25.0f);
    }

    @Test
    public void saveShouldReturnTheSavedCartProduct() {
        CartProduct mockedCartProduct = new CartProduct("1", "1", 1, 15);
        Mockito.when(cartProductDao.save(mockedCartProduct)).thenReturn(mockedCartProduct);

        CartProduct actual = cartProductService.save(mockedCartProduct);

        assertEquals(actual, mockedCartProduct);
    }

    @Test
    public void updateShouldReturnTheUpdatedCartProduct() {
        CartProduct mockedCartProduct = new CartProduct("1", "1", 1, 15);
        Mockito.when(cartProductDao.update(mockedCartProduct)).thenReturn(mockedCartProduct);

        CartProduct actual = cartProductService.update(mockedCartProduct);

        assertEquals(actual, mockedCartProduct);
    }

    @Test
    public void deleteByCartIdAndProductIdShouldReturnTrueIfDeleted() {
        CartProduct mockedCartProduct = new CartProduct("1", "1", 1, 15);
        Mockito.when(cartProductDao.deleteByCartIdAndProductId("1", "1")).thenReturn(true);

        boolean actual = cartProductService.deleteByCartIdAndProductId("1", "1");

        assertTrue(actual);
    }

    @Test
    public void deleteAllItemsAssociatedWithCartIdShouldReturnTrueIfDeleted() {
        CartProduct mockedCartProduct = new CartProduct("1", "1", 1, 15);
        Mockito.when(cartProductDao.findAll()).thenReturn(List.of(mockedCartProduct));
        Mockito.when(cartProductDao.deleteByCartIdAndProductId("1", "1")).thenReturn(true);

        boolean actual = cartProductService.deleteAllItemsAssociatedWithCartId("1");

        assertTrue(actual);
    }
}
