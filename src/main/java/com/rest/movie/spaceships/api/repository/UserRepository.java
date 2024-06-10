package com.rest.movie.spaceships.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.rest.movie.spaceships.api.model.Userlogin;

@Repository
public interface UserRepository extends JpaRepository<Userlogin, Long> {
	List<Userlogin> findByUserName(String username);

}
