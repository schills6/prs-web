package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.JsonResponse;
import com.prs.business.User;
import com.prs.db.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(userRepository.findAll());
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<User> u = userRepository.findById(id);
			if (u.isPresent())
				jr = JsonResponse.getInstance(u);
			else
				jr = JsonResponse.getInstance("No user found for id: " + id);
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PostMapping("/")
	public JsonResponse add(@RequestBody User u) {
		JsonResponse jr = null;
		// NOTE: may need to enhance exception handling if more than one exception type
		// needs to be caught
		try {
			jr = JsonResponse.getInstance(userRepository.save(u));
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PutMapping("/")
	public JsonResponse update(@RequestBody User u) {
		JsonResponse jr = null;
		// NOTE: may need to enhance exception handling if more than one exception type
		// needs to be caught
		try {
			if (userRepository.existsById(u.getId())) {
				jr = JsonResponse.getInstance(userRepository.save(u));
			} else {
				jr = JsonResponse
						.getInstance("User id: " + u.getId() + "does not exist and you are attempting to save it.");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@DeleteMapping("/")
	public JsonResponse delete(@RequestBody User u) {
		JsonResponse jr = null;
		// NOTE: may need to enhance exception handling if more than one exception type
		// needs to be caught
		try {
			if (userRepository.existsById(u.getId())) {
				userRepository.delete(u);
				jr = JsonResponse.getInstance("User deleted.");
			} else {
				jr = JsonResponse
						.getInstance("User id: " + u.getId() + " does not exist and you are attempting to delete it.");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PostMapping("/authenticate")
	public JsonResponse authenticate(@RequestBody User u) {
		JsonResponse jr = null;
		String userName = u.getUserName();
		String password = u.getPassword();
		try {
			Optional<User> user = userRepository.findByUserNameAndPassword(userName, password);
			if (user.isPresent())
				jr = JsonResponse.getInstance(user);
			else
				jr = JsonResponse.getInstance("Invalid username or password");
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
}
