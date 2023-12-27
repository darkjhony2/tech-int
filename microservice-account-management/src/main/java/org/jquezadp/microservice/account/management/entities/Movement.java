package org.jquezadp.microservice.account.management.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "movements")
@Data
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String movementType;
    private BigDecimal initialBalance;
    private BigDecimal value;
    private BigDecimal balance;
    private Long accountId;
    private boolean status = true;
}
