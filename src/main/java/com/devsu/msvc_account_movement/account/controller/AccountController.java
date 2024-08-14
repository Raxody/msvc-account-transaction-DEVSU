package com.devsu.msvc_account_movement.account.controller;

import com.devsu.msvc_account_movement.account.dto.AccountDTO;
import com.devsu.msvc_account_movement.account.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {
        AccountDTO createdAccount = accountService.createAccount(accountDTO);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long accountNumber) {
        AccountDTO accountDTO = accountService.getAccountById(accountNumber);
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PatchMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long accountNumber, @RequestBody AccountDTO accountDTO) {
        AccountDTO updatedAccount = accountService.updateAccount(accountNumber, accountDTO);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountNumber) {
        accountService.deleteAccount(accountNumber);
        return new ResponseEntity<>("Account deleted",HttpStatus.OK);
    }
}