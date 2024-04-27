package com.revature.shop.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.revature.shop.daos.OrderProductDao;
import com.revature.shop.models.CartProduct;
import com.revature.shop.models.OrderProduct;

public class OrderProductServiceTest {
    private OrderProductService orderProductService;
    private OrderProductDao orderProductDao;

    @Before
    public void startup() {
        orderProductDao = Mockito.mock(OrderProductDao.class);
        orderProductService = new OrderProductService(orderProductDao);
    }

    @Test
    public void getOrderPriceByIdShouldReturnTotalOrderPriceWithValidOrderId() {
        String mockedId = "14";
        Mockito.when(orderProductDao.getCartPrice(mockedId)).thenReturn(24.56f);

        float actual = orderProductService.getTotalPriceByOrderId("14");

        assertTrue(24.56f == actual);
    }

    @Test
    public void getOrderPriceByIdSHouldReturn0WithInvalidOrderId() {
        String mockedId = "14";
        Mockito.when(orderProductDao.getCartPrice(mockedId)).thenReturn(24.56f);

        float actual = orderProductService.getTotalPriceByOrderId("1");

        assertTrue(0.0f == actual);
    }

    @Test
    public void findAllOrderProductsByOrderIdShouldReturnListOfOrderProductsWithValidOrderId() {
        List<OrderProduct> mockedOrderProducts = List.of(
                new OrderProduct("1", "1", 1, 3),
                new OrderProduct("2", "1", 1, 3),
                new OrderProduct("1", "2", 2, 1));
        Mockito.when(orderProductDao.findAll()).thenReturn(mockedOrderProducts);

        List<OrderProduct> actual = orderProductService.findAllOrderProductsByOrderId("1");
        List<OrderProduct> expectedResult = List.of(
                new OrderProduct("1", "1", 1, 3),
                new OrderProduct("1", "2", 2, 1));

        assertEquals(actual, expectedResult);
    }

    @Test
    public void moveProductsFromCartProductsToOrderProductsShouldReturnTrueWithValidInput() {
        List<CartProduct> mockedCartProducts = List.of(
                new CartProduct("1", "1", 1, 5));
        OrderProduct mockedOrderProduct = new OrderProduct("1", "1",
                1, 5);
        String orderId = "1";

        Mockito.when(orderProductDao.save(mockedOrderProduct)).thenReturn(mockedOrderProduct);

        boolean actual = orderProductService
                .moveProductsFromCartProductsToOrderProducts(mockedCartProducts, orderId);

        assertTrue(actual);
    }
}
