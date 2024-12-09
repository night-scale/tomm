package sgms.ugc.exception;

import sgms.ugc.enums.BusinessErrorCode;

public class BusinessException extends RuntimeException{
    private final int code;
    private final String message;

    public BusinessException(BusinessErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
