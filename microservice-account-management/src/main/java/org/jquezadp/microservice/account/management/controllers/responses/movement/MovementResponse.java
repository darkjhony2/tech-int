package org.jquezadp.microservice.account.management.controllers.responses.movement;

import lombok.Data;
import org.jquezadp.microservice.account.management.entities.Movement;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MovementResponse {
    private Long id;
    private Date date;
    private String movementType;
    private BigDecimal initialBalance;
    private BigDecimal value;
    private BigDecimal balance;
    private String client;

    public static MovementResponse mapEntityToResponse(Movement movement) {
        MovementResponse movementResponse = new MovementResponse();
        movementResponse.setId(movement.getId());
        movementResponse.setDate(movement.getDate());
        movementResponse.setMovementType(movement.getMovementType());
        movementResponse.setValue(movement.getValue());
        movementResponse.setBalance(movement.getBalance());
        movementResponse.setInitialBalance(movement.getInitialBalance());
        return movementResponse;
    }
}
