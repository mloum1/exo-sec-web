package com.exercice_sec.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "utilisateur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur extends Personne {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utilisateur_seq")
	@SequenceGenerator(name = "utilisateur_seq", sequenceName = "utilisateur_seq", allocationSize = 1)
	@Column(name = "id_utilisateur")
	private Long id;

	@Column(name = "mot_de_passe", nullable = false)
	private String password;

	/**
	 * Un utilisateur peut avoir plusieurs rôles.
	 * Ils sont stockés sous forme de liste de chaînes.
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "utilisateur_roles",
			joinColumns = @JoinColumn(name = "utilisateur_id")
	)
	@Column(name = "role")
	private Set<String> roles = new HashSet<>();
}
