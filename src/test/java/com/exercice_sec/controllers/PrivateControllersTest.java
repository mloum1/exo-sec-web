package com.exercice_sec.controllers;

import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test accès aux controllers privées.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PrivateControllersTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldReturn401() throws Exception {
		mockMvc.perform(get("/api/utilisateur/ajouter")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	void shouldReturn200() throws Exception {
		String jsonBody = """
        {
            "nom": "Dupont",
            "prenom": "Jean",
            "email": "jean.dupont@example.com",
            "password": "test1234"
        }
        """;

		mockMvc.perform(post("/api/utilisateur/ajouter")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonBody))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "user", roles = "USER")
	void shouldReturn403() throws Exception {
		String jsonBody = """
    {
        "nom": "Dupont",
        "prenom": "Jean",
        "email": "jean.dupont@example.com",
        "password": "test1234"
    }
    """;

		mockMvc.perform(post("/api/utilisateur/ajouter")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonBody))
				.andExpect(status().isForbidden());
	}
}
