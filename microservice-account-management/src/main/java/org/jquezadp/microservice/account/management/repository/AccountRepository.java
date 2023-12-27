package org.jquezadp.microservice.account.management.repository;

import org.jquezadp.microservice.account.management.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountsByClientId(Long clientId);
}
