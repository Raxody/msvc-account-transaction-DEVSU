package com.devsu.msvc_account_movement.movement.controller;

import com.devsu.msvc_account_movement.movement.dto.MovementDTO;
import com.devsu.msvc_account_movement.movement.service.MovementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/movements")
public class MovementController {

    private final MovementService movementService;

    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @PostMapping
    public ResponseEntity<MovementDTO> createMovement(@RequestBody MovementDTO movementDTO) {
        MovementDTO createdMovement = movementService.createMovement(movementDTO);
        return new ResponseEntity<>(createdMovement, HttpStatus.CREATED);
    }

    @GetMapping("/{movementId}")
    public ResponseEntity<MovementDTO> getMovementById(@PathVariable UUID movementId) {
        MovementDTO movementDTO = movementService.getMovementById(movementId);
        return new ResponseEntity<>(movementDTO, HttpStatus.OK);
    }

    @GetMapping("/log/{accountId}")
    public ResponseEntity<List<MovementDTO>> getTransactionLogById(@PathVariable Long accountId) {
        List<MovementDTO> movements = movementService.getMovementsdByAccountAccountNumberOrderByDateDesc(accountId);
        return new ResponseEntity<>(movements, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MovementDTO>> getAllMovements() {
        List<MovementDTO> movements = movementService.getAllMovements();
        return new ResponseEntity<>(movements, HttpStatus.OK);
    }

    @PatchMapping("/{movementId}")
    public ResponseEntity<MovementDTO> updateMovement(@PathVariable UUID movementId, @RequestBody MovementDTO movementDTO) {
        MovementDTO updatedMovement = movementService.updateMovement(movementId, movementDTO);
        return new ResponseEntity<>(updatedMovement, HttpStatus.OK);
    }

    @DeleteMapping("/{movementId}")
    public ResponseEntity<String> deleteMovement(@PathVariable UUID movementId) {
        movementService.deleteMovement(movementId);
        return new ResponseEntity<>("Movement deleted", HttpStatus.OK);
    }
}
