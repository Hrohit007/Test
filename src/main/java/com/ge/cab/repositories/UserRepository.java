package com.ge.cab.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ge.cab.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findBySsoAndContactNo(long sso, long contactNo);
}
	