package kr.co.popool.bblmember.infra.error.exception;

import kr.co.popool.bblmember.infra.error.model.ErrorCode;

public class JwtTokenExpiredException extends BusinessLogicException{
    public JwtTokenExpiredException(ErrorCode errorCode) {
        super(errorCode);
    }
}
