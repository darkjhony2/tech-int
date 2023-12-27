package org.jquezadp.microservice.client.management.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.jquezadp.microservice.client.management.entities.Client;

@Data
@AllArgsConstructor
public class CreateClientRequest {
    @NonNull
    private String name;
    private String gender;
    private Integer age;
    @NonNull
    private String identification;
    private String address;
    private String telephone;
    @NonNull
    private String clientId;
    @NonNull
    private String password;
    private boolean status = true;

    public static Client mapToEntity(CreateClientRequest createClientRequest) {
        Client client = new Client();
        client.setName(createClientRequest.getName());
        client.setGender(createClientRequest.getGender());
        client.setAge(createClientRequest.getAge());
        client.setIdentification(createClientRequest.getIdentification());
        client.setAddress(createClientRequest.getAddress());
        client.setTelephone(createClientRequest.getTelephone());
        client.setClientId(createClientRequest.getClientId());
        client.setPassword(createClientRequest.getPassword());
        client.setStatus(createClientRequest.isStatus());
        return client;
    }
}
