package com.devsu.msvc_account_movement.account.repository;

import com.devsu.msvc_account_movement.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByCustomerId(String customerId);

}
