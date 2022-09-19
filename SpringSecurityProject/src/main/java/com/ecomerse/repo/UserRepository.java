package com.ecomerse.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecomerse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecomerse.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


public User findByUsername(String username);
	
	//public User findbyID(int id);
	public User findAllById(int id);
	
	@Modifying
	@Transactional
	@Query(value="DELETE  from User b where b.username=?1",nativeQuery=true)
	public void deleteByUsername(String username);
	
	
//	@Modifying
//	@Transactional
//	@Query(value="DELETE  from User b where b.Id=?1",nativeQuery=true)
//	public void deleteById(int Id);
	
	
	
	@Query(value="SELECT * from User ud where ud.lastname=?1",nativeQuery=true)
	public List<User> getUSerbySurname(String lastname);

	
	
	//to find the user by pin and name
	public List<User> findByFirstnameAndPincode(String firstname , int pincode);

}
