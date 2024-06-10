package com.rest.movie.spaceships.api.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import com.rest.movie.spaceships.api.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.rest.movie.spaceships.api.controller.SpaceshipController;
import com.rest.movie.spaceships.api.model.Spaceship;
import com.rest.movie.spaceships.api.service.SpaceshipService;

@WebMvcTest(SpaceshipController.class)
@ContextConfiguration(classes = {SpaceshipController.class, SecurityConfig.class})
@Import(SecurityConfig.class)
class SpaceshipControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SpaceshipService service;

	@MockBean
	private PasswordEncoder passwordEncoder;



	@Test
	void testGetAllSpaceships() throws Exception {
		final List<Spaceship> spaceships = List.of(getSpaceship(1L, "X-Wing", "Star Wars"),
				getSpaceship(2L, "USS Enterprise", "Star Trek"));
		final Page<Spaceship> page = new PageImpl<>(spaceships);

		Mockito.when(service.findAll(any(PageRequest.class))).thenReturn(page);

		mockMvc.perform(get("/spaceships")
						.with(user("ADMIN").password("ADMIN").roles("USER", "ADMIN"))
						.param("page", "0")
						.param("size", "10"))
				.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(spaceships.size()))
				.andExpect(jsonPath("$[0].name").value("X-Wing"))
			.andExpect(jsonPath("$[1].name").value("USS Enterprise"));
	}

	@Test
	void testGetSpaceshipById() throws Exception {
		final Spaceship spaceship = getSpaceship(1L, "X-Wing", "Star Wars");

		Mockito.when(service.findById(1L)).thenReturn(Optional.of(spaceship));

		mockMvc.perform(get("/spaceships/1")
						.with(user("ADMIN").password("ADMIN").roles("USER", "ADMIN")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("X-Wing"))
			.andExpect(jsonPath("$.movieSeries").value("Star Wars"));
	}

	@Test
	void testGetSpaceshipByIdNotFound() throws Exception {
		Mockito.when(service.findById(1L)).thenReturn(Optional.empty());

		mockMvc.perform(get("/spaceships/1")
						.with(user("ADMIN").password("ADMIN").roles("USER", "ADMIN")))
				.andExpect(status().isNotFound());
	}

	@Test
	void testGetSpaceshipsByName() throws Exception {
		final List<Spaceship> spaceships = List.of(
				getSpaceship(1L, "X-Wing", "Star Wars"),
				getSpaceship(2L, "Wing Commander", "Wing Commander"));

		Mockito.when(service.findByNameContaining(anyString())).thenReturn(spaceships);

		mockMvc.perform(get("/spaceships/search")
						.with(user("ADMIN").password("ADMIN").roles("USER", "ADMIN"))
						.param("name", "Wing")).andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(spaceships.size()))
				.andExpect(jsonPath("$[0].name").value("X-Wing"))
			.andExpect(jsonPath("$[1].name").value("Wing Commander"));
	}

	@Test
	void testCreateSpaceship() throws Exception {
		Mockito.when(service.save(any(Spaceship.class))).thenReturn(getSpaceship(1L, "Serenity", "Firefly"));

		mockMvc
			.perform(post("/spaceships").with(user("ADMIN").password("ADMIN").roles("USER", "ADMIN"))
					.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\": \"Serenity\", \"movieSeries\": \"Firefly\"}"))
			.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1L))
			.andExpect(jsonPath("$.name").value("Serenity"))
				.andExpect(jsonPath("$.movieSeries").value("Firefly"));
	}

	@Test
	void testUpdateSpaceship() throws Exception {
		final Spaceship spaceship = getSpaceship(1L, "Serenity", "Firefly");

		Mockito.when(service.findById(anyLong())).thenReturn(Optional.of(spaceship));
		Mockito.when(service.save(any(Spaceship.class))).thenReturn(spaceship);

		mockMvc
			.perform(put("/spaceships/1").contentType(MediaType.APPLICATION_JSON)
					.with(user("ADMIN").password("ADMIN").roles("USER", "ADMIN"))
				.content("{\"name\": \"Serenity Updated\", \"movieSeries\": \"Firefly\"}"))
			.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
			.andExpect(jsonPath("$.name").value("Serenity"))
				.andExpect(jsonPath("$.movieSeries").value("Firefly"));
	}

	@Test
	void testUpdateSpaceshipNotFound() throws Exception {
		Mockito.when(service.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc
			.perform(put("/spaceships/1")
					.with(user("ADMIN").password("ADMIN").roles("USER", "ADMIN"))
					.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\": \"Serenity Updated\", \"movieSeries\": \"Firefly\"}"))
			.andExpect(status().isNotFound());
	}

	@Test
	void testDeleteSpaceship() throws Exception {
		doNothing().when(service).deleteById(1L);

		mockMvc.perform(delete("/spaceships/1")
				.with(user("ADMIN").password("ADMIN").roles("USER", "ADMIN")))
				.andExpect(status().isNoContent());

		verify(service, times(1)).deleteById(1L);
	}
	
	private Spaceship getSpaceship(Long id, String name, String movieSeries) {
		return Spaceship.builder().id(id).name(name).movieSeries(movieSeries).build();
	}

}
