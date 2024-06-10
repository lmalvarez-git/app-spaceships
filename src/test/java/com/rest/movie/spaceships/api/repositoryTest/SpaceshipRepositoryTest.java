package com.rest.movie.spaceships.api.repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.rest.movie.spaceships.api.model.Spaceship;
import com.rest.movie.spaceships.api.repository.SpaceshipRepository;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class SpaceshipRepositoryTest {

	@Autowired
	private SpaceshipRepository repository;

	@Test
	void testFindAll() {
		final List<Spaceship> spaceships = repository.findAll();
		assertNotNull(spaceships);
		assertFalse(spaceships.isEmpty());
		assertEquals(4, spaceships.size());
	}

	@Test
	void testFindById() {
		final Optional<Spaceship> spaceship = repository.findById(1L);
		assertTrue(spaceship.isPresent());
		assertEquals("X-Wing", spaceship.get().getName());
	}

	@Test
	void testFindByNameContaining() {
		final List<Spaceship> spaceships = repository.findByNameContainingIgnoreCase("Halcón");
		assertNotNull(spaceships);
		assertFalse(spaceships.isEmpty());
		assertEquals("Halcón Milenario", spaceships.getFirst().getName());
	}

	@Test
	void testSaveSpaceship() {
		final Spaceship spaceship = Spaceship.builder().name("Serenity").movieSeries("Firefly").build();
		final Spaceship savedSpaceship = repository.save(spaceship);
		assertNotNull(savedSpaceship);
		assertNotNull(savedSpaceship.getId());
		assertEquals("Serenity", savedSpaceship.getName());
	}

	@Test
	void testDeleteSpaceship() {
		repository.deleteById(4L);
		final Optional<Spaceship> spaceship = repository.findById(4L);
		assertFalse(spaceship.isPresent());
	}

}
