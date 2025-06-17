package com.exercice_sec.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.exercice_sec.models.UtilisateurRole;

public interface UtilisateurRoleRepository extends JpaRepository<UtilisateurRole, Long> {
	Optional<UtilisateurRole> findByNom(String role);
}
