package Fabio.Reis.Scholarship.model.studentEntity.studentDTO;

import Fabio.Reis.Scholarship.model.teamEntity.Team;
import lombok.Data;

@Data
public class StudentRequestDTO {
    private String name;
    private String lastName;
    private String email;
    private String course;
    private int level;
}