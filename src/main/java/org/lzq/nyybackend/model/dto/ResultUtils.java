package org.lzq.nyybackend.model.dto;

public class ResultUtils {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ResponseStatus.SUCCESS, true, data);
    }

    public static ApiResponse<LoginResponse> successWithLoginData(LoginResponse loginResponse) {
        return new ApiResponse<>(ResponseStatus.SUCCESS, true, loginResponse);
    }

    public static <T> ApiResponse<T> error(ResponseStatus status) {
        return new ApiResponse<>(status, false, null);
    }

    public static <T> ApiResponse<T> error(ResponseStatus status, T data) {
        return new ApiResponse<>(status, false, data);
    }
}
