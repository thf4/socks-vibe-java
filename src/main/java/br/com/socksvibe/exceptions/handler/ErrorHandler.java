package br.com.socksvibe.exceptions.handler;

import br.com.socksvibe.exceptions.ResponseProductsException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({ ResponseProductsException.class })
    public ResponseEntity<Object> handleBadRequest(HttpServletRequest request, ResponseProductsException e) throws JsonProcessingException {
    ErrorApi apiErrorException = new ErrorApi(request.getRequestURI(), HttpStatus.BAD_REQUEST, e.getMessage());
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapper.writeValueAsString(apiErrorException));
    }
}
