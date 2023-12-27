package org.jquezadp.microservice.account.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroserviceAccountManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceAccountManagementApplication.class, args);
    }

}
