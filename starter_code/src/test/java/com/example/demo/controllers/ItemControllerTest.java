package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.ItemRepository;
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
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;

    private ItemRepository itemRepository = mock(ItemRepository.class);

    String userName = "test";
    private User user;
    private  Cart cart;
    private Item item;

    @Before
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectMocks(itemController, "itemRepository", itemRepository);

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
    public void testGetItemsByUserName() {
        // given
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemRepository.findByName(eq(userName))).thenReturn(itemList);

        // when
        ResponseEntity<List<Item>> itemsResEntity = itemController.getItemsByName(userName);
        List<Item> retrievedItems = itemsResEntity.getBody();

        // then
        assertEquals(itemsResEntity.getStatusCode().value(), 200);
        assertNotNull(itemsResEntity);
        assertNotNull(retrievedItems);
        assertFalse(retrievedItems.isEmpty());
    }

    @Test
    public void testGetAllItems() {
        // given
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemRepository.findAll()).thenReturn(itemList);

        // when
        ResponseEntity<List<Item>> itemsResEntity = itemController.getItems();
        List<Item> retrievedItems = itemsResEntity.getBody();

        // then
        assertEquals(itemsResEntity.getStatusCode().value(), 200);
        assertNotNull(itemsResEntity);
        assertNotNull(retrievedItems);
        assertFalse(retrievedItems.isEmpty());
    }


    @Test
    public void testGetItemById() {
        // given
        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));

        // when
        ResponseEntity<Item> itemsResEntity = itemController.getItemById(item.getId());
        Item retrievedItems = itemsResEntity.getBody();

        // then
        assertEquals(itemsResEntity.getStatusCode().value(), 200);
        assertNotNull(itemsResEntity);
        assertNotNull(retrievedItems);
    }

    @Test
    public void testGetEmptyItems() {
        // given
        List<Item> itemList = new ArrayList<>();
        when(itemRepository.findByName(eq(userName))).thenReturn(itemList);

        // when
        ResponseEntity<List<Item>> itemsResEntity = itemController.getItemsByName(userName);

        // then
        assertEquals(itemsResEntity.getStatusCode().value(), HttpStatus.NOT_FOUND.value());
    }

}
