package com.prs.web;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.prs.business.Vendor;
import com.prs.db.VendorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional

public class VendorTests {
	
	@Autowired
	private VendorRepository vendorRepository;
	
	@Test
	public void testVendorGetAll() {
		Iterable<Vendor> vendors = vendorRepository.findAll();
		assertNotNull(vendors);	
	}	
	
	@Test
	public void addAndDeleteVendor() {
		Vendor v = new Vendor ("code", "name", "address", "city", "st", "zip", "phoneNumber",
								"email", true);
		assertNotNull(vendorRepository.save(v));
		assertEquals("code", v.getCode());	
	}

}
