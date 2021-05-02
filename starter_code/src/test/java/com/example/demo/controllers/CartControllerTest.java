package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);

    private Cart cart;
    private User user;
    String userName = "test";

    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectMocks(cartController, "userRepository", userRepository);
        TestUtils.injectMocks(cartController, "itemRepository", itemRepository);
        TestUtils.injectMocks(cartController, "cartRepository", cartRepository);

        cart = new Cart();
        cart.setTotal(new BigDecimal(0));
        cart.setItems(new ArrayList<>());

        user = new User();
        user.setUsername(userName);
        user.setCart(cart);
    }

    @Test
    public void testAddToCart(){
        // given
        long itemId = 1L;
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(itemId);
        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setUsername(userName);
        Item item = new Item();
        item.setId(itemId);
        item.setPrice(new BigDecimal("2.99"));
        when(userRepository.findByUsername(eq(userName))).thenReturn(user);
        when(itemRepository.findById(eq(modifyCartRequest.getItemId()))).thenReturn(Optional.of(item));

        // when
        ResponseEntity<Cart> cartResponseEntity = cartController.addTocart(modifyCartRequest);
        Cart rerievedCart = cartResponseEntity.getBody();

        // then
        assertEquals(cartResponseEntity.getStatusCode().value(), 200);
        assertNotNull(rerievedCart);
        assertFalse(rerievedCart.getItems().isEmpty());
        assertEquals(rerievedCart.getItems().stream().findFirst().orElse(null).getId(), item.getId());
    }


    @Test
    public void testAddToCartToNotFoundUser(){
        // given
        String userName = "test";
        long itemId = 1L;
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(itemId);
        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setUsername(userName);
        Item item = new Item();
        item.setId(itemId);
        item.setPrice(new BigDecimal("2.99"));
        when(userRepository.findByUsername(eq(userName))).thenReturn(null);
        when(itemRepository.findById(eq(modifyCartRequest.getItemId()))).thenReturn(Optional.of(item));

        // when
        ResponseEntity<Cart> cartResponseEntity = cartController.addTocart(modifyCartRequest);
        Cart rerievedCart = cartResponseEntity.getBody();

        // then
        assertEquals(cartResponseEntity.getStatusCode().value(), HttpStatus.NOT_FOUND.value());
        assertNull(rerievedCart);
    }


    @Test
    public void testAddToCartToNotFoundItem(){
        // given
        String userName = "test";
        long itemId = 1L;
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(itemId);
        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setUsername(userName);
        Item item = new Item();
        item.setId(itemId);
        item.setPrice(new BigDecimal("2.99"));
        when(userRepository.findByUsername(eq(userName))).thenReturn(user);

        // when
        ResponseEntity<Cart> cartResponseEntity = cartController.addTocart(modifyCartRequest);
        Cart rerievedCart = cartResponseEntity.getBody();

        // then
        assertEquals(cartResponseEntity.getStatusCode().value(), HttpStatus.NOT_FOUND.value());
        assertNull(rerievedCart);
    }


    @Test
    public void testRemoveFromCart(){
        // given
        long itemId = 1L;
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(itemId);
        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setUsername(userName);
        Item item = new Item();
        item.setId(itemId);
        item.setPrice(new BigDecimal("2.99"));
        when(userRepository.findByUsername(eq(userName))).thenReturn(user);
        when(itemRepository.findById(eq(modifyCartRequest.getItemId()))).thenReturn(Optional.of(item));

        // when
        ResponseEntity<Cart> cartResponseEntity = cartController.removeFromcart(modifyCartRequest);
        Cart rerievedCart = cartResponseEntity.getBody();

        // then
        assertEquals(cartResponseEntity.getStatusCode().value(), 200);
        assertNotNull(rerievedCart);
        assertTrue(rerievedCart.getItems().isEmpty());
    }


    @Test
    public void testRemoveFromCartForNotExistedUser(){
        // given
        long itemId = 1L;
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(itemId);
        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setUsername(userName);
        Item item = new Item();
        item.setId(itemId);
        item.setPrice(new BigDecimal("2.99"));
        when(userRepository.findByUsername(eq(userName))).thenReturn(null);
        when(itemRepository.findById(eq(modifyCartRequest.getItemId()))).thenReturn(Optional.of(item));

        // when
        ResponseEntity<Cart> cartResponseEntity = cartController.removeFromcart(modifyCartRequest);
        Cart rerievedCart = cartResponseEntity.getBody();

        // then
        assertEquals(cartResponseEntity.getStatusCode().value(), HttpStatus.NOT_FOUND.value());
        assertNull(rerievedCart);
    }

    @Test
    public void testRemoveFromCartForNotExistedItem(){
        // given
        long itemId = 1L;
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(itemId);
        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setUsername(userName);
        Item item = new Item();
        item.setId(itemId);
        item.setPrice(new BigDecimal("2.99"));
        when(userRepository.findByUsername(eq(userName))).thenReturn(user);

        // when
        ResponseEntity<Cart> cartResponseEntity = cartController.removeFromcart(modifyCartRequest);
        Cart rerievedCart = cartResponseEntity.getBody();

        // then
        assertEquals(cartResponseEntity.getStatusCode().value(), HttpStatus.NOT_FOUND.value());
        assertNull(rerievedCart);
    }



}
