package Fabio.Reis.Scholarship.model.squadEntity.squadDTO;

import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SquadDTO {
    private Long id;
    private String name;
    private Set<StudentDTO> students;
}
