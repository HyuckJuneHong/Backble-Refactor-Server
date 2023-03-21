package kr.co.popool.infra.error.exception;


import kr.co.popool.infra.error.model.ErrorCode;

public class JwtTokenExpiredException extends BusinessLogicException{
    public JwtTokenExpiredException(ErrorCode errorCode) {
        super(errorCode);
    }
}
