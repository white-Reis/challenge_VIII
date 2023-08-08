package Fabio.Reis.Scholarship.model.internalEntity.internaDTO;

import lombok.Data;

@Data
public class InternalRequestDto {
    private String name;
    private String lastName;
    private String email;
    private String position;
    private String role;
}