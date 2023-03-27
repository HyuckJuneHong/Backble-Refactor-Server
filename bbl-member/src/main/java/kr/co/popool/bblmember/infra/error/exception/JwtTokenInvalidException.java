package kr.co.popool.bblmember.infra.error.exception;

import kr.co.popool.bblmember.infra.error.model.ErrorCode;

public class JwtTokenInvalidException extends UserDefineException{
    public JwtTokenInvalidException(ErrorCode errorCode){
        super(errorCode);
    }
}