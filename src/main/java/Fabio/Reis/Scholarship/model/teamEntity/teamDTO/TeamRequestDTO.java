package Fabio.Reis.Scholarship.model.teamEntity.teamDTO;


import lombok.Data;

import java.time.LocalDate;

@Data
public class TeamRequestDTO {
    private String name;
    private String learning;
    private LocalDate startDate;
    private LocalDate endDate;

}