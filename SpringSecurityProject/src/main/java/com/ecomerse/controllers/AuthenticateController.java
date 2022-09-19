package com.ecomerse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecomerse.config.JwtUtils;
import com.ecomerse.model.JwtRequest;
import com.ecomerse.model.JwtResponse;
import com.ecomerse.services.UserDetailsServiceImpl;

@RestController
public class AuthenticateController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@PostMapping("/generate-token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
		try {
			
			authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
			
		}catch(Exception e) {
		
			throw new Exception("user not found");
		}
		
		UserDetails us = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		
		String token = jwtUtils.generateToken(us);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	
	
	private void authenticate(String username , String password) throws Exception {
		
		try {
			authenticationManager.authenticate
			(new UsernamePasswordAuthenticationToken(username , password));
			
			
		}catch(DisabledException e) {
			throw new Exception("User dis able");
		}catch(BadCredentialsException e) {
			throw new Exception("bad data");
		}
	}
}
