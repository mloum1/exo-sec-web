package com.exercice_sec.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercice_sec.models.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

	Optional<Utilisateur> findByNom(String username);
}
