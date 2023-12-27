package org.jquezadp.microservice.account.management.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts", uniqueConstraints = {
        @UniqueConstraint(name = "accountNumber", columnNames = {"accountNumber"})
})
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private boolean status;
    private Long clientId;
}
