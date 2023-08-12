package Fabio.Reis.Scholarship.model.teamEntity.teamDTO;


import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalRequestDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentRequestDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;


@Data
public class TeamRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Learning cannot be blank")
    private String learning;

    private List<StudentRequestDTO> students;
    private List<InternalRequestDTO> coordinators;
    private List<InternalRequestDTO> scrumMasters;
    private List<InternalRequestDTO> instructors;

}