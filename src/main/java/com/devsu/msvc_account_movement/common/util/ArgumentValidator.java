package com.devsu.msvc_account_movement.common.util;

import com.devsu.msvc_account_movement.common.exception.BusinessException;
import com.devsu.msvc_account_movement.common.exception.ErrorCodesEnum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;

public class ArgumentValidator {
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
    private static final String REGEX_ONLY_LETTERS = "^[\\p{L} \\p{M}]+$";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private ArgumentValidator() { /**/ }

    public static void requireNotEmpty(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new BusinessException(ErrorCodesEnum.NOT_NULL, field);
        }
    }

    public static void validFormatEmail(String emailAddress, String field) {
        Pattern pattern = Pattern.compile(REGEX_EMAIL);

        if (!pattern.matcher(emailAddress).matches()) {
            throw new BusinessException(ErrorCodesEnum.IT_IS_NOT_AN_EMAIL, field);
        }
    }

    public static void onlyAcceptLetters(String value, String field) {
        Pattern pattern = Pattern.compile(REGEX_ONLY_LETTERS);

        if (!pattern.matcher(value).matches()) {
            throw new BusinessException(ErrorCodesEnum.NOT_VALUES_DIFFERENT_FROM_LETTERS, field);
        }
    }

    public static <T> void listNotEmpty(List<T> list, String field) {
        if (list == null || list.isEmpty()) {
            throw new BusinessException(ErrorCodesEnum.THE_LIST_CANNOT_BE_EMPTY, field);
        }
    }

    public static void requirePositiveAndGreaterThanZero(String value, String field) {
        if (Double.parseDouble(value) <= 0) {
            throw new BusinessException(ErrorCodesEnum.THE_FIELD_CANNOT_BE_LESS_THAN_OR_EQUAL_TO_ZERO, field);
        }
    }

    public static void requireNumeric(String value, String field) {
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException numberFormatException) {
            throw new BusinessException(ErrorCodesEnum.IT_IS_NOT_NUMERIC, field);
        }
    }

    public static void mustMeetTheConfiguredSpecifications(String regex, String value, String field) {
        Pattern pattern = Pattern.compile(regex);

        if (!pattern.matcher(value).matches()) {
            throw new BusinessException(ErrorCodesEnum.IT_MUST_NOT_MEET_WITH_THE_CONFIGURED_SPECIFICATIONS, field);
        }
    }

    public static void validValueInList(List<String> validValues, String value, String field) {
        if (!validValues.contains(value)) {
            throw new BusinessException(ErrorCodesEnum.THE_FIELD_IS_NOT_IN_LIST, field);
        }
    }

    public static LocalDateTime validDateFormat(String date, String field) {
        LocalDateTime validDate;
        try {
            validDate = LocalDateTime.parse(date, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new BusinessException(ErrorCodesEnum.INVALID_DATE_FORMAT, field);
        }
        return validDate;
    }

}
