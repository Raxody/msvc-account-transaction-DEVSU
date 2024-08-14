package com.devsu.msvc_account_movement.report.service.impl;

import com.devsu.msvc_account_movement.account.entity.Account;
import com.devsu.msvc_account_movement.account.repository.AccountRepository;
import com.devsu.msvc_account_movement.common.util.ArgumentValidator;
import com.devsu.msvc_account_movement.movement.dto.MovementDTO;
import com.devsu.msvc_account_movement.movement.mapper.MovementMapper;
import com.devsu.msvc_account_movement.movement.repository.MovementRepository;
import com.devsu.msvc_account_movement.report.dto.AccountReportDTO;
import com.devsu.msvc_account_movement.report.dto.ReportRequestDTO;
import com.devsu.msvc_account_movement.report.dto.ReportResponseDTO;
import com.devsu.msvc_account_movement.report.service.ReportService;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;
    private final MovementMapper movementMapper = Mappers.getMapper(MovementMapper.class);

    public ReportServiceImpl(AccountRepository accountRepository, MovementRepository movementRepository) {
        this.accountRepository = accountRepository;
        this.movementRepository = movementRepository;
    }

    @Override
    public ReportResponseDTO generateReport(ReportRequestDTO request) {
        LocalDateTime startDate = ArgumentValidator.validDateFormat(request.getDateInitial(), "Fecha inicial");
        LocalDateTime endDate = ArgumentValidator.validDateFormat(request.getDateFinal(), "Fecha final");

        List<Account> accounts = accountRepository.findByCustomerId(request.getCustomerId().trim());

        List<AccountReportDTO> accountReports = accounts.stream().map(account -> {
            List<MovementDTO> movements = movementRepository.findByAccountAccountNumberAndDateBetween(
                            account.getAccountNumber(), startDate, endDate)
                    .stream()
                    .map(movementMapper::toDTO)
                    .toList();

            return new AccountReportDTO(account.getAccountNumber(), account.getAccountType(), account.getInitialBalance(), movements);
        }).toList();

        return new ReportResponseDTO(accountReports);
    }
}