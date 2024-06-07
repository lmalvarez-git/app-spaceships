package com.rest.movie.spaceships.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.movie.spaceships.api.model.Spaceship;

@Repository
public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> {
	List<Spaceship> findByNameContainingIgnoreCase(String name);

}
