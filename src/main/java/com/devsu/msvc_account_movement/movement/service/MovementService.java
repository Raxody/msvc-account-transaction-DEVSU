package com.devsu.msvc_account_movement.movement.service;

import com.devsu.msvc_account_movement.movement.dto.MovementDTO;

import java.util.List;
import java.util.UUID;

public interface MovementService {
    MovementDTO createMovement(MovementDTO movementDTO);
    MovementDTO getMovementById(UUID movementId);
    List<MovementDTO> getAllMovements();
    MovementDTO updateMovement(UUID movementId, MovementDTO movementDTO);
    void deleteMovement(UUID movementId);

    List<MovementDTO> getMovementsdByAccountAccountNumberOrderByDateDesc(Long accountNumber);
}
