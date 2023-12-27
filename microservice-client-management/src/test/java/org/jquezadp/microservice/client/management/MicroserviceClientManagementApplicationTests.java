package org.jquezadp.microservice.client.management;

import org.jquezadp.microservice.client.management.controllers.requests.CreateClientRequest;
import org.jquezadp.microservice.client.management.controllers.responses.ClientResponse;
import org.jquezadp.microservice.client.management.controllers.responses.CreateClientResponse;
import org.jquezadp.microservice.client.management.entities.Client;
import org.jquezadp.microservice.client.management.respositories.ClientRepository;
import org.jquezadp.microservice.client.management.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MicroserviceClientManagementApplicationTests {

    @MockBean
    ClientRepository clientRepository;

    @Autowired
    ClientService clientService;
    private static Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L);
        client.setName("Test name");
        client.setClientId("C00001");
        client.setAge(26);
        client.setStatus(true);
        client.setIdentification("74968564");
        client.setAddress("Some Address");
        client.setPassword("asd123");
        client.setGender("Male");
        client.setTelephone("964321987");
    }

    @Test
    void testClientService() throws Exception {
        when(clientRepository.save(any())).thenReturn(client);
        when(clientRepository.findById(1L)).thenReturn(Optional.ofNullable(client));

        CreateClientRequest createClientRequest = new CreateClientRequest("Test name",
                "Male", 26, "74968564", "Some Address", "964321987",
                "C00001", "asd123", true);

        CreateClientResponse createClientResponse = clientService.create(createClientRequest);
        assertTrue(createClientResponse.isCreated());

        ClientResponse clientResponse = clientService.findById(1L);
        assertEquals(client.getId(), clientResponse.getId());
        assertEquals(client.getName(), clientResponse.getName());
        assertEquals(client.getClientId(), clientResponse.getClientId());
        assertEquals(client.getAge(), clientResponse.getAge());
        assertEquals(client.isStatus(), clientResponse.isStatus());
        assertEquals(client.getIdentification(), clientResponse.getIdentification());
        assertEquals(client.getAddress(), clientResponse.getAddress());
        assertEquals(client.getPassword(), clientResponse.getPassword());
        assertEquals(client.getGender(), clientResponse.getGender());
        assertEquals(client.getTelephone(), clientResponse.getTelephone());
    }

}
