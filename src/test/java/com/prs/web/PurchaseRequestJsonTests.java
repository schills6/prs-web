package com.prs.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.PurchaseRequest;
import com.prs.business.User;

@RunWith(SpringRunner.class)
@JsonTest
public class PurchaseRequestJsonTests {
	
	@Autowired
	private JacksonTester<PurchaseRequest> json;
	
	@Test
	public void serializePurchaseRequesttoJsonTest() {
		User u = new User();
		LocalDate dateNeeded = LocalDate.now();
		LocalDateTime submittedDate = LocalDateTime.now();
		PurchaseRequest pr = new PurchaseRequest(u, "description", "justification", dateNeeded,
				"deliveryMode", "status", 100.00, submittedDate, "reasonForRejection");
		try {
			assertThat(json.write(pr))
				.extractingJsonPathStringValue("$.description")
				.isEqualTo("description");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}