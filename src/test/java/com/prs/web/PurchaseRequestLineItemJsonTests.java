package com.prs.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureJsonTesters
public class PurchaseRequestLineItemJsonTests {
	
	@Autowired
	private JacksonTester<PurchaseRequestLineItem> json;
	
	@Test
	public void serializePurchaseRequestLineItemtoJsonTest() {
		Vendor v = new Vendor();
		User u = new User();
		LocalDate dateNeeded = LocalDate.now();
		LocalDateTime submittedDate = LocalDateTime.now();
		Product product = new Product(v, "partNumber", "name", 100.00, "unit", "photoPath");
		PurchaseRequest purchaserequest = new PurchaseRequest (u, "description", "justification", dateNeeded,
				"deliveryMode", "status", 100.00, submittedDate, "reasonForRejection");
		PurchaseRequestLineItem prli = new PurchaseRequestLineItem(purchaserequest, product, 8);
		try {
			assertThat(json.write(prli))
				.extractingJsonPathStringValue("$.description")
				.isEqualTo("description");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}