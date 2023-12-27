package org.jquezadp.microservice.account.management.exceptions;

public class InactiveEntityException extends Exception {
    public InactiveEntityException(String message) {
        super(message);
    }
}
