package Fabio.Reis.Scholarship.model.teamEntity.teamDTO;


import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalRequestDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentRequestDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
public class TeamRequestDTO {
    private String name;
    private String learning;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<StudentRequestDTO> students;
    private List<InternalRequestDTO> coordinators;
    private List<InternalRequestDTO> scrumMasters;
    private List<InternalRequestDTO> instructors;

}