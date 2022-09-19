package com.ecomerse.services;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecomerse.Exceptions.InvalidIdOfUsers;
import com.ecomerse.model.User;
import com.ecomerse.model.UserRole;
import com.ecomerse.repo.RoleRepository;
import com.ecomerse.repo.UserRepository;

@Service

public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {
		
		User local = this.userRepository.findByUsername(user.getUsername());
		if(local != null) {
			System.out.println("User is Already There ");
			throw new Exception("User already present..");
		}else {
			//create user
			
			for(UserRole ur : userRoles) 
			{
				roleRepository.save(ur.getRole());
			}
			
			user.getUserRole().addAll(userRoles);
			 local = this.userRepository.save(user);
			
			
		}
		
		return local ;
	}

	//get user by username
	@Override
	public User getUser(String username) {
		return this.userRepository.findByUsername(username);
		
	}

	// Update the user
	@Override
	public User updateUser(User user, int id) {
		User us = userRepository.findAllById(id);
		us.setUsername(user.getUsername());
		us.setPassword(user.getPassword());
		us.setFirstname(user.getFirstname());
		us.setLastname(user.getLastname());
		us.setDateofbirth(user.getDateofbirth());
		us.setAddress(user.getAddress());
		us.setPincode(user.getPincode());
		return this.userRepository.save(us);
	}

	
	public void deleteUser(int Id) {
		
		if(Id != 1) {
		this.userRepository.deleteById(Id);
		}else {
			throw new InvalidIdOfUsers("Invalid id");
		}
	}

	@Override
	public List<User> getUserbySurname(String lastname) {
	
	    return this.userRepository.getUSerbySurname(lastname);
	}

	@Override
	public List<User> getAllUsers() {
		
		return userRepository.findAll();
	}

	@Override
	public List<User> getUsrbySorting(String field) {
		
		return userRepository.findAll(Sort.by(Direction.ASC ,field));
	}
	
}
