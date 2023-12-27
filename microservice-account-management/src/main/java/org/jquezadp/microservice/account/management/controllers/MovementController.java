package org.jquezadp.microservice.account.management.controllers;

import org.jquezadp.microservice.account.management.controllers.requests.account.CreateAccountRequest;
import org.jquezadp.microservice.account.management.controllers.requests.account.UpdateAccountRequest;
import org.jquezadp.microservice.account.management.controllers.requests.movement.CreateMovementRequest;
import org.jquezadp.microservice.account.management.controllers.requests.movement.UpdateMovementRequest;
import org.jquezadp.microservice.account.management.controllers.responses.account.AccountResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.CreateBaseResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.DeleteBaseResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.UpdateBaseResponse;
import org.jquezadp.microservice.account.management.controllers.responses.movement.MovementResponse;
import org.jquezadp.microservice.account.management.services.MovementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movements")
public class MovementController {

    private final MovementService service;

    public MovementController(MovementService service) {
        this.service = service;
    }

    @GetMapping
    public List<MovementResponse> fetchAll(@RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize) throws Exception {
        return service.findAll(page, pageSize);
    }

    @GetMapping("/{id}")
    public MovementResponse findById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @PostMapping
    public CreateBaseResponse create(@RequestBody CreateMovementRequest createMovementRequest) throws Exception {
        return service.create(createMovementRequest);
    }

    @PutMapping("/{id}")
    public UpdateBaseResponse edit(@PathVariable Long id, @RequestBody UpdateMovementRequest updateMovementRequest) throws Exception {
        return service.update(id, updateMovementRequest);
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
