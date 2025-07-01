package vn.edu.ptit.duongvct.practice1.socialmedia.blob_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.edu.ptit.duongvct.practice1.socialmedia.blob_service.dto.response.RestResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(
            Exception.class
    )
    public ResponseEntity<RestResponse<Object>> handleAllException(Exception exception) {
        RestResponse<Object> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setError("Internal Server Error");
        response.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class,
    } )
    public ResponseEntity<RestResponse<Object>> handleInvalidRequestException(IllegalArgumentException exception) {
        RestResponse<Object> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exception.getMessage());
        response.setError("Exception occurs");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
