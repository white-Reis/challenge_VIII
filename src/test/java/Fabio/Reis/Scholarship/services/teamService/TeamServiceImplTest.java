package Fabio.Reis.Scholarship.services.teamService;

import Fabio.Reis.Scholarship.exceptions.ClassRuleException;
import Fabio.Reis.Scholarship.exceptions.ObjectNotFoundException;
import Fabio.Reis.Scholarship.model.Commons.IdsList;
import Fabio.Reis.Scholarship.model.internalEntity.Internal;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalRequestDTO;
import Fabio.Reis.Scholarship.model.studentEntity.Student;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentRequestDTO;
import Fabio.Reis.Scholarship.model.teamEntity.Team;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamRequestDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamsToStatus;
import Fabio.Reis.Scholarship.repository.InternalRepo;
import Fabio.Reis.Scholarship.repository.SquadRepo;
import Fabio.Reis.Scholarship.repository.StudentRepo;
import Fabio.Reis.Scholarship.repository.TeamRepo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {

    @InjectMocks
    private TeamServiceImpl teamService;

    @Mock
    private TeamRepo teamRepo;
    @Mock
    private StudentRepo studentRepo;
    @Mock
    private InternalRepo internalRepo;
    @Mock
    private SquadRepo squad;

    @Spy
    private Validator validator;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void testGetClassById_TeamFoundAndNotStarted() {

        Team team = new Team();
        when(teamRepo.findById(anyLong())).thenReturn(Optional.of(team));

        ResponseEntity<?> response = teamService.getClassById(1L);


    }


    @Test
    void testGetClassById_TeamNotFound() {

        when(teamRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> teamService.getClassById(1L));
    }

    @Test
    void testGetClasses() {

        List<Team> teams = new ArrayList<>();

        when(teamRepo.findAll()).thenReturn(teams);

        ResponseEntity<TeamsToStatus> actualResponse = teamService.getClasses();

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

    }

    @Test
    void updateClassNotFound() {
        Long teamId = 1L;
        TeamRequestDTO updatedTeamDTO = new TeamRequestDTO();

        when(teamRepo.findById(teamId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> {
            teamService.updateClass(teamId, updatedTeamDTO);
        });
    }

    @Test
    void testStartClass_Success() {

        Team team = new Team();
        for (int i = 0;i < 20 ; i++){
            Student student = new Student();
            team.getStudents().add(student);
        }for (int i = 0;i < 3 ; i++){
            Internal internal = new Internal();
        internal.setRole("instructor");
            team.getInternals().add(internal);
        }
        Internal internal = new Internal();
        internal.setRole("Scrum Master");
        team.getInternals().add(internal);
        Internal internal2 = new Internal();
        internal2.setRole("coordinator");
        team.getInternals().add(internal2);



        when(teamRepo.findById(anyLong())).thenReturn(Optional.of(team));

        ResponseEntity<Void> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(null);


        ResponseEntity<Void> actualResponse = teamService.startClass(1L);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }

    @Test
    void testStartClass_AlreadyStarted() {

        Team team = new Team();
        team.setStatus(1);
        when(teamRepo.findById(anyLong())).thenReturn(Optional.of(team));

        assertThrows(ClassRuleException.class, () -> teamService.startClass(1L));
    }

    @Test
    void finishClass() {

        Team team = new Team();
        for (int i = 0;i < 20 ; i++){
            Student student = new Student();
            team.getStudents().add(student);
        }for (int i = 0;i < 3 ; i++){
            Internal internal = new Internal();
            internal.setRole("instructor");
            team.getInternals().add(internal);
        }
        Internal internal = new Internal();
        internal.setRole("Scrum Master");
        team.getInternals().add(internal);
        Internal internal2 = new Internal();
        internal2.setRole("coordinator");
        team.getInternals().add(internal2);



        when(teamRepo.findById(anyLong())).thenReturn(Optional.of(team));

        ResponseEntity<Void> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(null);

        teamService.startClass(1L);
        ResponseEntity<Void> actualResponse = teamService.finishClass(1L);


        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());

    }

    @Test
    void testAddInternalsByIds_Success() {

        Team team = new Team();
        when(teamRepo.findById(anyLong())).thenReturn(Optional.of(team));

        IdsList internalsIds = new IdsList();
        ResponseEntity<Void> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(null);


        ResponseEntity<Void> actualResponse = teamService.addInternalsByIds(1L, internalsIds);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }

    @Test
    void testAddInternalsByIds_TeamNotFound() {

        when(teamRepo.findById(anyLong())).thenReturn(Optional.empty());

        IdsList internalsIds = new IdsList();

        assertThrows(ObjectNotFoundException.class, () -> teamService.addInternalsByIds(1L, internalsIds));
    }

    @Test
    void testAddStudentsByIds_Success() {

        Team team = new Team();
        when(teamRepo.findById(anyLong())).thenReturn(Optional.of(team));

        IdsList studentsIds = new IdsList();
        ResponseEntity<Void> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(null);


        ResponseEntity<Void> actualResponse = teamService.addStudentByIds(1L, studentsIds);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }
    @Test
    void testAddStudentsByIds_TeamNotFound() {

        when(teamRepo.findById(anyLong())).thenReturn(Optional.empty());

        IdsList studentsIds = new IdsList();

        assertThrows(ObjectNotFoundException.class, () -> teamService.addStudentByIds(1L, studentsIds));
    }

    @Test
    void validTeam() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        TeamRequestDTO validTeamRequest = new TeamRequestDTO();


        Set<ConstraintViolation<TeamRequestDTO>> violations = validator.validate(validTeamRequest);
        assertEquals(2, violations.size());
    }

    @Test
    void validInternal() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        InternalRequestDTO validInternalRequest = new InternalRequestDTO();


        Set<ConstraintViolation<InternalRequestDTO>> violations = validator.validate(validInternalRequest);
        assertEquals(5, violations.size());
    }

    @Test
    void validStudent() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        StudentRequestDTO validStudentRequest = new StudentRequestDTO();


        Set<ConstraintViolation<StudentRequestDTO>> violations = validator.validate(validStudentRequest);
        assertEquals(5, violations.size());
    }
}