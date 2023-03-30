package kr.co.popool.bblpayment.infra.error.handler;

import kr.co.popool.bblpayment.infra.error.exception.UserDefineException;
import kr.co.popool.bblpayment.infra.error.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleDuplicationException(RuntimeException e){
        return ResponseEntity.ok(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(UserDefineException.class)
    public ResponseEntity<ErrorResponse> handleUserDefineException(UserDefineException e) {
        return ResponseEntity.ok(ErrorResponse.of(e.getMessage()));
    }
}
