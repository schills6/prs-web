package com.prs.web;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.prs.business.User;
import com.prs.db.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserTests {
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testUserGetAll() {
		Iterable<User> users = userRepository.findAll();
		assertNotNull(users);	
	}
	
	@Test
	public void testUserAddAndDelete() {
		User u = new User("userName", "password", "firstName", "lastName", "phone", "email", true, true);
		assertNotNull(userRepository.save(u));
		assertEquals("lastName", u.getLastName());
	}

}
