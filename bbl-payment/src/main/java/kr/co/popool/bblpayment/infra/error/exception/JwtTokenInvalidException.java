package kr.co.popool.bblpayment.infra.error.exception;

import kr.co.popool.bblpayment.infra.error.model.ErrorCode;

public class JwtTokenInvalidException extends UserDefineException{
    public JwtTokenInvalidException(ErrorCode errorCode){
        super(errorCode);
    }
}