package Fabio.Reis.Scholarship.model.teamEntity.teamDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamsToStatus {
    List<TeamDTO> waintingClasses = new ArrayList<>();
    List<TeamStartedDTO> stardedClasses = new ArrayList<>();
    List<TeamStartedDTO> finishedClasses = new ArrayList<>();
}
