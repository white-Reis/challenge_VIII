package Fabio.Reis.Scholarship.validation.noNumbers;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoNumbersValidator implements ConstraintValidator<NoNumbers,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;  // Null values are considered valid
        }
        return !value.matches(".*\\d.*"); // Check if the value contains any digit
    }
}
