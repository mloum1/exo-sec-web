package com.exercice_sec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exercice_sec.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}