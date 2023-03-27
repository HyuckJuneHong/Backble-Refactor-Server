package kr.co.popool.gateway.error.exception;

import kr.co.popool.gateway.error.model.ErrorCode;

public class BusinessLogicException extends RuntimeException{
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    private ErrorCode errorCode;

    public BusinessLogicException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessLogicException(String part,
                                  ErrorCode errorCode){
        super(part + " : " + errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessLogicException(String message){
        super(message);
    }
}
