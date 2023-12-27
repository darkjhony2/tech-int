package org.jquezadp.microservice.client.management.controllers.responses;


import lombok.Data;
import org.jquezadp.microservice.client.management.entities.Client;

@Data
public class ClientResponse {
    private Long id;
    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String address;
    private String telephone;
    private String clientId;
    private String password;
    private boolean status;

    public static ClientResponse mapEntityToResponse(Client client) {
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setId(client.getId());
        clientResponse.setName(client.getName());
        clientResponse.setGender(client.getGender());
        clientResponse.setAge(client.getAge());
        clientResponse.setIdentification(client.getIdentification());
        clientResponse.setAddress(client.getAddress());
        clientResponse.setTelephone(client.getTelephone());
        clientResponse.setClientId(client.getClientId());
        clientResponse.setPassword(client.getPassword());
        clientResponse.setStatus(client.isStatus());
        return clientResponse;
    }
}
