package Fabio.Reis.Scholarship.annotations.noNumbers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoNumbersValidatorTest {

    @Test
    public void testIsValidWithNullValue() {
        NoNumbersValidator validator = new NoNumbersValidator();
        String value = null;
        assertTrue(validator.isValid(value, null));
    }

    @Test
    public void testIsValidWithValidValue() {
        NoNumbersValidator validator = new NoNumbersValidator();
        String value = "This is a valid value";
        assertTrue(validator.isValid(value, null));
    }

    @Test
    public void testIsValidWithInvalidValue() {
        NoNumbersValidator validator = new NoNumbersValidator();
        String value = "This value contains a number: 123";
        assertFalse(validator.isValid(value, null));
    }
}