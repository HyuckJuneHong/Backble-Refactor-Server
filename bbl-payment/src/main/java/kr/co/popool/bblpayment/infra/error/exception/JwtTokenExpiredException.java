package kr.co.popool.bblpayment.infra.error.exception;

import kr.co.popool.bblpayment.infra.error.model.ErrorCode;

public class JwtTokenExpiredException extends BusinessLogicException{
    public JwtTokenExpiredException(ErrorCode errorCode) {
        super(errorCode);
    }
}
