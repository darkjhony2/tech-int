package org.jquezadp.microservice.client.management.services;

import org.jquezadp.microservice.client.management.controllers.requests.CreateClientRequest;
import org.jquezadp.microservice.client.management.controllers.responses.ClientResponse;
import org.jquezadp.microservice.client.management.controllers.responses.CreateClientResponse;
import org.jquezadp.microservice.client.management.controllers.requests.UpdateClientRequest;
import org.jquezadp.microservice.client.management.controllers.responses.DeleteClientResponse;
import org.jquezadp.microservice.client.management.controllers.responses.UpdateClientResponse;

import java.util.List;

public interface ClientService {
    CreateClientResponse create(CreateClientRequest createClientRequest) throws Exception;
    List<ClientResponse> findAll(Integer page, Integer pageSize) throws Exception;
    ClientResponse findById(Long id) throws Exception;
    UpdateClientResponse update(Long id, UpdateClientRequest updateClientRequest) throws Exception;
    DeleteClientResponse safeDelete(Long id) throws Exception;
    DeleteClientResponse hardDelete(Long id) throws Exception;
}
