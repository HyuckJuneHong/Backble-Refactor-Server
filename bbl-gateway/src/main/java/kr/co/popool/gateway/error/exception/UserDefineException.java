package kr.co.popool.gateway.error.exception;

import kr.co.popool.gateway.error.model.ErrorCode;

public class UserDefineException extends RuntimeException{
    private ErrorCode errorCode;
    private String originalMessage;

    public UserDefineException(String message,
                               String originalMessage){
        super(message);
        this.originalMessage = originalMessage;
    }

    public UserDefineException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
