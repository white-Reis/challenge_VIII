package Fabio.Reis.Scholarship.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClassRuleExceptionTest {

    @Test
    void testClassRuleExceptionMessage() {
        String errorMessage = "This is an error message.";

        ClassRuleException exception = assertThrows(ClassRuleException.class, () -> {
            throw new ClassRuleException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }

}