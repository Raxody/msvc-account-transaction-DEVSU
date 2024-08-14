package com.devsu.msvc_account_movement.account.service.impl;

import com.devsu.msvc_account_movement.account.dto.AccountDTO;
import com.devsu.msvc_account_movement.account.entity.Account;
import com.devsu.msvc_account_movement.account.mapper.AccountMapper;
import com.devsu.msvc_account_movement.account.repository.AccountRepository;
import com.devsu.msvc_account_movement.account.service.AccountService;
import com.devsu.msvc_account_movement.common.exception.BusinessException;
import com.devsu.msvc_account_movement.common.exception.ErrorCodesEnum;
import com.devsu.msvc_account_movement.common.util.ArgumentValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        if (accountDTO.getStatus()==null) accountDTO.setStatus(Boolean.TRUE);
        validateAccountDTO(accountDTO);

        Account account = accountMapper.toEntity(accountDTO);
        account = accountRepository.save(account);
        return accountMapper.toDTO(account);
    }

    @Override
    public AccountDTO getAccountById(Long accountNumber) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new BusinessException(ErrorCodesEnum.THE_ACCOUNT_IS_NOT_FOUND_OR_INACTIVE, accountNumber.toString()));
        return accountMapper.toDTO(account);
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .filter(Account::getStatus)
                .map(accountMapper::toDTO)
                .toList();
    }

    @Override
    public AccountDTO updateAccount(Long accountNumber, AccountDTO accountDTO) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new BusinessException(ErrorCodesEnum.THE_ACCOUNT_IS_NOT_FOUND_OR_INACTIVE, accountNumber.toString()));
        validateAccountDTO(accountDTO);

        accountMapper.updateAccountFromDto(accountDTO, account);
        account = accountRepository.save(account);
        return accountMapper.toDTO(account);
    }

    @Override
    public void deleteAccount(Long accountNumber) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new BusinessException(ErrorCodesEnum.THE_ACCOUNT_IS_NOT_FOUND_OR_INACTIVE, accountNumber.toString()));

        account.setStatus(false);
        accountRepository.save(account);
    }

    private void validateAccountDTO(AccountDTO accountDTO) {
        ArgumentValidator.requireNotEmpty(accountDTO.getAccountType(), "Account Type");
        ArgumentValidator.validValueInList(List.of("Ahorros", "Corriente"), accountDTO.getAccountType(), "Account Type");

        ArgumentValidator.requireNotEmpty(accountDTO.getInitialBalance().toString(), "Initial Balance");
        ArgumentValidator.requireNumeric(accountDTO.getInitialBalance().toString(), "Initial Balance");
        ArgumentValidator.requirePositiveAndGreaterThanZero(accountDTO.getInitialBalance().toString(), "Initial Balance");

        ArgumentValidator.requireNotEmpty(String.valueOf(accountDTO.getStatus()), "Status");

        ArgumentValidator.requireNotEmpty(accountDTO.getCustomerId(), "Customer ID");
    }
}
