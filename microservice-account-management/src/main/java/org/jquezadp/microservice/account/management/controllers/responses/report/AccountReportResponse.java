package org.jquezadp.microservice.account.management.controllers.responses.report;

import lombok.Data;
import org.jquezadp.microservice.account.management.entities.Account;
import org.jquezadp.microservice.account.management.entities.Movement;

import java.util.ArrayList;
import java.util.List;

@Data
public class AccountReportResponse {
    private Account account;
    private List<Movement> accountMovements = new ArrayList<>();
}
