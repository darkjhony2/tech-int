package org.jquezadp.microservice.account.management.exceptions;

public class BalanceNotAvailableException extends Exception{
    public BalanceNotAvailableException(String message) {
        super(message);
    }
}
