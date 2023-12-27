package org.jquezadp.microservice.account.management.controllers.requests.account;

import lombok.Data;
import lombok.NonNull;
import org.jquezadp.microservice.account.management.entities.Account;

import java.math.BigDecimal;

@Data
public class UpdateAccountRequest {
    @NonNull
    private String accountNumber;
    @NonNull
    private String accountType;
    @NonNull
    private BigDecimal initialBalance;
    private boolean status = true;
    @NonNull
    private Long clientId;

    public static Account mapToEntity(UpdateAccountRequest updateAccountRequest) {
        Account account = new Account();
        account.setAccountNumber(updateAccountRequest.getAccountNumber());
        account.setAccountType(updateAccountRequest.getAccountType());
        account.setInitialBalance(updateAccountRequest.getInitialBalance());
        account.setStatus(updateAccountRequest.isStatus());
        account.setClientId(updateAccountRequest.getClientId());
        return account;
    }
}
