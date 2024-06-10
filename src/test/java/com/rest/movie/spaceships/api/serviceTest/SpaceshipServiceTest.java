package com.rest.movie.spaceships.api.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.rest.movie.spaceships.api.model.Spaceship;
import com.rest.movie.spaceships.api.repository.SpaceshipRepository;
import com.rest.movie.spaceships.api.service.SpaceshipService;

@ExtendWith(MockitoExtension.class)
class SpaceshipServiceTest {

	@Mock
	private SpaceshipRepository repository;

	@InjectMocks
	private SpaceshipService service;

	@Test
	void testFindAll() {
		final Pageable pageable = PageRequest.of(0, 10);
		final List<Spaceship> expectedSpaceships = List.of(
				getSpacheShip(1L, "X-Wing", "Star Wars"),
				getSpacheShip(2L, "USS Enterprise", "Star Trek"));
		final Page<Spaceship> expectedPage = new PageImpl<>(expectedSpaceships);

		when(repository.findAll(pageable)).thenReturn(expectedPage);

		final Page<Spaceship> result = service.findAll(pageable);

		assertNotNull(result);
		assertEquals(2, result.getTotalElements());
		assertEquals("X-Wing", result.getContent().getFirst().getName());
		verify(repository, times(1)).findAll(pageable);
	}

	@Test
	void testFindById() {
		final Spaceship expectedSpaceship = getSpacheShip(1L, "X-Wing", "Star Wars");
		when(repository.findById(1L)).thenReturn(Optional.of(expectedSpaceship));

		final Optional<Spaceship> result = service.findById(1L);

		assertTrue(result.isPresent());
		assertEquals("X-Wing", result.get().getName());
		verify(repository, times(1)).findById(1L);
	}

	@Test
	void testFindByNameContaining() {
		final List<Spaceship> expectedSpaceships = List.of(
				getSpacheShip(1L, "X-Wing", "Star Wars"),
				getSpacheShip(2L, "Wing Commander", "Wing Commander"));
		when(repository.findByNameContainingIgnoreCase("Wing")).thenReturn(expectedSpaceships);

		final List<Spaceship> result = service.findByNameContaining("Wing");

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("X-Wing", result.getFirst().getName());
		verify(repository, times(1)).findByNameContainingIgnoreCase("Wing");
	}

	@Test
	void testSaveSpaceship() {
		final Spaceship spaceshipToSave = getSpacheShip(null, "Serenity", "Firefly");
		final Spaceship savedSpaceship = getSpacheShip(1L, "Serenity", "Firefly");

		when(repository.save(spaceshipToSave)).thenReturn(savedSpaceship);

		final Spaceship result = service.save(spaceshipToSave);

		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals("Serenity", result.getName());
		verify(repository, times(1)).save(spaceshipToSave);
	}

	@Test
	void testDeleteById() {
		service.deleteById(1L);
		verify(repository, times(1)).deleteById(1L);
	}
	
	
	private Spaceship getSpacheShip(Long id, String name, String movieSeries) {
		return Spaceship.builder()
				.id(id)
				.name(name)
				.movieSeries(movieSeries)
				.build();
	}
}
