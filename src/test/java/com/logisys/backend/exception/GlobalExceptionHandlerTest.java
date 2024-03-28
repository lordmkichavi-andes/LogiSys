package com.logisys.backend.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @Test
    void handleValidationExceptions() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(null, "");
        bindingResult.addError(fieldError);

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        Map<String, String> errors = exceptionHandler.handleValidationExceptions(ex);
        assertThat(errors).containsKey("field");
        assertThat(errors.get("field")).isEqualTo("defaultMessage");
    }

    @Test
    void globalExceptionHandler() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        Exception ex = new RuntimeException("Test Exception");
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/test-uri");

        ResponseEntity<?> responseEntity = exceptionHandler.globalExceptionHandler(ex, request);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        Map<String, Object> body = (Map<String, Object>) responseEntity.getBody();
        assert body != null;
        assertThat(body.get("message")).isEqualTo("Ha ocurrido un error inesperado. Por favor, contacte al soporte técnico.");
        assertThat(body.get("path")).isEqualTo("/test-uri");
    }
}
