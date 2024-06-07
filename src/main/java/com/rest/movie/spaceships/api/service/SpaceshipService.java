package com.rest.movie.spaceships.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rest.movie.spaceships.api.model.Spaceship;
import com.rest.movie.spaceships.api.repository.SpaceshipRepository;

@Service
public class SpaceshipService {
	
	private final SpaceshipRepository spaceshipRepository;
	
	public SpaceshipService(SpaceshipRepository spaceshipRepository) {
		this.spaceshipRepository = spaceshipRepository;
	}
	
	@Cacheable("spaceships")
    public Page<Spaceship> findAll(Pageable pageable) {
        return spaceshipRepository.findAll(pageable);
    }

    public Optional<Spaceship> findById(Long id) {
        return spaceshipRepository.findById(id);
    }

    public List<Spaceship> findByNameContaining(String name) {
        return spaceshipRepository.findByNameContainingIgnoreCase(name);
    }

    public Spaceship save(Spaceship spaceship) {
        return spaceshipRepository.save(spaceship);
    }

    public void deleteById(Long id) {
    	spaceshipRepository.deleteById(id);
    }

}
