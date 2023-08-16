package Fabio.Reis.Scholarship.model.teamEntity.teamDTO;

import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.squadEntity.squadDTO.SquadDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamStartedDTO {
    private Long id;
    private String name;
    private String learning;
    private String status;
    private LocalDate startDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate endDate;
    private Set<InternalDTO> coordinators;
    private Set<InternalDTO> scrumMasters;
    private Set<InternalDTO> instructors;
    private Set<SquadDTO> squads;
}
