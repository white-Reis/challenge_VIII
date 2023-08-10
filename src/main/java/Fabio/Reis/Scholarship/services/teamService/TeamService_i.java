package Fabio.Reis.Scholarship.services.teamService;

import Fabio.Reis.Scholarship.model.teamEntity.Team;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamOffRolesDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface TeamService_i {

    ResponseEntity<TeamDTO> getClassById(Long id);
    ResponseEntity<List<TeamDTO>> getClasses();
    ResponseEntity<Void> createClass(TeamRequestDTO teamRequest);
    ResponseEntity<Void> updateClass (TeamRequestDTO teamRequest);
    ResponseEntity<Void> startClass(Long id);
    ResponseEntity<Void> createClassWithStudentsAndInternals(TeamRequestDTO teamRequest);
}
