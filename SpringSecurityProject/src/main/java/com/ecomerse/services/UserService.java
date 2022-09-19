package com.ecomerse.services;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.ecomerse.model.User;
import com.ecomerse.model.UserRole;


public interface UserService {


	//To Create User
		public User createUser(User user , Set<UserRole> userRoles) throws Exception ;
		
		//to get user-by username
		public User getUser(String username);
		
		//Get User by some sorting order
		public List<User> getUsrbySorting(String field);

		
		//to update the user 
		public User updateUser(User user, int id);
		

		// Delete by Username
		public void deleteUser(int Id);

		
		//Get By Surname
		public List<User> getUserbySurname(String lastname);

		public List<User> getAllUsers();

		
		
}
