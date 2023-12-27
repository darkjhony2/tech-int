package org.jquezadp.microservice.account.management.services.impl;

import org.jquezadp.microservice.account.management.clients.ClientRestClient;
import org.jquezadp.microservice.account.management.controllers.responses.client.ClientResponse;
import org.jquezadp.microservice.account.management.controllers.responses.report.AccountReportResponse;
import org.jquezadp.microservice.account.management.controllers.responses.report.ReportResponse;
import org.jquezadp.microservice.account.management.entities.Account;
import org.jquezadp.microservice.account.management.entities.Movement;
import org.jquezadp.microservice.account.management.services.AccountService;
import org.jquezadp.microservice.account.management.services.MovementService;
import org.jquezadp.microservice.account.management.services.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportServiceImpl.class);
    private final ClientRestClient clientRestClient;
    private final AccountService accountService;
    private final MovementService movementService;

    public ReportServiceImpl(AccountService accountService, MovementService movementService, ClientRestClient clientRestClient) {
        this.accountService = accountService;
        this.movementService = movementService;
        this.clientRestClient = clientRestClient;
    }

    @Override
    @Transactional(readOnly = true)
    public ReportResponse generateReport(String startDate, String endDate, Long clientId) throws Exception {
        try {
            ReportResponse reportResponse = new ReportResponse();
            List<Long> accountIds = new ArrayList<>();
            List<AccountReportResponse> accountReportResponses = new ArrayList<>();
            List<Account> accounts = accountService.findAccountsByClientId(clientId);
            accounts.forEach(clientAccount -> accountIds.add(clientAccount.getId()));
            List<Movement> movements = movementService.findByAccountIdInAndDateBetween(accountIds, startDate, endDate);
            ClientResponse clientResponse = clientRestClient.findById(clientId);
            accounts.forEach(account -> {
                AccountReportResponse accountReportResponse = new AccountReportResponse();
                accountReportResponse.setAccount(account);
                List<Movement> accountMovements = movements.stream()
                        .filter(movement -> Objects.equals(movement.getAccountId(), account.getId()))
                        .collect(Collectors.toList());
                accountReportResponse.setAccountMovements(accountMovements);
                accountReportResponses.add(accountReportResponse);
            });
            reportResponse.setClientResponse(clientResponse);
            reportResponse.setAccountReportResponses(accountReportResponses);
            return reportResponse;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
