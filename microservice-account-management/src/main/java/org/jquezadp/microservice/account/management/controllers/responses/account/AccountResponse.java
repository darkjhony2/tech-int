package org.jquezadp.microservice.account.management.controllers.responses.account;

import lombok.Data;
import org.jquezadp.microservice.account.management.entities.Account;

import java.math.BigDecimal;

@Data
public class AccountResponse {
    private Long id;
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private boolean status;
    private String client;
    private Long clientId;

    public static AccountResponse mapEntityToResponse(Account account) {
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setId(account.getId());
        accountResponse.setAccountNumber(account.getAccountNumber());
        accountResponse.setAccountType(account.getAccountType());
        accountResponse.setInitialBalance(account.getInitialBalance());
        accountResponse.setStatus(account.isStatus());
        accountResponse.setClientId(account.getClientId());
        return accountResponse;
    }

    public static Account mapResponseToEntity(AccountResponse accountResponse) {
        Account account = new Account();
        account.setId(accountResponse.getId());
        account.setAccountNumber(accountResponse.getAccountNumber());
        account.setAccountType(accountResponse.getAccountType());
        account.setInitialBalance(accountResponse.getInitialBalance());
        account.setStatus(accountResponse.isStatus());
        account.setClientId(accountResponse.getClientId());
        return account;
    }
}
