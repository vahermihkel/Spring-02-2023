package ee.mihkel.webshop.controller.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice // KÕIKIDELE KONTROLLERITELE
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleError(FailedException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setDate(new Date());
        response.setHttpStatusCode(400);
        response.setMessage("Request failed!");
        return ResponseEntity.badRequest().body(response);
    }

//    @ExceptionHandler
//    public ResponseEntity<ExceptionResponse> handleError(PasswordIncorrectException e) {
//        ExceptionResponse response = new ExceptionResponse();
//        response.setDate(new Date());
//        response.setHttpStatusCode(400);
//        response.setMessage("Password incorrect!");
//        return ResponseEntity.badRequest().body(response);
//    }

//    @ExceptionHandler
//    public ResponseEntity<> handleError(ProductNotFoundException e) {
//        return ResponseEntity.badRequest().body();
//    }

}
