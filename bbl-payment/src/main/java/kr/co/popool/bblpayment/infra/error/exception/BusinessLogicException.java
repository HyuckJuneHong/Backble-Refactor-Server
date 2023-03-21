package kr.co.popool.bblpayment.infra.error.exception;

import kr.co.popool.bblpayment.infra.error.model.ErrorCode;

public class BusinessLogicException extends RuntimeException{
    private ErrorCode errorCode;

    public BusinessLogicException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessLogicException(String part, ErrorCode errorCode){
        super(part + " : " + errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessLogicException(String message){
        super(message);
    }
}
