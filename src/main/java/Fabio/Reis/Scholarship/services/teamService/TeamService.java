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

import java.time.LocalDate;
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
        List<Team> teams = teamRepo.findAll();
        List<TeamDTO> teamDTOs = teams.stream()
                .map(this::mapInternalsDTO)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(teamDTOs);
    }

    @Override
    public ResponseEntity<List<InternalDTO>> getClassInstructors(Long id) {
        Optional<Team> teamOptional = teamRepo.findById(id);

        if (teamOptional.isPresent()) {
            TeamDTO teamDTO = mapInternalsDTO(teamOptional.get());
            List<InternalDTO> instructorsDTO = new ArrayList<>(teamDTO.getInstructors());
            return ResponseEntity.status(HttpStatus.OK).body(instructorsDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    public ResponseEntity<List<InternalDTO>> getScrumMasters(Long id) {
        Optional<Team> teamOptional = teamRepo.findById(id);

        if (teamOptional.isPresent()) {
            TeamDTO teamDTO = mapInternalsDTO(teamOptional.get());
            List<InternalDTO> scrumMastersDTO = new ArrayList<>(teamDTO.getScrumMasters());
            return ResponseEntity.status(HttpStatus.OK).body(scrumMastersDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<List<InternalDTO>> getCoordinators(Long id) {
        Optional<Team> teamOptional = teamRepo.findById(id);

        if (teamOptional.isPresent()) {
            TeamDTO teamDTO = mapInternalsDTO(teamOptional.get());
            List<InternalDTO> coordinatorsDTO = new ArrayList<>(teamDTO.getCoordinators());
            return ResponseEntity.status(HttpStatus.OK).body(coordinatorsDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<List<StudentDTO>> getStudents(Long id) {
        Optional<Team> teamOptional = teamRepo.findById(id);

        if (teamOptional.isPresent()) {
            TeamDTO teamDTO = mapInternalsDTO(teamOptional.get());
            List<StudentDTO> studentsDTO = new ArrayList<>(teamDTO.getStudents());
            return ResponseEntity.status(HttpStatus.OK).body(studentsDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    public ResponseEntity<Void> updateClass(Long id, TeamRequestDTO teamRequest) {
        Optional<Team> teamOptional = teamRepo.findById(id);

        if (teamOptional.isPresent()) {
            Team team = mapInternalsToTeam(teamOptional.get());
            team.setName(teamRequest.getName());
            team.setLearning(team.getLearning());
            if (teamRequest.getStudents() != null) {
                addNewStudents(team, teamRequest.getStudents());
            }

            if (teamRequest.getInstructors() != null) {
                addNewInternals(team, teamRequest.getInstructors(), "Instructor");
            }
            if (teamRequest.getCoordinators() != null) {
                addNewInternals(team, teamRequest.getCoordinators(), "Coordinator");
            }
            if (teamRequest.getScrumMasters() != null) {
                addNewInternals(team, teamRequest.getScrumMasters(), "Scrum Master");
            }

            teamRepo.save(team);

            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @Override
    public ResponseEntity<Void> startClass(Long id) {
        Optional<Team> teamOptional = teamRepo.findById(id);

        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();
            team.setStatus(1);
            team.setStartDate(LocalDate.now());

            teamRepo.save(team);

            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Void> finishClass(Long id) {
        Optional<Team> teamOptional = teamRepo.findById(id);

        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();
            team.setStatus(2);
            team.setEndDate(LocalDate.now());

            teamRepo.save(team);

            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.notFound().build();
        }
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

        switch (role.toLowerCase()) {
            case "coordinator":
                team.setCoordinators(members);
                break;
            case "scrum master":
                team.setScrumMasters(members);
                break;
            case "instructor":
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
            if (memberRequest.getRole().toLowerCase().equals("coordinator")) {
                coordinatorDTOs.add(modelMapper.map(memberRequest, InternalDTO.class));
            }
        }

        for (Internal memberRequest : team.getScrumMasters()) {
            if (memberRequest.getRole().toLowerCase().equals("scrum master")) {
                scrumMasterDTOs.add(modelMapper.map(memberRequest, InternalDTO.class));
            }
        }

        for (Internal memberRequest : team.getInstructors()) {
            if (memberRequest.getRole().toLowerCase().equals("instructor")) {
                instructorDTOs.add(modelMapper.map(memberRequest, InternalDTO.class));
            }
        }

        teamDTO.setCoordinators(coordinatorDTOs);
        teamDTO.setScrumMasters(scrumMasterDTOs);
        teamDTO.setInstructors(instructorDTOs);
        teamDTO.setStudents(team.getStudents().stream().map(student -> modelMapper.map(student, StudentDTO.class)).collect(Collectors.toList()));

        return teamDTO;
    }

    private Team mapInternalsToTeam(Team team) {
        List<Internal> coordinators = new ArrayList<>();
        List<Internal> scrumMasters = new ArrayList<>();
        List<Internal> instructors = new ArrayList<>();

        for (Internal member : team.getCoordinators()) {
            if (member.getRole().equalsIgnoreCase("Coordinator")) {
                coordinators.add(member);
            }
        }

        for (Internal member : team.getScrumMasters()) {
            if (member.getRole().equalsIgnoreCase("Scrum Master")) {
                scrumMasters.add(member);
            }
        }

        for (Internal member : team.getInstructors()) {
            if (member.getRole().equalsIgnoreCase("Instructor")) {
                instructors.add(member);
            }
        }

        team.setCoordinators(coordinators);
        team.setScrumMasters(scrumMasters);
        team.setInstructors(instructors);

        return team;
    }


    private void addNewStudents(Team team, List<StudentRequestDTO> studentsRequest) {
        for (StudentRequestDTO studentRequest : studentsRequest) {
            Student student = modelMapper.map(studentRequest, Student.class);
            student.setTeam(team);
            studentRepo.save(student);
        }
    }


    private void addNewInternals(Team team, List<InternalRequestDTO> membersRequest, String role) {
        List<Internal> membersToAdd = new ArrayList<>();
        for (InternalRequestDTO memberRequest : membersRequest) {
            Internal member = modelMapper.map(memberRequest, Internal.class);
            member.setRole(role);
            member.setTeam(team);
            membersToAdd.add(member);
            internalRepo.save(member);
        }
    }
}