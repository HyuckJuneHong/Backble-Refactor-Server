package kr.co.popool.bblmember.infra.error.handler;

import kr.co.popool.bblmember.infra.error.exception.JwtTokenExpiredException;
import kr.co.popool.bblmember.infra.error.exception.JwtTokenInvalidException;
import kr.co.popool.bblmember.infra.error.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FilterExceptionHandler {

    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleJwtTokenExpiredException(JwtTokenExpiredException e){
        return ResponseEntity.ok(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(JwtTokenInvalidException.class)
    public ResponseEntity<ErrorResponse> handleJwtTokenInvalidException(JwtTokenInvalidException e){
        return ResponseEntity.ok(ErrorResponse.of(e.getMessage()));
    }
}
