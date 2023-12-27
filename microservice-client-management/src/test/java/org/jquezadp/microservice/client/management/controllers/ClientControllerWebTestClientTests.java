package org.jquezadp.microservice.client.management.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jquezadp.microservice.client.management.controllers.requests.CreateClientRequest;
import org.jquezadp.microservice.client.management.controllers.requests.UpdateClientRequest;
import org.jquezadp.microservice.client.management.controllers.responses.ClientResponse;
import org.jquezadp.microservice.client.management.entities.Client;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ClientControllerWebTestClientTests {

    private ObjectMapper objectMapper;

    @Autowired
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testCreate() {
        CreateClientRequest createClientRequest = new CreateClientRequest("Test Name", "Male", 26,
                "79856482", "Some Address", "987648645", "C00001", "asd123",
                true);

        //when
        client.post().uri("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createClientRequest)
                .exchange()
        //then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.created").isEqualTo(true);
    }

    @Test
    @Order(2)
    void testFindById() throws JsonProcessingException {

        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setId(1L);
        clientResponse.setName("Test Name");
        clientResponse.setClientId("C00001");
        clientResponse.setAge(26);
        clientResponse.setStatus(true);
        clientResponse.setIdentification("79856482");
        clientResponse.setAddress("Some Address");
        clientResponse.setPassword("asd123");
        clientResponse.setGender("Male");
        clientResponse.setTelephone("987648645");

        client.get().uri("/clients/1").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .json(objectMapper.writeValueAsString(clientResponse));
    }

    @Test
    @Order(3)
    void testUpdate() {
        UpdateClientRequest updateClientRequest = new UpdateClientRequest("Test Name", "Male", 27,
                "79856482", "Some Address", "987648645", "C00001",
                "asd123", true);

        //when
        client.put().uri("/clients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateClientRequest)
                .exchange()
                //then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.updated").isEqualTo(true);

        //when
        client.get().uri("/clients/1").exchange()
                //then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.age").isEqualTo(27);
    }

    @Test
    @Order(4)
    void testSoftDelete() {
        //when
        client.delete().uri("/clients/1").exchange()
                //then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.deleted").isEqualTo(true);

        //when
        client.get().uri("/clients/1").exchange()
                //then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.status").isEqualTo(false);
    }

    @Test
    @Order(5)
    void testHardDelete() {
        //when
        client.delete().uri("/clients/1/hardDelete").exchange()
                //then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.deleted").isEqualTo(true);
    }
}
