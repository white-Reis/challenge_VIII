package Fabio.Reis.Scholarship.model.teamEntity.teamDTO;

import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.squadEntity.squadDTO.SquadDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class TeamStartedDTO {
    private Long id;
    private String name;
    private String learning;
    private int status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<InternalDTO> coordinators;
    private Set<InternalDTO> scrumMasters;
    private Set<InternalDTO> instructors;
    private Set<SquadDTO> squads;
}
