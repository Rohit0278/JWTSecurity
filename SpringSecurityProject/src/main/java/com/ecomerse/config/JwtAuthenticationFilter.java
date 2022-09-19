package com.ecomerse.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecomerse.services.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private JwtUtils jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestTokenHeader = request.getHeader("Authorization");
		
		String username=null;
		String jwtToken= null;
		
		if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			
		jwtToken  =	requestTokenHeader.substring(7);
		
		try {
			
	    username  =	jwtUtil.extractUsername(jwtToken);
	    
		
		}catch(Exception e) {
			e.printStackTrace();
		}	
		
	}  else {
			System.out.println("token Unavilable");
		}
		
		// validation of token
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			final UserDetails userdetails = userDetailsServiceImpl.loadUserByUsername(username);
			
			if(this.jwtUtil.validateToken(jwtToken, userdetails)) {
				//token is validated and have to set in context
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthentication = 
						new UsernamePasswordAuthenticationToken(userdetails, null,userdetails.getAuthorities());
				
				usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
				
			}
			
		}else {
			
			System.out.println("nonvalid token");
		}
		
		filterChain.doFilter(request, response);
		
		
		
	}

}
