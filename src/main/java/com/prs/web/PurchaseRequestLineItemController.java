package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.JsonResponse;
import com.prs.business.PurchaseRequest;
import com.prs.business.PurchaseRequestLineItem;
import com.prs.db.PurchaseRequestLineItemRepository;
import com.prs.db.PurchaseRequestRepository;

@RestController
@RequestMapping("/purchase-request-line-items")
public class PurchaseRequestLineItemController {

	@Autowired
	private PurchaseRequestLineItemRepository purchaseRequestLineItemRepository;
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepository;

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
			recalculateTotal(prli.getPurchaseRequest());
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
				recalculateTotal(prli.getPurchaseRequest());
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
				recalculateTotal(prli.getPurchaseRequest());
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

	private void recalculateTotal(PurchaseRequest pr) {
		double sum = 0.00;
		Iterable<PurchaseRequestLineItem> prli = purchaseRequestLineItemRepository.findAllByPurchaseRequestId(pr.getId());
		for (PurchaseRequestLineItem purchaseRequestLineItem : prli) {
			sum += purchaseRequestLineItem.getQuantity()*purchaseRequestLineItem.getProduct().getPrice();
		}
		pr.setTotal(sum);
		purchaseRequestRepository.save(pr);	
	}
	
	@GetMapping("/lines-for-pr/{id}")
	public JsonResponse requestReview(@PathVariable int id) {
		JsonResponse jr = null;		
		try {
			if(purchaseRequestLineItemRepository.existsById(id)) {
				Iterable<PurchaseRequestLineItem> purchaseRequestLineItems = purchaseRequestLineItemRepository.findAllByPurchaseRequestId(id);
			jr=JsonResponse.getInstance(purchaseRequestLineItems);
			}
			else {
				jr = JsonResponse.getInstance("No purchase request available with this ID");
			}
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
		}
	
	

}
