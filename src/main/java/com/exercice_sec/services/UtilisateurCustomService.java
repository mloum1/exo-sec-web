package com.exercice_sec.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.exercice_sec.models.Utilisateur;
import com.exercice_sec.models.UtilisateurRole;
import com.exercice_sec.repositories.UtilisateurRepository;
import com.exercice_sec.repositories.UtilisateurRoleRepository;

@Service
public class UtilisateurCustomService implements UserDetailsService {

	private final BCryptPasswordEncoder passwordEncoder;
	private final UtilisateurRepository utilisateurRepository;
	private final UtilisateurRoleRepository utilisateurRoleRepository;

	public UtilisateurCustomService(BCryptPasswordEncoder encoder , UtilisateurRepository utilisateurRepository, UtilisateurRoleRepository utilisateurRoleRepository ) {
		this.passwordEncoder = encoder;
		this.utilisateurRepository = utilisateurRepository;
		this.utilisateurRoleRepository = utilisateurRoleRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Utilisateur utilisateur = utilisateurRepository.findByNom(username).orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable : " + username));
		return new User(utilisateur.getNom(), utilisateur.getPassword(), this.buildAuthorities(utilisateur.getRoles()));
	}

	private List<GrantedAuthority> buildAuthorities(Collection<UtilisateurRole> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (UtilisateurRole role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getNom().toUpperCase()));
		}
		return authorities;
	}

	/**
	 * Permet d'ajouter un utilisateur à la base de données avec ses roles.
	 * @param utilisateur l'utilisateur à ajouter dans la base de données.
	 * @return l'utilisateur sauvegardé.
	 */
	public Utilisateur ajouterUtilisateur (Utilisateur utilisateur) {
		utilisateurRepository.findByNom(utilisateur.getNom()).ifPresent(u -> {
			throw new IllegalArgumentException("Un utilisateur avec le nom '" + utilisateur.getNom() + "' existe déjà.");
		});

		utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
		Set<UtilisateurRole> rolesPourUtilisateur = new HashSet<>();

		for (UtilisateurRole role : utilisateur.getRoles()) {
			UtilisateurRole roleExistant = utilisateurRoleRepository
					.findByNom(role.getNom())
					.orElseGet(() -> utilisateurRoleRepository.save(role));

			rolesPourUtilisateur.add(roleExistant);
		}

		utilisateur.setRoles(rolesPourUtilisateur);
		return utilisateurRepository.save(utilisateur);
	}
}
