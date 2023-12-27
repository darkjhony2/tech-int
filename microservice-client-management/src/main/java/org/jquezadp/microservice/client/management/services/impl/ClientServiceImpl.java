package org.jquezadp.microservice.client.management.services.impl;

import org.jquezadp.microservice.client.management.controllers.requests.CreateClientRequest;
import org.jquezadp.microservice.client.management.controllers.responses.ClientResponse;
import org.jquezadp.microservice.client.management.controllers.responses.CreateClientResponse;
import org.jquezadp.microservice.client.management.entities.Client;
import org.jquezadp.microservice.client.management.respositories.ClientRepository;
import org.jquezadp.microservice.client.management.services.ClientService;
import org.jquezadp.microservice.client.management.controllers.requests.UpdateClientRequest;
import org.jquezadp.microservice.client.management.controllers.responses.DeleteClientResponse;
import org.jquezadp.microservice.client.management.controllers.responses.UpdateClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final Logger LOG = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository repository;

    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public CreateClientResponse create(CreateClientRequest createClientRequest) throws Exception {
        CreateClientResponse createClientResponse = new CreateClientResponse();
        try {
            Client client = CreateClientRequest.mapToEntity(createClientRequest);
            repository.save(client);
            createClientResponse.setCreated(true);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return  createClientResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findAll(Integer page, Integer pageSize) throws Exception {
        List<ClientResponse> clientResponses = new ArrayList<>();
        List<Client> clients = new ArrayList<>();
        try {
            if (page != null && pageSize != null) {
                Pageable pageable = PageRequest.of(page, pageSize);
                Page<Client> pageResult =  repository.findAll(pageable);
                clients = pageResult.getContent();
            }
            if (page == null || pageSize == null) {
                clients = repository.findAll();
            }
            for (Client client : clients) {
                clientResponses.add(ClientResponse.mapEntityToResponse(client));
            }
            if (!CollectionUtils.isEmpty(clientResponses)) {
                return clientResponses;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return clientResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse findById(Long id) throws Exception {
        try {
            Optional<Client> optionalClient = repository.findById(id);
            Client client = optionalClient.orElse(null);
            if (client != null) {
                return ClientResponse.mapEntityToResponse(client);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public UpdateClientResponse update(Long id, UpdateClientRequest updateClientRequest) throws Exception {
        try {
            if (updateClientRequest == null) throw new Exception("Client update request body has not been given.");
            Optional<Client> optionalClient = repository.findById(id);
            if (optionalClient.isPresent()) {
                Client clientDb = optionalClient.get();
                Client clientToBeUpdated = UpdateClientRequest.mapToEntity(updateClientRequest);
                clientToBeUpdated.setId(clientDb.getId());
                repository.save(clientToBeUpdated);
                UpdateClientResponse updateClientResponse = new UpdateClientResponse();
                updateClientResponse.setUpdated(true);
                return updateClientResponse;
            } else {
                throw new Exception("Client not found with the id given.");
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public DeleteClientResponse safeDelete(Long id) throws Exception {
        DeleteClientResponse deleteClientResponse = new DeleteClientResponse();
        try {
            Optional<Client> optionalClient = repository.findById(id);
            if (optionalClient.isPresent()) {
                Client client = optionalClient.get();
                client.setStatus(false);
                repository.save(client);
                deleteClientResponse.setDeleted(true);
            } else throw new NoSuchElementException("Client not found with the given id");
            return deleteClientResponse;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public DeleteClientResponse hardDelete(Long id) throws Exception {
        DeleteClientResponse deleteClientResponse = new DeleteClientResponse();
        try {
            Optional<Client> optionalClient = repository.findById(id);
            if (optionalClient.isPresent()) {
                Client client = optionalClient.get();
                repository.delete(client);
                deleteClientResponse.setDeleted(true);
            } else throw new NoSuchElementException("Client not found with the id given");
            return deleteClientResponse;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
