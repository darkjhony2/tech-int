package org.jquezadp.microservice.client.management.entities;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public class Person {
    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String address;
    private String telephone;
}