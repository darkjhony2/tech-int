package org.jquezadp.microservice.account.management.services.impl;

import org.apache.coyote.BadRequestException;
import org.jquezadp.microservice.account.management.clients.ClientRestClient;
import org.jquezadp.microservice.account.management.controllers.requests.account.CreateAccountRequest;
import org.jquezadp.microservice.account.management.controllers.requests.account.UpdateAccountRequest;
import org.jquezadp.microservice.account.management.controllers.responses.account.AccountResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.CreateBaseResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.DeleteBaseResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.UpdateBaseResponse;
import org.jquezadp.microservice.account.management.controllers.responses.client.ClientResponse;
import org.jquezadp.microservice.account.management.entities.Account;
import org.jquezadp.microservice.account.management.exceptions.InactiveEntityException;
import org.jquezadp.microservice.account.management.repository.AccountRepository;
import org.jquezadp.microservice.account.management.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final ClientRestClient clientRestClient;
    private final AccountRepository repository;

    public AccountServiceImpl(ClientRestClient clientRestClient, AccountRepository repository) {
        this.clientRestClient = clientRestClient;
        this.repository = repository;
    }

    @Override
    @Transactional
    public CreateBaseResponse create(CreateAccountRequest createAccountRequest) throws Exception {
        CreateBaseResponse createBaseResponse = new CreateBaseResponse();
        try {
            if (createAccountRequest == null) throw new BadRequestException("Body has not been received");
            ClientResponse clientResponse = clientRestClient.findById(createAccountRequest.getClientId());
            if (clientResponse == null) throw new NoSuchElementException("Client not found with the given id.");
            if (!clientResponse.isStatus()) throw new InactiveEntityException("Client is inactive");
            Account account = CreateAccountRequest.mapToEntity(createAccountRequest);
            repository.save(account);
            createBaseResponse.setCreated(true);
            return createBaseResponse;
        } catch (NoSuchElementException e) {
            LOG.error(e.getMessage());
            throw new NoSuchElementException(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> findAll(Integer page, Integer pageSize) throws Exception {
        List<AccountResponse> accountResponses = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        try {
            Map<Long, ClientResponse> clientResponseHashMap = new HashMap<>();
            if (page != null && pageSize != null) {
                Pageable pageable = PageRequest.of(page, pageSize);
                Page<Account> pageResult =  repository.findAll(pageable);
                accounts = pageResult.getContent();
            }
            if (page == null || pageSize == null) {
                accounts = repository.findAll();
            }
            List<ClientResponse> clientResponses = clientRestClient.fetchAll(null, null);
            if (!CollectionUtils.isEmpty(clientResponses)) {
                clientResponseHashMap = clientResponses.stream().collect(Collectors.toMap(ClientResponse::getId, clientResponse -> clientResponse));
            }

            for (Account account: accounts) {
                ClientResponse clientResponse = clientResponseHashMap.get(account.getClientId());
                AccountResponse accountResponse = AccountResponse.mapEntityToResponse(account);
                accountResponse.setClient(clientResponse.getName());
                accountResponses.add(accountResponse);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return accountResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> findAccountsByClientId(Long clientId) throws Exception {
        try {
            return repository.findAccountsByClientId(clientId);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse findById(Long id) throws Exception {
        try {
            Map<Long, ClientResponse> clientResponseHashMap = new HashMap<>();
            List<ClientResponse> clientResponses = clientRestClient.fetchAll(null, null);
            if (!CollectionUtils.isEmpty(clientResponses)) {
                clientResponseHashMap = clientResponses.stream().collect(Collectors.toMap(ClientResponse::getId, clientResponse -> clientResponse));
            }
            Optional<Account>optionalAccount = repository.findById(id);
            Account account =optionalAccount.orElse(null);
            if (account != null) {
                ClientResponse clientResponse = clientResponseHashMap.get(account.getClientId());
                AccountResponse accountResponse = AccountResponse.mapEntityToResponse(account);
                accountResponse.setClient(clientResponse.getName());
                return accountResponse;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public UpdateBaseResponse update(Long id, UpdateAccountRequest updateAccountRequest) throws Exception {
        try {
            if (updateAccountRequest == null) throw new Exception("Account update request body has not been given.");
            Optional<Account> optionalAccount = repository.findById(id);
            if (optionalAccount.isPresent()) {
                Account accountDb = optionalAccount.get();
                Account accountToBeUpdated = UpdateAccountRequest.mapToEntity(updateAccountRequest);
                accountToBeUpdated.setId(accountDb.getId());
                repository.save(accountToBeUpdated);
                UpdateBaseResponse updateBaseResponse = new UpdateBaseResponse();
                updateBaseResponse.setUpdated(true);
                return updateBaseResponse;
            } else throw new Exception("Account not found with the id given.");
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void update(Long id, Account account) throws Exception {
        if (account == null) throw new Exception("Account update request body has not been given.");
        Optional<Account> optionalAccount = repository.findById(id);
        if (optionalAccount.isPresent()) {
            repository.save(account);
        } else throw new Exception("Account not found with the id given.");
    }

    @Override
    @Transactional
    public DeleteBaseResponse safeDelete(Long id) throws Exception {
        DeleteBaseResponse deleteBaseResponse = new DeleteBaseResponse();
        try {
            Optional<Account> optionalAccount = repository.findById(id);
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                account.setStatus(false);
                repository.save(account);
                deleteBaseResponse.setDeleted(true);
            }
            return deleteBaseResponse;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public DeleteBaseResponse hardDelete(Long id) throws Exception {
        DeleteBaseResponse deleteBaseResponse = new DeleteBaseResponse();
        try {
            Optional<Account> optionalAccount = repository.findById(id);
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                repository.delete(account);
                deleteBaseResponse.setDeleted(true);
            }
            return deleteBaseResponse;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
