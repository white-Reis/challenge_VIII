package Fabio.Reis.Scholarship.model.studentEntity.studentDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentDTO {
    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String course;

    private int level;

}
