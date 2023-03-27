package kr.co.popool.bblmember.infra.error.exception;

import kr.co.popool.bblmember.infra.error.model.ErrorCode;

public class BusinessLogicException extends RuntimeException{
    private ErrorCode errorCode;

    public BusinessLogicException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessLogicException(String message){
        super(message);
    }
}
