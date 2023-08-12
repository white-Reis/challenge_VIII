package Fabio.Reis.Scholarship.services.teamService;

import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TeamService_i {

    ResponseEntity<TeamDTO> getClassById(Long id);

    ResponseEntity<List<TeamDTO>> getClasses();

    ResponseEntity<List<InternalDTO>> getClassInstructors(Long id);

    ResponseEntity<List<InternalDTO>> getScrumMasters(Long id);

    ResponseEntity<List<InternalDTO>> getCoordinators(Long id);

    ResponseEntity<List<StudentDTO>> getStudents(Long id);

    ResponseEntity<Void> updateClass(Long id, TeamRequestDTO teamRequest);

    ResponseEntity<Void> startClass(Long id);

    ResponseEntity<Void> finishClass(Long id);

    ResponseEntity<Void> createClassWithStudentsAndInternals(TeamRequestDTO teamRequest);
}
