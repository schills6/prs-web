package com.prs.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.Product;
import com.prs.business.Vendor;

@RunWith(SpringRunner.class)
@JsonTest
public class ProductJsonTests {
	
	@Autowired
	private JacksonTester<Product> json;
	
	@Test
	public void serializeProducttoJsonTest() {
		Vendor v = new Vendor();
		Product product = new Product(v, "partNumber", "name", 100.00, "unit", "photoPath");
		try {
			assertThat(json.write(product))
				.extractingJsonPathStringValue("$.partNumber")
				.isEqualTo("partNumber");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}