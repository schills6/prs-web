package com.prs.web;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.JsonResponse;
import com.prs.business.PurchaseRequest;
import com.prs.db.PurchaseRequestRepository;

@RestController
@RequestMapping("/purchase-requests")
public class PurchaseRequestController {

	@Autowired
	private PurchaseRequestRepository purchaseRequestRepository;

	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(purchaseRequestRepository.findAll());
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequest> pr = purchaseRequestRepository.findById(id);
			if (pr.isPresent())
				jr = JsonResponse.getInstance(pr);
			else
				jr = JsonResponse.getInstance("No purchase request found for id: " + id);
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PostMapping("/")
	public JsonResponse add(@RequestBody PurchaseRequest pr) {
		JsonResponse jr = null;
		// NOTE: may need to enhance exception handling if more than one exception type
		// needs to be caught
		try {
			jr = JsonResponse.getInstance(purchaseRequestRepository.save(pr));
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PutMapping("/")
	public JsonResponse update(@RequestBody PurchaseRequest pr) {
		JsonResponse jr = null;
		// NOTE: may need to enhance exception handling if more than one exception type
		// needs to be caught
		try {
			if (purchaseRequestRepository.existsById(pr.getId())) {
				jr = JsonResponse.getInstance(purchaseRequestRepository.save(pr));
			} else {
				jr = JsonResponse
						.getInstance("Purchase Request id: " + pr.getId() + "does not exist and you are attempting to save it.");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@DeleteMapping("/")
	public JsonResponse delete(@RequestBody PurchaseRequest pr) {
		JsonResponse jr = null;
		// NOTE: may need to enhance exception handling if more than one exception type
		// needs to be caught
		try {
			if (purchaseRequestRepository.existsById(pr.getId())) {
				purchaseRequestRepository.delete(pr);
				jr = JsonResponse.getInstance("PurchaseRequest deleted.");
			} else {
				jr = JsonResponse
						.getInstance("Purchase Request id: " + pr.getId() + " does not exist and you are attempting to delete it.");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@PostMapping("/submit-new")
	public JsonResponse submitNew(@RequestBody PurchaseRequest pr) {
		JsonResponse jr = null;
		try {
				pr.setStatus("New");
				pr.setSubmittedDate(LocalDateTime.now());
				purchaseRequestRepository.save(pr);
			}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;		
	}

	
}
