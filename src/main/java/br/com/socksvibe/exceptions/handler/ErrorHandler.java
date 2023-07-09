package br.com.socksvibe.exceptions.handler;

import br.com.socksvibe.exceptions.ResponseBadRequestException;
import br.com.socksvibe.exceptions.ResponseNotFoundException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ResponseBadRequestException.class })
    public ResponseEntity<Object> handleBadRequest(HttpServletRequest request, ResponseBadRequestException e) throws JsonProcessingException {
    ErrorApi apiErrorException = new ErrorApi(request.getRequestURI(), HttpStatus.BAD_REQUEST, e.getMessage());
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsString(apiErrorException));
    }

    @ExceptionHandler({ ResponseNotFoundException.class })
    public ResponseEntity<Object> handleNotFoundRequest(HttpServletRequest request, ResponseNotFoundException e) throws  JsonProcessingException {
        ErrorApi apiErrorException = new ErrorApi(request.getRequestURI(), HttpStatus.NOT_FOUND, e.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsString(apiErrorException));
    }
}
