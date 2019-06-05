package com.prs.web;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.JsonResponse;
import com.prs.business.PurchaseRequest;
import com.prs.business.User;
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
				jr = JsonResponse.getInstance(purchaseRequestRepository.save(pr));
			}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;		
	}

	@PutMapping("/submit-review")
	public JsonResponse submitForReview(@RequestBody PurchaseRequest pr) {
		JsonResponse jr = null;
		try {
			if (pr.getTotal() <= 50.00){
				pr.setStatus("Approved");
				pr.setSubmittedDate(LocalDateTime.now());
				purchaseRequestRepository.save(pr);
				jr = JsonResponse.getInstance("Purchase Request Approved.");
			}
			else {
				pr.setStatus("Review");
				pr.setSubmittedDate(LocalDateTime.now());
				purchaseRequestRepository.save(pr);
				jr = JsonResponse.getInstance("Purchase Request submitted for review.");
			}
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@GetMapping("/list-review")
	public JsonResponse requestReview(@RequestBody User u) {
		JsonResponse jr = null;
		try {
			Iterable<PurchaseRequest> purchaseRequests = purchaseRequestRepository.findByStatusAndUserNot("Review", u);
			jr=JsonResponse.getInstance(purchaseRequests);
			}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;		
	}
	
	@PutMapping("/approve")
	public JsonResponse requestApprove(@RequestBody PurchaseRequest pr) {
		JsonResponse jr = null;
		try {
				pr.setStatus("Approved");
				purchaseRequestRepository.save(pr);
				jr = JsonResponse.getInstance("Purchase Request approved.");
			}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@PutMapping("/reject")
	public JsonResponse requestReject(@RequestBody PurchaseRequest pr) {
		JsonResponse jr = null;
		try {
				pr.setStatus("Rejected");
				purchaseRequestRepository.save(pr);
				jr = JsonResponse.getInstance("Purchase Request rejected.");
			}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	
}
