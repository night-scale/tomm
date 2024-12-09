package sgms.ugc.enums;

import lombok.Getter;

@Getter
public enum BusinessErrorCode {
    //    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND),
    //    INVALID_INPUT(1002, "Invalid input", HttpStatus.BAD_REQUEST),
    //    UNAUTHORIZED(1003, "Unauthorized", HttpStatus.FORBIDDEN),
    //    INTERNAL_SERVER_ERROR(1000, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    //枚举自定义业务异常
    DATABASE_ACCESS_ERROR(1001, "internal database access error"),
    EMPTY_FIELD(1002, "empty request field"),
    RESOURCE_NOT_EXIST(1003, "resource does not exist"),
    NOT_OWNER(1004, "you are not the owner"),
    CONTENT_NOT_EXIST(1005, "content does not exist"),
    PARAM_NOT_VALID(1006, "Method Argument Not Valid");
    private final int code;
    private final String message;

    BusinessErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    //private final HttpStatus status;
}
