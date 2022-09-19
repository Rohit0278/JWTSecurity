package com.ecomerse.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecomerse.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
