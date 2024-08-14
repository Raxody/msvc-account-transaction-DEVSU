package com.devsu.msvc_account_movement.common.exception;

public class BusinessException extends RuntimeException {
    private final ErrorCodesEnum codes;

    public BusinessException(ErrorCodesEnum codes) {
        super(codes.getMessage());
        this.codes = codes;
    }

    public BusinessException(ErrorCodesEnum codes, Object... args) {
        super(String.format(codes.getMessage(), args));
        this.codes = codes;
    }

    public ErrorCodesEnum getCodes() {
        return codes;
    }
}

