package io.factorialsystems.msscstore21products.exception;


import io.factorialsystems.msscstore21products.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ProductControllerAdvice {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageDTO> handleRuntimeException(RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new MessageDTO(HttpStatus.BAD_REQUEST.value(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductExistsException.class)
    public ResponseEntity<MessageDTO> handleProductExistsException(ProductExistsException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new MessageDTO(HttpStatus.BAD_REQUEST.value(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongTenantException.class)
    public ResponseEntity<MessageDTO> handleWrongTenantException(WrongTenantException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(
                new MessageDTO(HttpStatus.FORBIDDEN.value(), exception.getMessage()), HttpStatus.FORBIDDEN);
    }
}
