package Fabio.Reis.Scholarship.handler;

import Fabio.Reis.Scholarship.exceptions.ClassRuleException;
import Fabio.Reis.Scholarship.exceptions.DataIntegratyViolationException;
import Fabio.Reis.Scholarship.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

class ControllerExceptionHandlerTest {
    private final ControllerExceptionHandler controllerExceptionHandler = new ControllerExceptionHandler();

    @Test
    void testObjectNotFoundExceptionHandling() {
        ObjectNotFoundException ex = new ObjectNotFoundException("Object not found.");
        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<StandardError> response = controllerExceptionHandler.objectNotFound(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Object not found.", response.getBody().getError());
    }

    @Test
    void testDataIntegratyViolationExceptionHandling() {
        DataIntegratyViolationException ex = new DataIntegratyViolationException("Data integrity violation.");
        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<StandardError> response = controllerExceptionHandler.dataIntegratyViolation(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Data integrity violation.", response.getBody().getError());
    }

    @Test
    void testClassRuleExceptionHandling() {
        ClassRuleException ex = new ClassRuleException("Class rule exception.");
        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<StandardError> response = controllerExceptionHandler.classRuleException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Class rule exception.", response.getBody().getError());
    }
}