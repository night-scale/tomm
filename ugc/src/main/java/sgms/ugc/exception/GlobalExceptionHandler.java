package sgms.ugc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import sgms.ugc.dto.ApiResponse;
import sgms.ugc.enums.BusinessErrorCode;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 默认错误捕获方式， 返回1000 internal server error
    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleGenericException(Exception ex) {
        return ApiResponse.error();
    }

    @ExceptionHandler(DataAccessE.class)
    public ApiResponse<String> handleDataAccessException(DataAccessE ex) {
        return ApiResponse.error(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(ResourceNotExistE.class)
    public ApiResponse<String> handleResourceNotExistException(ResourceNotExistE ex) {
        return ApiResponse.error(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleJSRException(MethodArgumentNotValidException ex) {
        return ApiResponse.error(BusinessErrorCode.PARAM_NOT_VALID);
    }
}
