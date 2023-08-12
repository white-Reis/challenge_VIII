package Fabio.Reis.Scholarship.model.studentEntity.studentDTO;

import lombok.Data;

@Data
public class StudentDTO {
    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String course;

    private int level;
}
