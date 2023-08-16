package Fabio.Reis.Scholarship.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectNotFoundExceptionTest {
    @Test
    void testObjectNotFoundExceptionMessage() {
        String errorMessage = "Object not found.";

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
            throw new ObjectNotFoundException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }

}