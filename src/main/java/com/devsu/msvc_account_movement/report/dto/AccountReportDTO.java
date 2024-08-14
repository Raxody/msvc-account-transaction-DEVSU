package com.devsu.msvc_account_movement.report.dto;

import com.devsu.msvc_account_movement.movement.dto.MovementDTO;

import java.util.List;

public class AccountReportDTO {
    private Long accountNumber;
    private String accountType;
    private Double balance;
    private List<MovementDTO> movements;

    public AccountReportDTO(Long accountNumber, String accountType, Double balance, List<MovementDTO> movements) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.movements = movements;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<MovementDTO> getMovements() {
        return movements;
    }

    public void setMovements(List<MovementDTO> movements) {
        this.movements = movements;
    }
}