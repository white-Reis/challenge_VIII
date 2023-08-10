package Fabio.Reis.Scholarship.model.teamEntity.teamDTO;

import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TeamOffRolesDTO {

    private Long id;
    private String name;
    private String learning;
    private int status;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<InternalDTO> internals;
}
