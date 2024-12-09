package sgms.ugc.dto;

import sgms.ugc.enums.BusinessErrorCode;

public record ApiResponse<T>(int status, String message, T data) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(0, "success", data);
    }
    public static <T> ApiResponse<T> ok(){
        return new ApiResponse<>(0, "success", null);
    }


    public static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<>(status, message, null);
    }
    public static <T> ApiResponse<T> error(int status, String message, T data) {
        return new ApiResponse<>(status, message, data);
    }
    public static <T> ApiResponse<T> error(){
        return new ApiResponse<>(1000, "Internal server error", null);
    }
    public static <T> ApiResponse<T> error(BusinessErrorCode code){
        return new ApiResponse<>(code.getCode(), code.getMessage(), null);
    }
    public static <T> ApiResponse<T> error(BusinessErrorCode code, T data){
        return new ApiResponse<>(code.getCode(), code.getMessage(), data);
    }
}

