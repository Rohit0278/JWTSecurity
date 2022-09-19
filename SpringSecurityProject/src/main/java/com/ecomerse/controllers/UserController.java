package com.ecomerse.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecomerse.model.Role;
import com.ecomerse.model.User;
import com.ecomerse.model.UserRole;
import com.ecomerse.repo.UserRepository;
import com.ecomerse.services.UserService;

@RestController
@RequestMapping("/User")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	// Create User

	@PostMapping("/") // Registarion
	public User createUser(@RequestBody User user) throws Exception {

		Set<UserRole> roles = new HashSet<>();
		Role role = new Role();	
		role.setRoleId(2);
		role.setRole("ROLE_USER");

		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);

		roles.add(userRole);
		return this.userService.createUser(user, roles);
	}

	@GetMapping("/{username}")
	public User getUser(@PathVariable("username") String username) {
		return this.userService.getUser(username);
	}

	@GetMapping("/sort/{field}")
	public List<User> getUsrbySorting(@PathVariable("field") String field) {
		return this.userService.getUsrbySorting(field);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/surname/{lastname}")
	public ResponseEntity<List<User>> getUserbySurname(@PathVariable("lastname") String lastname) {
		return new ResponseEntity<>(userService.getUserbySurname(lastname), HttpStatus.OK);

	}

	
	// get user by pincode and name
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/empbynamepin")
	public List<User> findBynameandpin(@RequestParam String firstname, @RequestParam int pincode) {
		return userRepository.findByFirstnameAndPincode(firstname, pincode);

	}

	@PutMapping("/{id}")
	public void updateUser(@RequestBody User user, @PathVariable("id") int id) {
		this.userService.updateUser(user, id);

	}

	// Delete User By username
	// Only allowed to admin 
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{Id}")
	public void deleteUser(@PathVariable("Id") int Id) {

		this.userService.deleteUser(Id);
	}

	// Find all users

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getAllUsers") 
	public @ResponseBody List<User> getAllUsers() throws Exception {
		return userService.getAllUsers();

	}
}
