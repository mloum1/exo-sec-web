package com.exercice_sec.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercice_sec.models.Utilisateur;
import com.exercice_sec.services.JwtService;
import com.exercice_sec.services.UtilisateurCustomService;

/**
 * Controller pour les utilisateurs.
 */
@RestController
@RequestMapping("/api/utilisateur")
public class UtilisateurController {


	private JwtService jwtService;
	private final UtilisateurCustomService utilisateurService;

	public UtilisateurController(JwtService jwtService,UtilisateurCustomService utilisateurService) {
		this.jwtService = jwtService;
		this.utilisateurService = utilisateurService;
	}

	@PostMapping("/ajouter")
	@PreAuthorize("hasRole('ADMIN')")
	public Utilisateur ajouterUtilisateur (@RequestBody Utilisateur utilisateur) {
		return utilisateurService.ajouterUtilisateur(utilisateur);
	}

	/**
	 * Permet d'authentifier avec Oauth2 resource server
	 * @param authentication l'authentification.
	 * @return le token généré.
	 */
	@PostMapping("/login")
	public String login(Authentication authentication) {
		return jwtService.genererToken(authentication);
	}

	/**
	 * Permet de s'authentifier avec un jwt customiser suivant la logique de filtre.
	 * @param authentication l'authentification.
	 * @return Le token généré.
	 */
	@PostMapping("/loginWithCustomJwt")
	public String loginwithCustomJwt(Authentication authentication) {
		return jwtService.genererTokenCustomiser(authentication);
	}
}
