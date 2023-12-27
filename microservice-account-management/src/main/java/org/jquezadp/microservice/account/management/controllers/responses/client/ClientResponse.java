package org.jquezadp.microservice.account.management.controllers.responses.client;


import lombok.Data;

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
}
