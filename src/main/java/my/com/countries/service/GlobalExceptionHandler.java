package my.com.countries.service;

import jakarta.servlet.http.HttpServletRequest;
import my.com.countries.controller.error.ApiError;
import my.com.countries.ex.CountryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Value("${api.version}")
    private String apiVersion;

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ApiError> handleCountryController(CountryNotFoundException ex, HttpServletRequest request) {
        LOG.error(request.getMethod() + " " + request.getRequestURL(), ex);
        return new ResponseEntity<>(
                new ApiError(
                        apiVersion,
                        HttpStatus.NOT_FOUND.toString(),
                        "Photo not found",
                        request.getRequestURI(),
                        ex.getMessage()
                ),
                HttpStatus.NOT_FOUND
        );
    }
}
