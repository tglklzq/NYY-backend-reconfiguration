package org.lzq.nyybackend.model.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private int statusCode;
    private boolean success;
    private String message;
    private T data;

    public ApiResponse() {}

    public ApiResponse(ResponseStatus status, boolean success, T data) {
        this.statusCode = status.getCode();
        this.message = status.getMessage();
        this.success = success;
        this.data = data;
    }
}
