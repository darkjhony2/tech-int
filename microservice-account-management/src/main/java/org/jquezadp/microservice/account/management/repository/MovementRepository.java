package org.jquezadp.microservice.account.management.repository;

import org.jquezadp.microservice.account.management.entities.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MovementRepository extends JpaRepository<Movement, Long> {
    List<Movement> findByAccountIdInAndDateBetween(List<Long> id, Date startDate, Date endDate);
}
