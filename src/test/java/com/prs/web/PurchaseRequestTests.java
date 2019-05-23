package com.prs.web;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.prs.business.PurchaseRequest;
import com.prs.business.User;
import com.prs.db.PurchaseRequestRepository;
import com.prs.db.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PurchaseRequestTests {
	
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testPurchaseRequestGetAll() {
		Iterable<PurchaseRequest> purchaserequests = purchaseRequestRepository.findAll();
		assertNotNull(purchaserequests);	
	}
	
	@Test
	public void testPurchaseRequestAddAndDelete() {
		Iterable<User> users = userRepository.findAll();
		User u = users.iterator().next();
		LocalDate dateNeeded = LocalDate.parse("2019-05-25");
		LocalDateTime submittedDate = LocalDateTime.now();
		PurchaseRequest pr = new PurchaseRequest(u, "description", "justification",
												dateNeeded, "Mail", "new", 88.10, submittedDate,"");
		assertNotNull(purchaseRequestRepository.save(pr)); 
		assertEquals("description", pr.getDescription());	
	}
}
