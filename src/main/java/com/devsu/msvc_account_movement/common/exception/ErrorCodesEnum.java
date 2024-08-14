package com.devsu.msvc_account_movement.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCodesEnum {
    NOT_NULL("0001", HttpStatus.BAD_REQUEST, "El campo %s no puede estar vacío."),
    NOT_VALUES_DIFFERENT_FROM_LETTERS("0002", HttpStatus.BAD_REQUEST, "El campo %s no puede contener valores distintos a letras."),
    IT_IS_NOT_AN_EMAIL("0003", HttpStatus.BAD_REQUEST, "El campo %s no tiene un formato de correo electrónico válido, por ejemplo: usuario@dominio.com."),
    THE_LIST_CANNOT_BE_EMPTY("0004", HttpStatus.BAD_REQUEST, "La lista de %s no puede estar vacía."),
    IT_IS_NOT_NUMERIC("0005", HttpStatus.BAD_REQUEST, "El campo %s debe ser numérico."),
    THE_FIELD_CANNOT_BE_LESS_THAN_OR_EQUAL_TO_ZERO("0006", HttpStatus.BAD_REQUEST, "El campo %s no puede ser menor o igual a cero."),
    IT_MUST_NOT_MEET_WITH_THE_CONFIGURED_SPECIFICATIONS("0007", HttpStatus.BAD_REQUEST, "El campo %s no cumple con las especificaciones configuradas."),
    THE_FIELD_IS_NOT_IN_LIST("0008", HttpStatus.BAD_REQUEST, "El número de cuenta no está en la lista; los valores posibles son %s."),
    THE_ACCOUNT_IS_ALREADY_REGISTRED("0009", HttpStatus.BAD_REQUEST, "La cuenta con ID de cliente: %s ya está registrada."),
    THE_ACCOUNT_IS_NOT_FOUND_OR_INACTIVE("0010", HttpStatus.BAD_REQUEST, "La cuenta con número: %s no se encuentra o está inactiva."),
    INSUFFICIENT_FUNDS("0011", HttpStatus.BAD_REQUEST, "Saldo insuficiente para la cuenta con número: %s."),
    THE_MOVEMENT_IS_NOT_FOUND_OR_INACTIVE("0012", HttpStatus.BAD_REQUEST, "El movimiento con ID: %s no se encuentra o está inactivo."),
    INVALID_DATE_FORMAT("0012", HttpStatus.BAD_REQUEST, "El movimiento con ID: %s no se encuentra o está inactivo.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    ErrorCodesEnum(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

}
