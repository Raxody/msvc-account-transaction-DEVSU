package com.devsu.msvc_account_movement.movement.service.impl;

import com.devsu.msvc_account_movement.account.entity.Account;
import com.devsu.msvc_account_movement.account.repository.AccountRepository;
import com.devsu.msvc_account_movement.movement.dto.MovementDTO;
import com.devsu.msvc_account_movement.movement.entity.Movement;
import com.devsu.msvc_account_movement.movement.mapper.MovementMapper;
import com.devsu.msvc_account_movement.movement.repository.MovementRepository;
import com.devsu.msvc_account_movement.movement.service.MovementService;
import com.devsu.msvc_account_movement.common.exception.BusinessException;
import com.devsu.msvc_account_movement.common.exception.ErrorCodesEnum;
import com.devsu.msvc_account_movement.common.util.ArgumentValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MovementServiceImpl implements MovementService {

    private static final String MOVEMENT_TYPE = "Movement Type";
    private static final String VALUE = "Value";
    private static final String ACCOUNT_NUMBER = "Account Number";
    public static final String INGRESO = "Ingreso";
    public static final String EGRESO = "Egreso";

    private final MovementRepository movementRepository;
    private final MovementMapper movementMapper = Mappers.getMapper(MovementMapper.class);
    private final AccountRepository accountRepository;

    public MovementServiceImpl(MovementRepository movementRepository, AccountRepository accountRepository) {
        this.movementRepository = movementRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public MovementDTO createMovement(MovementDTO movementDTO) {
        Account account = validateAndGetAccount(movementDTO.getAccountNumber());
        if (movementDTO.getDate() == null) movementDTO.setDate(LocalDateTime.now());
        movementDTO.setStatus(Boolean.TRUE);
        validateMovementDTO(movementDTO);

        double newBalance = calculateNewBalance(account.getInitialBalance(), movementDTO);

        account.setInitialBalance(newBalance);
        accountRepository.save(account);

        Movement movement = movementMapper.toEntity(movementDTO);
        movement.setBalance(newBalance);
        movement.setAccount(account);
        movement.setStatus(Boolean.TRUE);
        movement = movementRepository.save(movement);

        return movementMapper.toDTO(movement);
    }

    @Override
    public MovementDTO getMovementById(UUID movementId) {
        Movement movement = getMovement(movementId);
        return movementMapper.toDTO(movement);
    }

    @Override
    public List<MovementDTO> getAllMovements() {
        return movementRepository.findAll().stream()
                .map(movementMapper::toDTO)
                .toList();
    }

    @Override
    public MovementDTO updateMovement(UUID movementId, MovementDTO movementDTO) {
        Movement existingMovement = getMovement(movementId);
        Account account = existingMovement.getAccount();
        if (movementDTO.getStatus() == null) movementDTO.setStatus(existingMovement.getStatus());
        movementDTO.setDate(LocalDateTime.now());
        movementDTO.setMovementId(movementId);
        if (Boolean.FALSE.equals(account.getStatus()))
            throw new BusinessException(ErrorCodesEnum.THE_ACCOUNT_IS_NOT_FOUND_OR_INACTIVE, account.getAccountNumber());

        revertPreviousBalance(account, existingMovement);

        validateMovementDTO(movementDTO);

        double newBalance = calculateNewBalance(account.getInitialBalance(), movementDTO);

        account.setInitialBalance(newBalance);
        accountRepository.save(account);

        movementMapper.updateMovementFromDto(movementDTO, existingMovement);
        existingMovement.setBalance(newBalance);
        movementRepository.save(existingMovement);

        return movementMapper.toDTO(existingMovement);
    }

    @Override
    public void deleteMovement(UUID movementId) {
        Movement movement = getMovement(movementId);
        Account account = movement.getAccount();

        revertPreviousBalance(account, movement);
        accountRepository.save(account);

        deactivateMovement(movement);
    }

    @Override
    public List<MovementDTO> getMovementsdByAccountAccountNumberOrderByDateDesc(Long accountNumber) {
        return movementRepository.findByAccountAccountNumberOrderByDateDesc(accountNumber)
                .stream().map(movementMapper::toDTO).toList();
    }

    private void validateMovementDTO(MovementDTO movementDTO) {
        ArgumentValidator.requireNotEmpty(movementDTO.getMovementType(), MOVEMENT_TYPE);
        ArgumentValidator.validValueInList(List.of(INGRESO, EGRESO), movementDTO.getMovementType(), MOVEMENT_TYPE);

        ArgumentValidator.requireNotEmpty(movementDTO.getValue().toString(), VALUE);
        ArgumentValidator.requireNumeric(movementDTO.getValue().toString(), VALUE);
        ArgumentValidator.requirePositiveAndGreaterThanZero(movementDTO.getValue().toString(), VALUE);

        ArgumentValidator.requireNotEmpty(movementDTO.getAccountNumber().toString(), ACCOUNT_NUMBER);
    }

    private Account validateAndGetAccount(Long accountNumber) {
        return accountRepository.findById(accountNumber)
                .filter(Account::getStatus)
                .orElseThrow(() -> new BusinessException(ErrorCodesEnum.THE_ACCOUNT_IS_NOT_FOUND_OR_INACTIVE, accountNumber.toString()));
    }


    private Movement getMovement(UUID movementId) {
        return movementRepository.findById(movementId)
                .filter(Movement::getStatus)
                .orElseThrow(() -> new BusinessException(ErrorCodesEnum.THE_MOVEMENT_IS_NOT_FOUND_OR_INACTIVE, movementId.toString()));
    }

    private double calculateNewBalance(double currentBalance, MovementDTO movementDTO) {
        double newBalance = currentBalance;
        if (movementDTO.getMovementType().equalsIgnoreCase(INGRESO)) {
            newBalance += movementDTO.getValue();
        } else if (movementDTO.getMovementType().equalsIgnoreCase(EGRESO)) {
            if (newBalance < movementDTO.getValue()) {
                throw new BusinessException(ErrorCodesEnum.INSUFFICIENT_FUNDS, movementDTO.getAccountNumber().toString());
            }
            newBalance -= movementDTO.getValue();
        }
        return newBalance;
    }

    private void revertPreviousBalance(Account account, Movement movement) {
        if (movement.getMovementType().equalsIgnoreCase(INGRESO)) {
            account.setInitialBalance(account.getInitialBalance() - movement.getValue());
        } else if (movement.getMovementType().equalsIgnoreCase(EGRESO)) {
            account.setInitialBalance(account.getInitialBalance() + movement.getValue());
        }
    }

    private void deactivateMovement(Movement movement) {
        movement.setStatus(false);
        movementRepository.save(movement);
    }
}
