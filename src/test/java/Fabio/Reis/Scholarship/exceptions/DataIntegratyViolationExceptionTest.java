package Fabio.Reis.Scholarship.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataIntegratyViolationExceptionTest {
    @Test
    void testDataIntegratyViolationExceptionMessage() {
        String errorMessage = "This is an error message.";

        DataIntegratyViolationException exception = assertThrows(DataIntegratyViolationException.class, () -> {
            throw new DataIntegratyViolationException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }

}