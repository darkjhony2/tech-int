package org.jquezadp.microservice.account.management.clients;

import org.jquezadp.microservice.account.management.controllers.requests.client.CreateClientRequest;
import org.jquezadp.microservice.account.management.controllers.requests.client.UpdateClientRequest;
import org.jquezadp.microservice.account.management.controllers.responses.client.ClientResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.CreateBaseResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.DeleteBaseResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.UpdateBaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "client-management-service")
public interface ClientRestClient {
    @GetMapping("/clients")
    List<ClientResponse> fetchAll(@RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize);

    @GetMapping("/clients/{id}")
    ClientResponse findById(@PathVariable Long id) throws Exception;

    @PostMapping("/clients")
    CreateBaseResponse create(@RequestBody CreateClientRequest createClientRequest) throws Exception;

    @PutMapping("/clients/{id}")
    UpdateBaseResponse edit(@PathVariable Long id, @RequestBody UpdateClientRequest updateClientRequest) throws Exception;

    @DeleteMapping("/clients/{id}")
    DeleteBaseResponse safeDelete(@PathVariable Long id) throws Exception;

    @DeleteMapping("/clients/{id}/hardDelete")
    DeleteBaseResponse hardDelete(@PathVariable Long id) throws Exception;
}
