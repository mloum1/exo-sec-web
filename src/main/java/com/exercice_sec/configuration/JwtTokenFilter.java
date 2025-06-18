package com.exercice_sec.configuration;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.exercice_sec.services.JwtService;
import com.exercice_sec.services.UtilisateurCustomService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtre JWT personnalisé pour authentifier l'utilisateur via le token.
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UtilisateurCustomService utilisateurCustomService;

	public JwtTokenFilter(JwtService jwtService, UtilisateurCustomService utilisateurCustomService) {
		this.jwtService = jwtService;
		this.utilisateurCustomService = utilisateurCustomService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {

		String header = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = header.substring(7).trim();

		if (!jwtService.validerToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		String username = jwtService.getUsername(token);

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = utilisateurCustomService.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken authToken =
					new UsernamePasswordAuthenticationToken(
							userDetails,
							null,
							userDetails.getAuthorities()
					);
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authToken);
		}

		filterChain.doFilter(request, response);
	}
}
