package Fabio.Reis.Scholarship.services.teamService;

import Fabio.Reis.Scholarship.model.Commons.IdsList;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamRequestDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamsToStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TeamService {

    ResponseEntity<?> getClassById(Long id);

    ResponseEntity<TeamsToStatus> getClasses();

    ResponseEntity<List<InternalDTO>> getClassInstructors(Long id);

    ResponseEntity<List<InternalDTO>> getScrumMasters(Long id);

    ResponseEntity<List<InternalDTO>> getCoordinators(Long id);

    ResponseEntity<List<StudentDTO>> getStudents(Long id);

    ResponseEntity<Void> updateClass(Long id, TeamRequestDTO teamRequest);

    ResponseEntity<Void> startClass(Long id);

    ResponseEntity<Void> finishClass(Long id);

    ResponseEntity<Void> createClassWithStudentsAndInternals(TeamRequestDTO teamRequest);

    ResponseEntity<Void> addInternalsByIds(Long id, IdsList internalsIds);

    ResponseEntity<Void> addStudentByIds(Long id, IdsList studentsIds);

    ResponseEntity<Void> removeInternalsByIds(Long id, IdsList internalsIds);

    ResponseEntity<Void> removeStudentByIds(Long id, IdsList studentsIds);
}
