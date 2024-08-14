package com.devsu.msvc_account_movement.movement.mapper;

import com.devsu.msvc_account_movement.movement.dto.MovementDTO;
import com.devsu.msvc_account_movement.movement.entity.Movement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface MovementMapper {

    @Mapping(target = "account.accountNumber", source = "accountNumber")
    Movement toEntity(MovementDTO movementDTO);

    @Mapping(source = "account.accountNumber", target = "accountNumber")
    MovementDTO toDTO(Movement movement);

    @Mapping(target = "account.accountNumber", source = "accountNumber")
    void updateMovementFromDto(MovementDTO movementDTO, @MappingTarget Movement movement);
}
