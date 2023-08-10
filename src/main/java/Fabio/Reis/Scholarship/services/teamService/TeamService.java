package Fabio.Reis.Scholarship.services.teamService;

import Fabio.Reis.Scholarship.model.internalEntity.Internal;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalRequestDTO;
import Fabio.Reis.Scholarship.model.studentEntity.Student;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentRequestDTO;
import Fabio.Reis.Scholarship.model.teamEntity.Team;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamRequestDTO;
import Fabio.Reis.Scholarship.repository.InternalRepo;
import Fabio.Reis.Scholarship.repository.StudentRepo;
import Fabio.Reis.Scholarship.repository.TeamRepo;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService implements TeamService_i {

    private final TeamRepo teamRepo;
    private final StudentRepo studentRepo;
    private final InternalRepo internalRepo;
    private final ModelMapper modelMapper;

    public TeamService(TeamRepo teamRepo, StudentRepo studentRepo, InternalRepo internalRepo, ModelMapper modelMapper) {
        this.teamRepo = teamRepo;
        this.studentRepo = studentRepo;
        this.internalRepo = internalRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<TeamDTO> getClassById(Long id) {
        Optional<Team> team = teamRepo.findById(id);
        if (team.isPresent()) {
            TeamDTO teamDTO = mapInternalsDTO(team.get());
            return ResponseEntity.status(HttpStatus.FOUND).body(teamDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    public ResponseEntity<List<TeamDTO>> getClasses() {
        return null;
    }

    @Override
    public ResponseEntity<Void> createClass(TeamRequestDTO teamRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateClass(TeamRequestDTO teamRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> startClass(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> createClassWithStudentsAndInternals(TeamRequestDTO teamRequest) {
        Team teamEntity = modelMapper.map(teamRequest, Team.class);
        teamEntity.setStatus(0);

        mapAndSetStudents(teamEntity, teamRequest.getStudents());
        mapAndSetInternals(teamEntity, teamRequest.getCoordinators(), "Coordinator");
        mapAndSetInternals(teamEntity, teamRequest.getScrumMasters(), "Scrum Master");
        mapAndSetInternals(teamEntity, teamRequest.getInstructors(), "Instructor");
        teamRepo.save(teamEntity);
        ;
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }


    private void mapAndSetStudents(Team team, List<StudentRequestDTO> studentsRequest) {
        List<Student> students = new ArrayList<>();

        for (StudentRequestDTO studentRequest : studentsRequest) {
            Student student = modelMapper.map(studentRequest, Student.class);
            student.setTeam(team);
            students.add(student);
        }

        team.setStudents(students);
    }

    private void mapAndSetInternals(Team team, List<InternalRequestDTO> membersRequest, String role) {
        List<Internal> members = new ArrayList<>();

        for (InternalRequestDTO memberRequest : membersRequest) {
            Internal member = modelMapper.map(memberRequest, Internal.class);
            member.setRole(role);
            member.setTeam(team);
            members.add(member);
        }

        switch (role) {
            case "Coordinator":
                team.setCoordinators(members);
                break;
            case "Scrum Master":
                team.setScrumMasters(members);
                break;
            case "Instructor":
                team.setInstructors(members);
                break;
            default:
                // Handle unexpected role
                break;
        }
    }

    private TeamDTO mapInternalsDTO(Team team) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(team.getId());
        teamDTO.setName(team.getName());
        teamDTO.setLearning(team.getLearning());
        teamDTO.setStatus(team.getStatus());
        teamDTO.setStartDate(team.getStartDate());
        teamDTO.setEndDate(team.getEndDate());

        List<InternalDTO> coordinatorDTOs = new ArrayList<>();
        List<InternalDTO> scrumMasterDTOs = new ArrayList<>();
        List<InternalDTO> instructorDTOs = new ArrayList<>();

        for (Internal memberRequest : team.getCoordinators()) {
            if (memberRequest.getRole().equals("Coordinator")) {
                coordinatorDTOs.add(modelMapper.map(memberRequest, InternalDTO.class));
            }
        }

        for (Internal memberRequest : team.getScrumMasters()) {
            if (memberRequest.getRole().equals("Scrum Master")) {
                scrumMasterDTOs.add(modelMapper.map(memberRequest, InternalDTO.class));
            }
        }

        for (Internal memberRequest : team.getInstructors()) {
            if (memberRequest.getRole().equals("Instructor")) {
                instructorDTOs.add(modelMapper.map(memberRequest, InternalDTO.class));
            }
        }

        teamDTO.setCoordinators(coordinatorDTOs);
        teamDTO.setScrumMasters(scrumMasterDTOs);
        teamDTO.setInstructors(instructorDTOs);
        teamDTO.setStudents(team.getStudents().stream().map(student -> modelMapper.map(student, StudentDTO.class)).collect(Collectors.toList()));

        return teamDTO;
    }

}