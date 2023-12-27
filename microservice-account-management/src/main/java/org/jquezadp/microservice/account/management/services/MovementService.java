package org.jquezadp.microservice.account.management.services;

import org.jquezadp.microservice.account.management.controllers.requests.movement.CreateMovementRequest;
import org.jquezadp.microservice.account.management.controllers.requests.movement.UpdateMovementRequest;
import org.jquezadp.microservice.account.management.controllers.responses.base.CreateBaseResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.DeleteBaseResponse;
import org.jquezadp.microservice.account.management.controllers.responses.base.UpdateBaseResponse;
import org.jquezadp.microservice.account.management.controllers.responses.movement.MovementResponse;
import org.jquezadp.microservice.account.management.entities.Movement;

import java.util.Date;
import java.util.List;

public interface MovementService {
    CreateBaseResponse create(CreateMovementRequest createMovementRequest) throws Exception;
    List<MovementResponse> findAll(Integer page, Integer pageSize) throws Exception;
    List<Movement> findByAccountIdInAndDateBetween(List<Long> ids, String startDate, String endDate) throws Exception;
    MovementResponse findById(Long id) throws Exception;
    UpdateBaseResponse update(Long id, UpdateMovementRequest updateMovementRequest) throws Exception;
    DeleteBaseResponse safeDelete(Long id) throws Exception;
    DeleteBaseResponse hardDelete(Long id) throws Exception;
}
