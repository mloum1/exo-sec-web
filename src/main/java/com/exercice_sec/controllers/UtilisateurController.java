package com.exercice_sec.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercice_sec.models.Utilisateur;
import com.exercice_sec.services.UtilisateurCustomService;

/**
 * Controller pour les utilisateurs.
 */
@RestController
@RequestMapping("/api/utilisateur")
public class UtilisateurController {

	private final UtilisateurCustomService utilisateurService;

	public UtilisateurController(UtilisateurCustomService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	@PostMapping("/ajouter")
	public Utilisateur ajouterUtilisateur (@RequestBody Utilisateur utilisateur) {
		return utilisateurService.ajouterUtilisateur(utilisateur);
	}

}
