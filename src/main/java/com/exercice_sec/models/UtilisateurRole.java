package com.exercice_sec.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "utilisateur_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurRole {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utilisateur_role_seq")
	@SequenceGenerator(name = "utilisateur_role_seq", sequenceName = "utilisateur_role_seq", allocationSize = 1)
	@Column(name = "id_utilisateur_role")
	private Long id;

	@Column(name = "nom", nullable = false)
	private String nom;
}
