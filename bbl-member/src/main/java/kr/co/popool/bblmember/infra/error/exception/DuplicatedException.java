package kr.co.popool.bblmember.infra.error.exception;

import kr.co.popool.bblmember.infra.error.model.ErrorCode;

public class DuplicatedException extends BusinessLogicException{
    public DuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
