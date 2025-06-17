package com.exercice_sec.services;

import com.exercice_sec.models.Client;
import com.exercice_sec.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ClientService {

	private final ClientRepository clientRepository;

	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	/**
	 * Récupère un client par son ID.
	 * @param id identifiant du client
	 * @return Client trouvé
	 */
	public Client recupererClient(Long id) {
		return clientRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Client non trouvé avec l'ID : " + id));
	}

	/**
	 * Récupère tous les clients.
	 * @return liste de tous les clients
	 */
	public Collection<Client> recupererClients() {
		return clientRepository.findAll();
	}

	/**
	 * Ajoute un nouveau client.
	 * @param client à ajouter
	 * @return client sauvegardé
	 */
	public Client ajouterClient(Client client) {
		return clientRepository.save(client);
	}

	/**
	 * Supprime un client par ID.
	 * @param id identifiant du client à supprimer
	 */
	public void supprimerClient(Long id) {
		if (!clientRepository.existsById(id)) {
			throw new IllegalArgumentException("Client non trouvé pour suppression avec l'ID : " + id);
		}
		clientRepository.deleteById(id);
	}
}
