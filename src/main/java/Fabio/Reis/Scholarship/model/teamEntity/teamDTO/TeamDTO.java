package Fabio.Reis.Scholarship.model.teamEntity.teamDTO;

import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import lombok.Data;

import java.time.LocalDate;

import java.util.Set;

@Data
public class TeamDTO {
    private Long id;
    private String name;
    private String learning;
    private String status;
    private Set<InternalDTO> coordinators;
    private Set<InternalDTO> scrumMasters;
    private Set<InternalDTO> instructors;
    private Set<StudentDTO> students;
}
