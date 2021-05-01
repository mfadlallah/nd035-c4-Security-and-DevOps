package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SareetaApplicationTests {

//	@Autowired
//	private UserController userController;
//
//	@Test
//	public void testCreateCustomer() {
//		CreateUserRequest createUserRequest = new CreateUserRequest();
//		CustomerDTO newCustomer = userController.createUser(createUserRequest);
//		CustomerDTO retrievedCustomer = userController.getAllCustomers().get(0);
//		Assertions.assertEquals(newCustomer.getName(), createUserRequest.getName());
//		Assertions.assertEquals(newCustomer.getId(), retrievedCustomer.getId());
//		Assertions.assertTrue(retrievedCustomer.getId() > 0);
//	}
	@Test
	public void contextLoads() {
	}

}
