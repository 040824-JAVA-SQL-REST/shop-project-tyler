package com.revature.shop.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.revature.shop.daos.OrderDao;
import com.revature.shop.models.Order;

public class OrderServiceTest {
    private OrderService orderService;
    private OrderDao orderDao;

    @Before
    public void setup() {
        orderDao = Mockito.mock(OrderDao.class);
        orderService = new OrderService(orderDao);
    }

    @Test
    public void saveShouldReturnTheSavedOrder() {
        Order mockedOrder = new Order("1", "PENDING", "VISA",
                "1", "NOW");
        Mockito.when(orderDao.save(mockedOrder)).thenReturn(mockedOrder);

        Order actual = orderService.save(mockedOrder);

        assertEquals(actual, mockedOrder);
    }

    @Test
    public void getOrderByIdShouldReturnOrderWithCorrectId() {
        Order mockedOrder = new Order("1", "PENDING", "VISA",
                "1", "NOW");
        Mockito.when(orderDao.findAll()).thenReturn(List.of(mockedOrder));

        Optional<Order> actual = orderService.getOrderById("1");

        assertEquals(mockedOrder, actual.get());
    }

    @Test
    public void getAllOrdersByUserIdShouldReturnAllOrdersWithCertainUserId() {
        List<Order> mockedOrders = List.of(
                new Order("1", "PENDING", "VISA", "1", "NOW"),
                new Order("2", "PENDING", "VISA", "1", "NOW"));
        Mockito.when(orderDao.findAll()).thenReturn(mockedOrders);

        List<Order> actual = orderService.getAllOrdersByUserId("1");

        assertEquals(actual, mockedOrders);
    }

    @Test
    public void getAllOrdersByUserIdShouldReturnEmptyWithInvalidUserId() {
        List<Order> mockedOrders = List.of(
                new Order("1", "PENDING", "VISA", "1", "NOW"),
                new Order("2", "PENDING", "VISA", "1", "NOW"));
        Mockito.when(orderDao.findAll()).thenReturn(mockedOrders);

        List<Order> actual = orderService.getAllOrdersByUserId("2");

        assertNotEquals(actual, mockedOrders);
    }

    @Test
    public void getAllOrdersShouldReturnAllOrders() {
        List<Order> mockedOrders = List.of(
                new Order("1", "PENDING", "VISA", "1", "NOW"),
                new Order("4", "PENDING", "VISA", "3", "NOW"),
                new Order("2", "PENDING", "VISA", "1", "NOW"));
        Mockito.when(orderDao.findAll()).thenReturn(mockedOrders);

        List<Order> actual = orderService.getAllOrders();

        assertEquals(actual, mockedOrders);
    }

    @Test
    public void isExistingOrderIdShouldReturnTrueIfOrderIdExists() {
        List<Order> mockedOrders = List.of(
                new Order("1", "PENDING", "VISA", "1", "NOW"),
                new Order("4", "PENDING", "VISA", "3", "NOW"),
                new Order("2", "PENDING", "VISA", "1", "NOW"));
        Mockito.when(orderDao.findAll()).thenReturn(mockedOrders);

        boolean actual = orderService.isExistingOrderId("1");

        assertTrue(actual);
    }

    @Test
    public void isExistingOrderIdShouldReturnFalseIfOrderIdDoesNotExists() {
        List<Order> mockedOrders = List.of(
                new Order("1", "PENDING", "VISA", "1", "NOW"),
                new Order("4", "PENDING", "VISA", "3", "NOW"),
                new Order("2", "PENDING", "VISA", "1", "NOW"));
        Mockito.when(orderDao.findAll()).thenReturn(mockedOrders);

        boolean actual = orderService.isExistingOrderId("0");

        assertFalse(actual);
    }
}
