package org.jquezadp.microservice.account.management.services.impl;

import org.apache.coyote.BadRequestException;
import org.jquezadp.microservice.account.management.controllers.requests.movement.CreateMovementRequest;
import org.jquezadp.microservice.account.management.controllers.requests.movement.UpdateMovementRequest;
import org.jquezadp.microservice.account.management.controllers.responses.account.AccountResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.CreateBaseResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.DeleteBaseResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.UpdateBaseResponse;
import org.jquezadp.microservice.account.management.controllers.responses.movement.MovementResponse;
import org.jquezadp.microservice.account.management.entities.Account;
import org.jquezadp.microservice.account.management.entities.Movement;
import org.jquezadp.microservice.account.management.exceptions.BalanceNotAvailableException;
import org.jquezadp.microservice.account.management.exceptions.InactiveEntityException;
import org.jquezadp.microservice.account.management.repository.MovementRepository;
import org.jquezadp.microservice.account.management.services.AccountService;
import org.jquezadp.microservice.account.management.services.MovementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovementServiceImpl implements MovementService {

    private static final Logger LOG = LoggerFactory.getLogger(MovementServiceImpl.class);
    private final MovementRepository repository;
    private final AccountService accountService;

    public MovementServiceImpl(AccountService accountService, MovementRepository repository) {
        this.repository = repository;
        this.accountService = accountService;
    }

    @Override
    @Transactional
    public CreateBaseResponse create(CreateMovementRequest createMovementRequest) throws Exception {
        CreateBaseResponse createBaseResponse = new CreateBaseResponse();
        try {
            if (createMovementRequest == null) throw new BadRequestException("Body has not been received");
            AccountResponse accountResponse = accountService.findById(createMovementRequest.getAccountId());
            if (accountResponse == null) throw new NoSuchElementException("Account not found with the given id.");
            if (!accountResponse.isStatus()) throw new InactiveEntityException("Account is inactive");

            //Calculating balance
            BigDecimal initialBalance = accountResponse.getInitialBalance();
            BigDecimal balance = initialBalance.add(createMovementRequest.getValue());
            if(balance.doubleValue() < 0) {
                throw new BalanceNotAvailableException("Balance not available");
            }

            //Mapping and persisting the movement with the balance calculated.
            Movement movement = CreateMovementRequest.mapToEntity(createMovementRequest);
            movement.setInitialBalance(initialBalance);
            movement.setBalance(balance);
            repository.save(movement);

            //Persisting the balance in account
            Account account = AccountResponse.mapResponseToEntity(accountResponse);
            account.setInitialBalance(balance);
            accountService.update(account.getId(), account);

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
    public List<MovementResponse> findAll(Integer page, Integer pageSize) throws Exception {
        List<MovementResponse> movementResponses = new ArrayList<>();
        try {
            Map<Long, AccountResponse> accountResponseHashMap = new HashMap<>();
            Pageable pageable = PageRequest.of(page, pageSize);
            Page<Movement> pageResult =  repository.findAll(pageable);
            List<AccountResponse> accountResponses = accountService.findAll(null, null);
            if (!CollectionUtils.isEmpty(accountResponses)) {
                accountResponseHashMap = accountResponses.stream().collect(Collectors.toMap(AccountResponse::getId, accountResponse -> accountResponse));
            }
            List<Movement> movements = pageResult.getContent();
            for (Movement movement: movements) {
                AccountResponse account = accountResponseHashMap.get(movement.getAccountId());
                MovementResponse movementResponse = MovementResponse.mapEntityToResponse(movement);
                movementResponse.setClient(account.getClient());
                movementResponses.add(movementResponse);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return movementResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movement> findByAccountIdInAndDateBetween(List<Long> ids, String startDate, String endDate) throws Exception {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedStartDate = dateFormat.parse(startDate);
            Date parsedEndDate = dateFormat.parse(endDate);
            return repository.findByAccountIdInAndDateBetween(ids, parsedStartDate, parsedEndDate);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MovementResponse findById(Long id) throws Exception {
        try {
            Map<Long, AccountResponse> accountResponseHashMap = new HashMap<>();
            List<AccountResponse> accountResponses = accountService.findAll(null, null);
            if (!CollectionUtils.isEmpty(accountResponses)) {
                accountResponseHashMap = accountResponses.stream().collect(Collectors.toMap(AccountResponse::getId, accountResponse -> accountResponse));
            }
            Optional<Movement> movementOptional =repository.findById(id);
            Movement movement = movementOptional.orElse(null);
            if (movement != null) {
                AccountResponse accountResponse = accountResponseHashMap.get(movement.getAccountId());
                MovementResponse movementResponse = MovementResponse.mapEntityToResponse(movement);
                movementResponse.setClient(accountResponse.getClient());
                return movementResponse;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public UpdateBaseResponse update(Long id, UpdateMovementRequest updateMovementRequest) throws Exception {
        try {
            if (updateMovementRequest == null) throw new Exception("Movement update request body has not been given.");
            Optional<Movement> optionalMovement = repository.findById(id);
            if (optionalMovement.isPresent()) {
                Movement movementDb = optionalMovement.get();
                Movement movementToBeUpdated = UpdateMovementRequest.mapToEntity(updateMovementRequest);
                movementToBeUpdated.setId(movementDb.getId());
                movementToBeUpdated.setAccountId(movementDb.getAccountId());
                repository.save(movementToBeUpdated);
                UpdateBaseResponse updateBaseResponse = new UpdateBaseResponse();
                updateBaseResponse.setUpdated(true);
                return updateBaseResponse;
            } else {
                throw new Exception("Account not found with the id given.");
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public DeleteBaseResponse safeDelete(Long id) throws Exception {
        DeleteBaseResponse deleteBaseResponse = new DeleteBaseResponse();
        try {
            Optional<Movement> optionalMovement = repository.findById(id);
            if (optionalMovement.isPresent()) {
                Movement movement = optionalMovement.get();
                movement.setStatus(false);
                repository.save(movement);
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
            Optional<Movement> optionalMovement = repository.findById(id);
            if (optionalMovement.isPresent()) {
                Movement movement = optionalMovement.get();
                repository.delete(movement);
                deleteBaseResponse.setDeleted(true);
            }
            return deleteBaseResponse;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
