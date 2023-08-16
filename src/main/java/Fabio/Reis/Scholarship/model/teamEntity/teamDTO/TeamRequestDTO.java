package Fabio.Reis.Scholarship.model.teamEntity.teamDTO;


import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalRequestDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentRequestDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Learning cannot be blank")
    private String learning;

    private List<StudentRequestDTO> students;
    private List<InternalRequestDTO> internals;


}