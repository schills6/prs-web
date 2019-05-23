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

import com.prs.business.*;
import com.prs.db.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PurchaseRequestLineItemTests {
	
	@Autowired
	private PurchaseRequestLineItemRepository purchaseRequestLineItemRepository;
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testPurchaseRequestLineItemGetAll() {
		Iterable<PurchaseRequestLineItem> purchaserequestlineitems = purchaseRequestLineItemRepository.findAll();
		assertNotNull(purchaserequestlineitems);	
	}
	
	@Test
	public void testPurchaseRequestLineItemAddAndDelete() {
		Iterable<Product> products = productRepository.findAll();
		Iterable<User> users = userRepository.findAll();
		User u = users.iterator().next();
		Product p = products.iterator().next();
		PurchaseRequest pr = new PurchaseRequest(u, "desc", "just", LocalDate.now(),
												"mail", "new", 100.00, LocalDateTime.now(), "");
		purchaseRequestRepository.save(pr);		
		System.out.println("prid = " + pr.getId() + ", pdtid= " + p.getId());
		PurchaseRequestLineItem prli = new PurchaseRequestLineItem(pr, p, 5);
		assertNotNull(purchaseRequestLineItemRepository.save(prli)); 
		assertEquals(5, prli.getQuantity());
	}
}
