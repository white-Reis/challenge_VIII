package Fabio.Reis.Scholarship.model.internalEntity.internaDTO;

import Fabio.Reis.Scholarship.annotations.noNumbers.NoNumbers;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class InternalRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    @NoNumbers(message = "Name cannot contain numbers")
    private String name;

    @NotBlank(message = "Last name cannot be blank")
    @NoNumbers(message = "Last name cannot contain numbers")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Position cannot be blank")
    private String position;

    @NotBlank(message = "Role cannot be blank")
    @Pattern(regexp = "^(?i)(Instructor|Coordinator|Scrum Master)$", message = "Invalid role. Allowed values: Instructor, Coordinator, Scrum Master")
    private String role;
}