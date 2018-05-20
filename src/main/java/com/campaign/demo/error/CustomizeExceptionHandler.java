package com.campaign.demo.error;

import com.campaign.demo.controller.CampaignController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomizeExceptionHandler {

    public final static Logger logger = LogManager.getLogger(CampaignController.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(Exception ex) {
        logger.error(ex);

        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logger.error(ex);

        List<ErrorResponse> errors = new ArrayList<>(ex.getBindingResult().getAllErrors().size());
        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            errors.add(new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), objectError.getDefaultMessage()));
        }

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        logger.error(ex);

        List<ErrorResponse> errors = new ArrayList<>(ex.getConstraintViolations().size());
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), violation.getMessage()));
        }

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<?> handleServletRequestBindingException(ServletRequestBindingException ex) {
        logger.error(ex);

        return ResponseEntity.badRequest().body(new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownException(Exception ex) {
        logger.error(ex);

        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
