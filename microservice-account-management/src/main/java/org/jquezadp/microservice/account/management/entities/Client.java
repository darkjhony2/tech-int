package org.jquezadp.microservice.account.management.entities;

import lombok.Data;

@Data
public class Client {
    private Long id;
    private String clientId;
    private String password;
    private boolean status;
    private String name;
    private String  gender;
    private Integer age;
    private String identification;
    private String address;
    private String telephone;
}
