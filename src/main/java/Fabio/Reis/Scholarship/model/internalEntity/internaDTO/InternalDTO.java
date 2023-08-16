package Fabio.Reis.Scholarship.model.internalEntity.internaDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InternalDTO {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String position;
    private String role;
}

