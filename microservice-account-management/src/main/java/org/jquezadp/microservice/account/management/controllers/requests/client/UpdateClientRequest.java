package org.jquezadp.microservice.account.management.controllers.requests.client;

import lombok.Data;
import lombok.NonNull;
import org.jquezadp.microservice.account.management.entities.Client;

@Data
public class UpdateClientRequest {
    @NonNull
    private String name;
    @NonNull
    private String gender;
    @NonNull
    private Integer age;
    @NonNull
    private String identification;
    @NonNull
    private String address;
    @NonNull
    private String telephone;
    @NonNull
    private String clientId;
    @NonNull
    private String password;
    @NonNull
    private boolean status;

    public static Client mapToEntity(UpdateClientRequest updateClientRequest) {
        Client client = new Client();
        client.setName(updateClientRequest.getName());
        client.setGender(updateClientRequest.getGender());
        client.setAge(updateClientRequest.getAge());
        client.setIdentification(updateClientRequest.getIdentification());
        client.setAddress(updateClientRequest.getAddress());
        client.setTelephone(updateClientRequest.getTelephone());
        client.setClientId(updateClientRequest.getClientId());
        client.setPassword(updateClientRequest.getPassword());
        client.setStatus(updateClientRequest.isStatus());
        return client;
    }
}
