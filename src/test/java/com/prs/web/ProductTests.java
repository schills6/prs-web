package com.prs.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.prs.business.Product;
import com.prs.business.Vendor;
import com.prs.db.ProductRepository;
import com.prs.db.VendorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional

public class ProductTests {
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private VendorRepository vendorRepository;
	
	@Test
	public void testProductGetAll() {
		Iterable<Product> products = productRepository.findAll();
		assertNotNull(products);	
	}	
	
	@Test
	public void testProductAddAndDelete() {
		Optional <Vendor> v = vendorRepository.findById(2);
		Product p = new Product (v.get(), "partNumber", "name", 50.00, "unit", "photoPath");
		assertNotNull(productRepository.save(p));
		assertEquals("partNumber", p.getPartNumber());
	}

}
