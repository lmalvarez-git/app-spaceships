package com.rest.movie.spaceships.api.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.movie.spaceships.api.model.Spaceship;
import com.rest.movie.spaceships.api.service.SpaceshipService;

@RestController
@RequestMapping("/spaceships")
public class SpaceshipController {

	private final SpaceshipService service;

	SpaceshipController(SpaceshipService spaceshipService) {
		this.service = spaceshipService;
	}

	@GetMapping
	public ResponseEntity<List<Spaceship>> getAllSpaceships(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		final Page<Spaceship> pageResult = service.findAll(PageRequest.of(page, size));
		return new ResponseEntity<>(pageResult.getContent(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Spaceship> getSpaceshipById(@PathVariable Long id) {
		return service.findById(id).map(spaceship -> new ResponseEntity<>(spaceship, HttpStatus.OK))
			.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/search")
	public ResponseEntity<List<Spaceship>> getSpaceshipsByName(@RequestParam String name) {
		return new ResponseEntity<>(service.findByNameContaining(name), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Spaceship> createSpaceship(@RequestBody Spaceship spaceship) {
		return new ResponseEntity<>(service.save(spaceship), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Spaceship> updateSpaceship(@PathVariable Long id, @RequestBody Spaceship spaceship) {
		return service.findById(id).map(existSpaceship -> {
			spaceship.setId(id);
			return new ResponseEntity<>(service.save(spaceship), HttpStatus.OK);
		}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSpaceship(@PathVariable Long id) {
		service.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
