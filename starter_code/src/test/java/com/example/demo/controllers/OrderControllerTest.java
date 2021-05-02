package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {


    private OrderController orderController;

    private UserRepository userRepository = mock(UserRepository.class);

    private OrderRepository orderRepository = mock(OrderRepository.class);

    String userName = "test";
    private User user;
    private  Cart cart;
    private Item item;

    @Before
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectMocks(orderController, "userRepository", userRepository);
        TestUtils.injectMocks(orderController, "orderRepository", orderRepository);

        item = new Item();
        item.setId(1L);
        item.setPrice(new BigDecimal("2.99"));

        cart = new Cart();
        cart.setTotal(new BigDecimal(0));
        cart.addItem(item);

        user = new User();
        user.setUsername(userName);
        user.setCart(cart);
    }

    @Test
    public void testSubmitOrder() {
        // given
        when(userRepository.findByUsername(eq(userName))).thenReturn(user);

        // when
        ResponseEntity<UserOrder> userOrderResponseEntity = orderController.submit(userName);
        UserOrder order = userOrderResponseEntity.getBody();

        // then
        assertEquals(userOrderResponseEntity.getStatusCode().value(), 200);
        assertNotNull(userOrderResponseEntity);
        assertNotNull(order);
        assertEquals(Objects.requireNonNull(order.getItems().stream().findFirst().orElse(null)).getId(),item.getId());
    }


    @Test
    public void testSubmitOrderForNotExistedUser() {
        // given
        when(userRepository.findByUsername(eq(userName))).thenReturn(null);

        // when
        ResponseEntity<UserOrder> userOrderResponseEntity = orderController.submit(userName);

        // then
        assertEquals(userOrderResponseEntity.getStatusCode().value(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testGetOrderHistory() {
        // given
        when(userRepository.findByUsername(eq(userName))).thenReturn(user);

        // when
        ResponseEntity<List<UserOrder>> userOrderResponseEntity = orderController.getOrdersForUser(userName);
        List<UserOrder> orderList = userOrderResponseEntity.getBody();

        // then
        assertEquals(userOrderResponseEntity.getStatusCode().value(), 200);
        assertNotNull(userOrderResponseEntity);
        assertNotNull(orderList);
    }

    @Test
    public void testGetOrderHistoryForNotExistedUser() {
        // given
        when(userRepository.findByUsername(eq(userName))).thenReturn(null);

        // when
        ResponseEntity<List<UserOrder>> userOrderResponseEntity = orderController.getOrdersForUser(userName);

        // then
        assertEquals(userOrderResponseEntity.getStatusCode().value(), HttpStatus.NOT_FOUND.value());
    }

}
