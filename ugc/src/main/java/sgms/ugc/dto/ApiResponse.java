package sgms.ugc.dto;

public record ApiResponse<T>(int status, String message, T data) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(0, "Request was successful", data);
    }
    public static <T> ApiResponse<T> ok(){
        return new ApiResponse<>(0, "Request was successful", null);
    }


    public static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<>(status, message, null);
    }
    public static <T> ApiResponse<T> error(int status, String message, T data) {
        return new ApiResponse<>(status, message, data);
    }

}

