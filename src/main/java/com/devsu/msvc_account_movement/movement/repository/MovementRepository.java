package com.devsu.msvc_account_movement.movement.repository;

import com.devsu.msvc_account_movement.movement.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MovementRepository extends JpaRepository<Movement, UUID> {

    List<Movement> findByAccountAccountNumberOrderByDateDesc(Long accountNumber);

    List<Movement> findByAccountAccountNumberAndDateBetween(Long accountNumber, LocalDateTime startDate, LocalDateTime endDate);
    
}
