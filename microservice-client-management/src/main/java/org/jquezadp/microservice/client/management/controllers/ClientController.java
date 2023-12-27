package org.jquezadp.microservice.client.management.controllers;

import org.jquezadp.microservice.client.management.controllers.requests.CreateClientRequest;
import org.jquezadp.microservice.client.management.controllers.requests.UpdateClientRequest;
import org.jquezadp.microservice.client.management.controllers.responses.ClientResponse;
import org.jquezadp.microservice.client.management.controllers.responses.CreateClientResponse;
import org.jquezadp.microservice.client.management.controllers.responses.DeleteClientResponse;
import org.jquezadp.microservice.client.management.controllers.responses.UpdateClientResponse;
import org.jquezadp.microservice.client.management.services.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public List<ClientResponse> fetchAll(@RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize) throws Exception {
        return service.findAll(page, pageSize);
    }

    @GetMapping("/{id}")
    public ClientResponse findById(@PathVariable Long id) throws Exception {
        if (id.equals(10L)) {
            TimeUnit.SECONDS.sleep(5L);
        }
        return service.findById(id);
    }

    @PostMapping
    public CreateClientResponse create(@RequestBody CreateClientRequest createClientRequest) throws Exception {
        return service.create(createClientRequest);
    }

    @PutMapping("/{id}")
    public UpdateClientResponse edit(@PathVariable Long id, @RequestBody UpdateClientRequest updateClientRequest) throws Exception {
        return service.update(id, updateClientRequest);
    }

    @DeleteMapping("/{id}")
    public DeleteClientResponse safeDelete(@PathVariable Long id) throws Exception {
        return service.safeDelete(id);
    }

    @DeleteMapping("/{id}/hardDelete")
    public DeleteClientResponse hardDelete(@PathVariable Long id) throws Exception {
        return service.hardDelete(id);
    }
}
