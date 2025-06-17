package com.exercice_sec.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercice_sec.models.Client;
import com.exercice_sec.services.ClientService;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/client/{id}")
    public Client recupererClient(@PathVariable Long id) {
        return clientService.recupererClient(id);
    }

    @GetMapping("/clients")
    public Collection<Client> recupererClients() {
        return clientService.recupererClients();
    }

    @PostMapping("/ajout/client")
    @PreAuthorize("isAuthenticated()")
    public Client ajouterClient(@RequestBody Client client) {
        return clientService.ajouterClient(client);
    }

    @DeleteMapping("/suppression/client/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void supprimerClient(@PathVariable Long id) {
        clientService.supprimerClient(id);
    }
}
