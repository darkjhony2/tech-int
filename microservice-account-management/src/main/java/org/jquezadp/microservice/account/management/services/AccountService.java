package org.jquezadp.microservice.account.management.services;

import org.apache.coyote.BadRequestException;
import org.jquezadp.microservice.account.management.controllers.requests.account.CreateAccountRequest;
import org.jquezadp.microservice.account.management.controllers.requests.account.UpdateAccountRequest;
import org.jquezadp.microservice.account.management.controllers.responses.account.AccountResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.CreateBaseResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.DeleteBaseResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.UpdateBaseResponse;
import org.jquezadp.microservice.account.management.entities.Account;

import java.util.List;

public interface AccountService {
    CreateBaseResponse create(CreateAccountRequest createAccountRequest) throws Exception;
    List<AccountResponse> findAll(Integer page, Integer pageSize) throws Exception;
    List<Account> findAccountsByClientId(Long clientId) throws Exception;
    AccountResponse findById(Long id) throws Exception;
    UpdateBaseResponse update(Long id, UpdateAccountRequest updateAccountRequest) throws Exception;
    void update(Long id, Account account) throws Exception;
    DeleteBaseResponse safeDelete(Long id) throws Exception;
    DeleteBaseResponse hardDelete(Long id) throws Exception;
}
