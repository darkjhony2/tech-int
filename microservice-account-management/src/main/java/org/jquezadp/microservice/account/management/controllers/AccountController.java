package org.jquezadp.microservice.account.management.controllers;

import org.jquezadp.microservice.account.management.controllers.requests.account.CreateAccountRequest;
import org.jquezadp.microservice.account.management.controllers.requests.account.UpdateAccountRequest;
import org.jquezadp.microservice.account.management.controllers.responses.account.AccountResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.CreateBaseResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.DeleteBaseResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.UpdateBaseResponse;
import org.jquezadp.microservice.account.management.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    public List<AccountResponse> fetchAll(@RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize) throws Exception {
        return service.findAll(page, pageSize);
    }

    @GetMapping("/{id}")
    public AccountResponse findById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @PostMapping
    public CreateBaseResponse create(@RequestBody CreateAccountRequest createAccountRequest) throws Exception {
        return service.create(createAccountRequest);
    }

    @PutMapping("/{id}")
    public UpdateBaseResponse edit(@PathVariable Long id, @RequestBody UpdateAccountRequest updateAccountRequest) throws Exception {
        return service.update(id, updateAccountRequest);
    }

    @DeleteMapping("/{id}")
    public DeleteBaseResponse safeDelete(@PathVariable Long id) throws Exception {
        return service.safeDelete(id);
    }

    @DeleteMapping("/{id}/hardDelete")
    public DeleteBaseResponse hardDelete(@PathVariable Long id) throws Exception {
        return service.hardDelete(id);
    }
}
