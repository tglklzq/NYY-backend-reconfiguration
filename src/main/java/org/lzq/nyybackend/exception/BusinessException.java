package org.lzq.nyybackend.exception;

import lombok.Getter;
import org.lzq.nyybackend.model.dto.ResponseStatus;

public class BusinessException extends RuntimeException {
    @Getter
    private final int code;
    private final String message;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(ResponseStatus status) {
        super(status.getMessage());
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
