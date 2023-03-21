package kr.co.popool.bblpayment.infra.error.handler;

import kr.co.popool.bblpayment.infra.error.exception.JwtTokenExpiredException;
import kr.co.popool.bblpayment.infra.error.exception.JwtTokenInvalidException;
import kr.co.popool.bblpayment.infra.error.model.ResponseFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FilterExceptionHandler {

    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity handlerJwtTokenExpiredException(JwtTokenExpiredException e){
        ResponseFormat responseFormat = ResponseFormat.expire();

        return new ResponseEntity(responseFormat, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtTokenInvalidException.class)
    public ResponseEntity handleJwtTokenInvalidException(JwtTokenInvalidException e){
        ResponseFormat responseFormat = ResponseFormat.fail(e.getMessage());

        return new ResponseEntity(responseFormat, HttpStatus.OK);
    }
}
