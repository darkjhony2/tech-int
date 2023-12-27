package org.jquezadp.microservice.account.management.controllers.requests.account;

import lombok.Data;
import lombok.NonNull;
import org.jquezadp.microservice.account.management.entities.Account;

import java.math.BigDecimal;

@Data
public class CreateAccountRequest {
    @NonNull
    private String accountNumber;
    @NonNull
    private String accountType;
    @NonNull
    private BigDecimal initialBalance;
    private boolean status = true;
    @NonNull
    private Long clientId;

    public static Account mapToEntity(CreateAccountRequest createAccountRequest) {
        Account account = new Account();
        account.setAccountNumber(createAccountRequest.getAccountNumber());
        account.setAccountType(createAccountRequest.getAccountType());
        account.setInitialBalance(createAccountRequest.getInitialBalance());
        account.setStatus(createAccountRequest.isStatus());
        account.setClientId(createAccountRequest.getClientId());
        return account;
    }
}
