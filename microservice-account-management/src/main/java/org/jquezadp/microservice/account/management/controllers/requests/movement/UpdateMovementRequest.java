package org.jquezadp.microservice.account.management.controllers.requests.movement;

import lombok.Data;
import lombok.NonNull;
import org.jquezadp.microservice.account.management.entities.Movement;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UpdateMovementRequest {
    @NonNull
    private Date date;
    @NonNull
    private String movementType;
    @NonNull
    private BigDecimal value;
    @NonNull
    private Long accountId;
    private boolean status = true;

    public static Movement mapToEntity(UpdateMovementRequest updateMovementRequest) {
        Movement movement = new Movement();
        movement.setDate(updateMovementRequest.getDate());
        movement.setMovementType(updateMovementRequest.getMovementType());
        movement.setValue(updateMovementRequest.getValue());
        movement.setAccountId(updateMovementRequest.getAccountId());
        movement.setStatus(updateMovementRequest.isStatus());
        return movement;
    }
}
