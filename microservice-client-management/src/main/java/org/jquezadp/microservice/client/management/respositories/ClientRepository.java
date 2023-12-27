package org.jquezadp.microservice.client.management.respositories;

import org.jquezadp.microservice.client.management.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}