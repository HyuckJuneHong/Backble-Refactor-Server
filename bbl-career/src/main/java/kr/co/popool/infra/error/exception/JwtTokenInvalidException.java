package kr.co.popool.infra.error.exception;

import kr.co.popool.infra.error.model.ErrorCode;

public class JwtTokenInvalidException extends UserDefineException{
    public JwtTokenInvalidException(ErrorCode errorCode){
        super(errorCode);
    }
}