package com.devsu.msvc_account_movement.account.mapper;

import com.devsu.msvc_account_movement.account.dto.AccountDTO;
import com.devsu.msvc_account_movement.account.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper()
public interface AccountMapper {

    @Mapping(target = "accountNumber", ignore = true)
    Account toEntity(AccountDTO accountDTO);

    AccountDTO toDTO(Account account);

    void updateAccountFromDto(AccountDTO accountDTO, @MappingTarget Account account);
}
