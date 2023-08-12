package Fabio.Reis.Scholarship.model.studentEntity.studentDTO;

import Fabio.Reis.Scholarship.validation.noNumbers.NoNumbers;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudentRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    @NoNumbers(message = "Name cannot contain numbers")
    private String name;

    @NotBlank(message = "Last name cannot be blank")
    @NoNumbers(message = "Last name cannot contain numbers")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Course cannot be blank")
    @NoNumbers(message = "Course cannot contain numbers")
    private String course;

    @Min(value = 1, message = "Level must be at least 1")
    @Max(value = 3, message = "Level must be at most 3")
    private int level;
}