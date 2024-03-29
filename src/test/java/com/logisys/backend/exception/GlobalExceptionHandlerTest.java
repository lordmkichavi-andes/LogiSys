package com.logisys.backend.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        webRequest = new ServletWebRequest(new MockHttpServletRequest());
    }

    @Test
    void handleValidationExceptions_ShouldReturnBadRequest() {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(null, "objectName");
        bindingResult.addError(new FieldError("objectName", "fieldName", "Default message"));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<?> responseEntity = exceptionHandler.handleValidationExceptions(ex);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Map<String, Object> body = (Map<String, Object>) responseEntity.getBody();
        assertThat(body).containsKey("errors");
        assertThat(((Map<String, String>)body.get("errors")).containsKey("fieldName")).isTrue();
    }

    @Test
    void handleAccessDeniedException_ShouldReturnForbidden() {
        AccessDeniedException ex = new AccessDeniedException("Access is denied");

        ResponseEntity<?> responseEntity = exceptionHandler.handleAccessDeniedException(ex, webRequest);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        Map<String, Object> body = (Map<String, Object>) responseEntity.getBody();
        assertThat(body.get("message")).isEqualTo("Acceso denegado");
    }

    @Test
    void globalExceptionHandler_ShouldReturnInternalServerError() {
        Exception ex = new Exception("An unexpected error occurred");

        ResponseEntity<?> responseEntity = exceptionHandler.globalExceptionHandler(ex, webRequest);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        Map<String, Object> body = (Map<String, Object>) responseEntity.getBody();
        assertThat(body.get("message")).isEqualTo("Ha ocurrido un error inesperado. Por favor, contacte al soporte técnico.");
    }
}
