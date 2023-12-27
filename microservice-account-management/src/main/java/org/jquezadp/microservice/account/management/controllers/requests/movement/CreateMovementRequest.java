package org.jquezadp.microservice.account.management.controllers.requests.movement;

import lombok.Data;
import lombok.NonNull;
import org.jquezadp.microservice.account.management.entities.Movement;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CreateMovementRequest {
    @NonNull
    private Date date;
    @NonNull
    private String movementType;
    @NonNull
    private BigDecimal value;
    @NonNull
    private Long accountId;
    private boolean status = true;

    public static Movement mapToEntity(CreateMovementRequest createMovementRequest) {
        Movement movement = new Movement();
        movement.setDate(createMovementRequest.getDate());
        movement.setMovementType(createMovementRequest.getMovementType());
        movement.setValue(createMovementRequest.getValue());
        movement.setAccountId(createMovementRequest.getAccountId());
        movement.setStatus(createMovementRequest.isStatus());
        return movement;
    }
}
