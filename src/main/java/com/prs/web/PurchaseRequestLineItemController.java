package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.JsonResponse;
import com.prs.business.PurchaseRequestLineItem;
import com.prs.db.PurchaseRequestLineItemRepository;

@RestController
@RequestMapping("/purchase-request-line-items")
public class PurchaseRequestLineItemController {

	@Autowired
	private PurchaseRequestLineItemRepository purchaseRequestLineItemRepository;

	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(purchaseRequestLineItemRepository.findAll());
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequestLineItem> prli = purchaseRequestLineItemRepository.findById(id);
			if (prli.isPresent())
				jr = JsonResponse.getInstance(prli);
			else
				jr = JsonResponse.getInstance("No Purchase Request Line Item found for id: " + id);
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PostMapping("/")
	public JsonResponse add(@RequestBody PurchaseRequestLineItem prli) {
		JsonResponse jr = null;
		// NOTE: may need to enhance exception handling if more than one exception type
		// needs to be caught
		try {
			jr = JsonResponse.getInstance(purchaseRequestLineItemRepository.save(prli));
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		
		return jr;
	}

	@PutMapping("/")
	public JsonResponse update(@RequestBody PurchaseRequestLineItem prli) {
		JsonResponse jr = null;
		// NOTE: may need to enhance exception handling if more than one exception type
		// needs to be caught
		try {
			if (purchaseRequestLineItemRepository.existsById(prli.getId())) {
				jr = JsonResponse.getInstance(purchaseRequestLineItemRepository.save(prli));
			} else {
				jr = JsonResponse
						.getInstance("Purchase Request Line Item id: " + prli.getId() + "does not exist and you are attempting to save it.");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@DeleteMapping("/")
	public JsonResponse delete(@RequestBody PurchaseRequestLineItem prli) {
		JsonResponse jr = null;
		// NOTE: may need to enhance exception handling if more than one exception type
		// needs to be caught
		try {
			if (purchaseRequestLineItemRepository.existsById(prli.getId())) {
				purchaseRequestLineItemRepository.delete(prli);
				jr = JsonResponse.getInstance("PurchaseRequestLineItem deleted.");
			} else {
				jr = JsonResponse
						.getInstance("Purchase Request Line Item id: " + prli.getId() + " does not exist and you are attempting to delete it.");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	
}
