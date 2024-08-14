package com.devsu.msvc_account_movement.account.service;

import com.devsu.msvc_account_movement.account.dto.AccountDTO;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);

    AccountDTO getAccountById(Long accountNumber);

    List<AccountDTO> getAllAccounts();

    AccountDTO updateAccount(Long accountNumber, AccountDTO accountDTO);

    void deleteAccount(Long accountNumber);
}
