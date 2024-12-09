package sgms.ugc.exception;

import sgms.ugc.enums.BusinessErrorCode;

public class ResourceNotExistE extends RuntimeException{
    private final BusinessErrorCode errorCode;

    public ResourceNotExistE(BusinessErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public int getCode() {
        return errorCode.getCode();
    }
    public String getMessage(){
        return errorCode.getMessage();
    }
}
