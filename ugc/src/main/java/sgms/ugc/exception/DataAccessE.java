package sgms.ugc.exception;

import sgms.ugc.enums.BusinessErrorCode;

public class DataAccessE extends RuntimeException{
    private final BusinessErrorCode errorCode;

    public DataAccessE(BusinessErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public int getCode() {
        return errorCode.getCode();
    }
    public String getMessage(){
        return errorCode.getMessage();
    }
    //    public HttpStatus getHttpStatus() {
    //        return errorCode.getStatus();
    //    }
}
