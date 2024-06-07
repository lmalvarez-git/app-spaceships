package com.rest.movie.spaceships.api.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rest.movie.spaceships.api.model.Spaceship;
import com.rest.movie.spaceships.api.repository.SpaceshipRepository;
import com.rest.movie.spaceships.api.service.SpaceshipService;

@SpringBootTest
class SpaceshipServiceTest {

	@Autowired
	private SpaceshipService service;

	@MockBean
	private SpaceshipRepository repository;

	@Test
	void testFindById() {
		final Spaceship spaceship = Spaceship.builder().id(1L).name("X-Wing").build();

		Mockito.when(repository.findById(1L)).thenReturn(Optional.of(spaceship));
		final Optional<Spaceship> result = service.findById(1L);

		assertTrue(result.isPresent());
		assertEquals("X-Wing", result.get().getName());
	}
}
