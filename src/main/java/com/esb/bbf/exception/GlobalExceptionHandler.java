package com.esb.bbf.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handle reward exception http entity.
     *
     * @param e the e
     * @return the http entity
     */
    @ExceptionHandler(Exception.class)
    public HttpEntity<?> handleRewardException(final Exception e) {
        log.error("caught exception", e);
        Map map = new HashMap<>();
        map.put("status", "error");
        map.put("code",500);
        map.put("message", e.getMessage());
        map.put("msg", e.getMessage());
        return new ResponseEntity<>(map,  HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
