package com.smartsolution.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smartsolution.dto.UserDto;
import com.smartsolution.service.UserService;
import com.smartsolution.serviceImpl.RazorpayServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private RazorpayServiceImpl razorPayService;
	
	@Autowired
	private UserService userService;

	@GetMapping("/demo")
	public ResponseEntity<String> handleGet() {
		System.out.println("------------------------demo------------");
		return ResponseEntity.ok("GET not supported for create operation");
	}

	@PostMapping("/create")
	public ResponseEntity<Map<String, String>> createUser(
	    @RequestPart("userDto") UserDto userDto,
	    @RequestPart("profileImagePath") MultipartFile profileImagePath
	) throws IOException {
	    UserDto createdUser = userService.createUser(userDto, profileImagePath);
	    return ResponseEntity.ok(Map.of("message", "User saved successfully"));
	}
	

	@PostMapping("/create-order")
	public ResponseEntity<?> createOrder(@RequestParam int amount) {
		System.out.println("call payment method...........!!");
		try {
			String orderResponse = razorPayService.createOrder(amount);
			if (orderResponse != null) {
				return ResponseEntity.ok(orderResponse);
			} else {
				return ResponseEntity.badRequest().body("Failed to create Razorpay order..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("An error occurred while creating the order.");
		}

	}
//	  @DeleteMapping("/delete/{id}")
//	    public ResponseEntity<Boolean> deleteRecord(@PathVariable int id) {
//	        boolean isDeleted = true; 
//
//	        return ResponseEntity.ok(isDeleted);
//	    }
//	
	
}
