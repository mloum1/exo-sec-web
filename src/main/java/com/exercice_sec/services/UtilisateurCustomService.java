package com.exercice_sec.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.exercice_sec.models.Utilisateur;
import com.exercice_sec.repositories.UtilisateurRepository;

@Service
public class UtilisateurCustomService implements UserDetailsService {

	private final BCryptPasswordEncoder passwordEncoder;
	private final UtilisateurRepository utilisateurRepository;

	public UtilisateurCustomService(BCryptPasswordEncoder encoder, UtilisateurRepository utilisateurRepository) {
		this.passwordEncoder = encoder;
		this.utilisateurRepository = utilisateurRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Utilisateur utilisateur = utilisateurRepository.findByNom(username)
				.orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable : " + username));

		return new User(
				utilisateur.getNom(),
				utilisateur.getPassword(),
				buildAuthorities(utilisateur.getRoles())
		);
	}

	private List<GrantedAuthority> buildAuthorities(Set<String> roles) {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
				.collect(Collectors.toList());
	}

	/**
	 * Permet d'ajouter un utilisateur à la base de données avec ses rôles.
	 * @param utilisateur l'utilisateur à ajouter dans la base de données.
	 * @return l'utilisateur sauvegardé.
	 */
	public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
		utilisateurRepository.findByNom(utilisateur.getNom()).ifPresent(u -> {
			throw new IllegalArgumentException("Un utilisateur avec le nom '" + utilisateur.getNom() + "' existe déjà.");
		});

		utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
		return utilisateurRepository.save(utilisateur);
	}
}
