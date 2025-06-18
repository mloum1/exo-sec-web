package com.exercice_sec.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.exercice_sec.models.Utilisateur;
import com.exercice_sec.repositories.UtilisateurRepository;
import com.exercice_sec.services.JwtService;

/**
 * Classe de test de l'accès aux controllers publics.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PublicControllersTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private JwtService jwtService;

	@BeforeEach
	void setupUser() {
		utilisateurRepository.deleteAll();

		Utilisateur user = new Utilisateur();
		user.setNom("Loum");
		user.setPrenom("Test");
		user.setPassword(encoder.encode("password123"));
		user.setRoles(Set.of("ADMIN"));
		utilisateurRepository.save(user);
	}
	@Test
	void shouldReturn200() throws Exception {
		mockMvc.perform(get("/api/clients"))
				.andExpect(status().isOk());
	}

	@Test
	void shouldGenerateTokenWithExpectedAuthorities() {
		String username = "Loum";
		String password = "password123";

		var authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
		var authentication = new UsernamePasswordAuthenticationToken(username, password, authorities);

		String token = jwtService.genererToken(authentication);

		assertNotNull(token);
		assertTrue(jwtService.validerToken(token));
		String extractedUsername = jwtService.getUsername(token);
		assertEquals(username, extractedUsername);
	}

	@Test
	void shouldLogin() throws Exception {
		String username = "Loum";
		String password = "password123";
		String auth = username + ":" + password;
		String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
		String headerValue = "Basic " + encodedAuth;

		MvcResult result = mockMvc.perform(
						post("/api/utilisateur/login")
								.header(HttpHeaders.AUTHORIZATION, headerValue)
				)
				.andExpect(status().isOk())
				.andReturn();

		String token = result.getResponse().getContentAsString();
		assertNotNull(token);
		assertTrue(jwtService.validerToken(token));
	}
}
