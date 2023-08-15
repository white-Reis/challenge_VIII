package Fabio.Reis.Scholarship.services.teamService;

import Fabio.Reis.Scholarship.exceptions.ClassRuleException;
import Fabio.Reis.Scholarship.exceptions.DataIntegratyViolationException;
import Fabio.Reis.Scholarship.exceptions.ObjectNotFoundException;
import Fabio.Reis.Scholarship.model.Commons.IdsList;
import Fabio.Reis.Scholarship.model.internalEntity.Internal;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalRequestDTO;
import Fabio.Reis.Scholarship.model.squadEntity.Squad;
import Fabio.Reis.Scholarship.model.squadEntity.squadDTO.SquadDTO;
import Fabio.Reis.Scholarship.model.studentEntity.Student;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentRequestDTO;
import Fabio.Reis.Scholarship.model.teamEntity.Team;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamRequestDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamStartedDTO;
import Fabio.Reis.Scholarship.repository.InternalRepo;
import Fabio.Reis.Scholarship.repository.SquadRepo;
import Fabio.Reis.Scholarship.repository.StudentRepo;
import Fabio.Reis.Scholarship.repository.TeamRepo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepo teamRepo;
    private final StudentRepo studentRepo;
    private final InternalRepo internalRepo;
    private final SquadRepo squadRepo;
    private final ModelMapper modelMapper;

    private final Validator validator;

    public TeamServiceImpl(TeamRepo teamRepo, StudentRepo studentRepo, InternalRepo internalRepo, ModelMapper modelMapper, SquadRepo squadRepo, Validator validator) {
        this.teamRepo = teamRepo;
        this.studentRepo = studentRepo;
        this.internalRepo = internalRepo;
        this.squadRepo = squadRepo;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public ResponseEntity<?> getClassById(Long id) {
        Optional<Team> team = teamRepo.findById(id);
        if (team.isPresent()) {
            if (team.get().getStatus() != 0) {
                TeamStartedDTO teamStartedDTO = mapInternalsStartedTeamDTO(team.get());
                return ResponseEntity.status(HttpStatus.FOUND).body(teamStartedDTO);
            } else {
                TeamDTO teamDTO = mapInternalsTeamDTO(team.get());
                return ResponseEntity.status(HttpStatus.FOUND).body(teamDTO);
            }
        } else {
            throw new ObjectNotFoundException("Class not found");
        }
    }

    @Override
    public ResponseEntity<List<TeamDTO>> getClasses() {
        List<Team> teams = teamRepo.findAll();
        List<TeamDTO> teamDTOs = new ArrayList<>();

        for (Team team : teams) {
            teamDTOs.add(mapInternalsTeamDTO(team));
        }

        return ResponseEntity.status(HttpStatus.OK).body(teamDTOs);
    }

    @Override
    public ResponseEntity<List<InternalDTO>> getClassInstructors(Long id) {
        Optional<Team> teamOptional = teamRepo.findById(id);

        if (teamOptional.isPresent()) {
            TeamDTO teamDTO = mapInternalsTeamDTO(teamOptional.get());
            List<InternalDTO> instructorsDTO = new ArrayList<>(teamDTO.getInstructors());
            return ResponseEntity.status(HttpStatus.OK).body(instructorsDTO);
        } else {
            throw new ObjectNotFoundException("Class not found");
        }
    }


    @Override
    public ResponseEntity<List<InternalDTO>> getScrumMasters(Long id) {
        Optional<Team> teamOptional = teamRepo.findById(id);

        if (teamOptional.isPresent()) {
            TeamDTO teamDTO = mapInternalsTeamDTO(teamOptional.get());
            List<InternalDTO> scrumMastersDTO = new ArrayList<>(teamDTO.getScrumMasters());
            return ResponseEntity.status(HttpStatus.OK).body(scrumMastersDTO);
        } else {
            throw new ObjectNotFoundException("Class not found");
        }
    }

    @Override
    public ResponseEntity<List<InternalDTO>> getCoordinators(Long id) {
        Optional<Team> teamOptional = teamRepo.findById(id);

        if (teamOptional.isPresent()) {
            TeamDTO teamDTO = mapInternalsTeamDTO(teamOptional.get());
            List<InternalDTO> coordinatorsDTO = new ArrayList<>(teamDTO.getCoordinators());
            return ResponseEntity.status(HttpStatus.OK).body(coordinatorsDTO);
        } else {
            throw new ObjectNotFoundException("Class not found");
        }
    }

    @Override
    public ResponseEntity<List<StudentDTO>> getStudents(Long id) {
        Optional<Team> teamOptional = teamRepo.findById(id);

        if (teamOptional.isPresent()) {
            TeamDTO teamDTO = mapInternalsTeamDTO(teamOptional.get());
            List<StudentDTO> studentsDTO = new ArrayList<>(teamDTO.getStudents());
            return ResponseEntity.status(HttpStatus.OK).body(studentsDTO);
        } else {
            throw new ObjectNotFoundException("Class not found");
        }
    }


    @Override
    public ResponseEntity<Void> updateClass(Long id, TeamRequestDTO teamRequest) {
        Optional<Team> teamOptional = teamRepo.findById(id);
        if (teamOptional.isPresent()) {
            if (teamOptional.get().getStatus() != 0) {
                throw new ClassRuleException("Class already Started");
            }
            if (teamRequest.getName() == null) {
                teamRequest.setName(teamOptional.get().getName());
            }
            if (teamRequest.getLearning() == null) {
                teamRequest.setLearning(teamOptional.get().getLearning());
            }
            validTeam(teamRequest, validator);

            Team team = teamOptional.get();
            mapTeamRequestDTOToTeam(team, teamRequest);

            teamRepo.save(team);

            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            throw new ObjectNotFoundException("Class not found");
        }
    }


    @Override
    public ResponseEntity<Void> startClass(Long id) {
        Optional<Team> teamOptional = teamRepo.findById(id);
        if (teamOptional.isPresent()) {
            TeamDTO teamDTO = mapInternalsTeamDTO(teamOptional.get());
            if (teamOptional.get().getStatus() != 0) {
                throw new ClassRuleException("Class already Started");
            }
            if (teamDTO.getStudents().size() < 15) {
                throw new ClassRuleException("Class must have at least fifteen students");
            }
            if (teamDTO.getStudents().size() > 30) {
                throw new ClassRuleException("The class must have a maximum of thirty students");
            }
            if (teamDTO.getCoordinators().size() < 1 || teamDTO.getScrumMasters().size() < 1 || teamDTO.getInstructors().size() < 3) {
                throw new ClassRuleException("The class must have at least one scrum master, one coordinator, and three instructors | Total of Coordinators: " + teamDTO.getCoordinators().size() + " | Total of Scrum Masters: " + teamDTO.getScrumMasters().size() + " | Total of Instructors: " + teamDTO.getInstructors().size());
            }
            Team team = teamOptional.get();
            team.setStatus(1);
            team.setStartDate(LocalDate.now());

            List<Student> students = new ArrayList<>(team.getStudents());
            students.sort(Comparator.comparingInt(Student::getLevel));
            if (students.size() == 15) {
                List<Squad> squadList = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    Squad squad = new Squad();
                    squad.setName("Squad-" + i);
                    squadRepo.save(squad);
                    squadList.add(squad);
                }
                int y = 0;
                for (Student student : students) {
                    student.addSquad(squadList.get(y));
                    squadList.get(y).getStudents().add(student);
                    squadList.get(y).setTeam(team);
                    if (y == 2) {
                        y = 0;
                    } else {
                        y++;
                    }
                }
                for (int i = 0; i < 3; i++) {
                    squadRepo.save(squadList.get(i));
                }
            } else if (students.size() > 15 && students.size() <= 20) {
                List<Squad> squadList = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    Squad squad = new Squad();
                    squad.setName("Squad-" + i);
                    squadRepo.save(squad);
                    squadList.add(squad);
                }
                int y = 0;
                for (Student student : students) {
                    student.addSquad(squadList.get(y));
                    squadList.get(y).getStudents().add(student);
                    squadList.get(y).setTeam(team);
                    if (y == 4) {
                        y = 0;
                    } else {
                        y++;
                    }
                }
                for (int i = 0; i < 4; i++) {
                    squadRepo.save(squadList.get(i));
                }
            } else if (students.size() >= 20 && students.size() < 25) {
                List<Squad> squadList = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    Squad squad = new Squad();
                    squad.setName("Squad-" + i);
                    squadRepo.save(squad);
                    squadList.add(squad);
                }
                int y = 0;
                for (Student student : students) {
                    student.addSquad(squadList.get(y));
                    squadList.get(y).getStudents().add(student);
                    squadList.get(y).setTeam(team);
                    squadRepo.save(squadList.get(y));
                    if (y == 4) {
                        y = 0;
                    } else {
                        y++;
                    }
                }
                for (int i = 0; i < 5; i++) {
                    squadRepo.save(squadList.get(i));
                }
            } else {
                List<Squad> squadList = new ArrayList<>();
                for (int i = 0; i < 6; i++) {
                    Squad squad = new Squad();
                    squad.setName("Squad-" + i);
                    squadRepo.save(squad);
                    squadList.add(squad);
                }
                int y = 0;
                for (Student student : students) {
                    student.addSquad(squadList.get(y));
                    squadList.get(y).getStudents().add(student);
                    squadList.get(y).setTeam(team);
                    squadRepo.save(squadList.get(y));
                    if (y == 5) {
                        y = 0;
                    } else {
                        y++;
                    }
                }
                for (int i = 0; i < 6; i++) {
                    squadRepo.save(squadList.get(i));
                }
            }
            teamRepo.save(team);

            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            throw new ObjectNotFoundException("Class not found");
        }
    }

    @Override
    public ResponseEntity<Void> finishClass(Long id) {
        Optional<Team> teamOptional = teamRepo.findById(id);

        if (teamOptional.isPresent()) {

            if (teamOptional.get().getStatus() == 0) {
                throw new ClassRuleException("class needs to be started");
            }
            if (teamOptional.get().getStatus() == 2) {
                throw new ClassRuleException("class already finished");
            }
            Team team = teamOptional.get();
            team.setStatus(2);
            team.setEndDate(LocalDate.now());

            teamRepo.save(team);

            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            throw new ObjectNotFoundException("Class not found");
        }
    }


    @Override
    public ResponseEntity<Void> createClassWithStudentsAndInternals(TeamRequestDTO teamRequest) {
        validTeam(teamRequest, validator);
        for (InternalRequestDTO internal: teamRequest.getInternals()){
            validInternal(internal,validator);
        }for (StudentRequestDTO student: teamRequest.getStudents()){
            validStudent(student,validator);
        }
        if (teamRequest.getStudents().size() > 30) {
            throw new ClassRuleException("The class must have a maximum of thirty students");
        }

        Team teamEntity = modelMapper.map(teamRequest, Team.class);
        teamEntity.setStatus(0);

        try {
            Team createdTeam = teamRepo.save(teamEntity);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdTeam.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegratyViolationException("Repeated or already registered emails");
        }
    }


    public ResponseEntity<Void> addInternalsByIds(Long id, IdsList internalsIds) {
        Optional<Team> team = teamRepo.findById(id);
        if (team.isPresent()) {
            for (Long internalId : internalsIds.getIds()) {
                Optional<Internal> internal = internalRepo.findById(internalId);
                if (internal.isPresent()) {
                    internal.get().getTeams().add(team.get());
                    team.get().getInternals().add(internal.get());
                    internalRepo.save(internal.get());
                } else {
                    throw new ObjectNotFoundException("Internal not found for id: " + internalId);
                }
            }
            teamRepo.save(team.get());
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            throw new ObjectNotFoundException("Class not found");
        }
    }

    @Override
    public ResponseEntity<Void> addStudentByIds(Long id, IdsList studentsIds) {
        Optional<Team> team = teamRepo.findById(id);
        if (team.isPresent()) {
                if (team.get().getStatus() != 0) {
                    throw new ClassRuleException("Class already Started");
                }
            for (Long studentId : studentsIds.getIds()) {
                Optional<Student> student = studentRepo.findById(studentId);
                if (student.isPresent()) {
                    student.get().getTeams().add(team.get());
                    team.get().getStudents().add(student.get());
                    studentRepo.save(student.get());
                } else {
                    throw new ObjectNotFoundException("Student not found for id: " + studentId);
                }
            }
            teamRepo.save(team.get());
            return ResponseEntity.status(HttpStatus.OK).body(null);

        } else {
            throw new ObjectNotFoundException("Class not found");
        }
    }


    private TeamDTO mapInternalsTeamDTO(Team team) {
        TeamDTO teamDTO = new TeamDTO();

        teamDTO.setId(team.getId());
        teamDTO.setName(team.getName());
        teamDTO.setLearning(team.getLearning());
        teamDTO.setStatus(getStatus(team.getStatus()));

        Set<InternalDTO> coordinatorDTOs = team.getInternals().stream()
                .filter(internal -> "Coordinator".equalsIgnoreCase(internal.getRole()))
                .map(internal -> modelMapper.map(internal, InternalDTO.class))
                .collect(Collectors.toSet());
        teamDTO.setCoordinators(coordinatorDTOs);


        Set<InternalDTO> scrumMasterDTOs = team.getInternals().stream()
                .filter(internal -> "Scrum Master".equalsIgnoreCase(internal.getRole()))
                .map(internal -> modelMapper.map(internal, InternalDTO.class))
                .collect(Collectors.toSet());
        teamDTO.setScrumMasters(scrumMasterDTOs);


        Set<InternalDTO> instructorDTOs = team.getInternals().stream()
                .filter(internal -> "Instructor".equalsIgnoreCase(internal.getRole()))
                .map(internal -> modelMapper.map(internal, InternalDTO.class))
                .collect(Collectors.toSet());
        teamDTO.setInstructors(instructorDTOs);


        Set<StudentDTO> studentDTOs = team.getStudents().stream()
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .collect(Collectors.toSet());
        teamDTO.setStudents(studentDTOs);

        return teamDTO;
    }

    private TeamStartedDTO mapInternalsStartedTeamDTO(Team team) {
        TeamStartedDTO teamDTO = new TeamStartedDTO();

        teamDTO.setId(team.getId());
        teamDTO.setName(team.getName());
        teamDTO.setLearning(team.getLearning());
        teamDTO.setStatus(getStatus(team.getStatus()));
        teamDTO.setStartDate(team.getStartDate());
        teamDTO.setEndDate(team.getEndDate());

        Set<InternalDTO> coordinatorDTOs = team.getInternals().stream()
                .filter(internal -> "Coordinator".equalsIgnoreCase(internal.getRole()))
                .map(internal -> modelMapper.map(internal, InternalDTO.class))
                .collect(Collectors.toSet());
        teamDTO.setCoordinators(coordinatorDTOs);


        Set<InternalDTO> scrumMasterDTOs = team.getInternals().stream()
                .filter(internal -> "Scrum Master".equalsIgnoreCase(internal.getRole()))
                .map(internal -> modelMapper.map(internal, InternalDTO.class))
                .collect(Collectors.toSet());
        teamDTO.setScrumMasters(scrumMasterDTOs);


        Set<InternalDTO> instructorDTOs = team.getInternals().stream()
                .filter(internal -> "Instructor".equalsIgnoreCase(internal.getRole()))
                .map(internal -> modelMapper.map(internal, InternalDTO.class))
                .collect(Collectors.toSet());
        teamDTO.setInstructors(instructorDTOs);


        Set<SquadDTO> squadDTOs = team.getSquads().stream()
                .map(squad -> modelMapper.map(squad, SquadDTO.class))
                .collect(Collectors.toSet());
        teamDTO.setSquads(squadDTOs);

        return teamDTO;
    }


    private void mapTeamRequestDTOToTeam(Team team, TeamRequestDTO teamRequestDTO) {
        Set<Internal> internals = team.getInternals();
        Set<Student> students = team.getStudents();

        internals.addAll(getInternalsFromRequest(teamRequestDTO.getInternals(), team));
        students.addAll(getStudentFromRequest(teamRequestDTO.getStudents(), team));

        team.setInternals(internals);
    }

    private Set<Student> getStudentFromRequest(List<StudentRequestDTO> studentRequests, Team team) {
        return studentRequests.stream()
                .peek(studentRequest -> validStudent(studentRequest, validator))
                .map(studentRequest -> {
                    Student student = modelMapper.map(studentRequest, Student.class);
                    student.setTeams(Collections.singleton(team));
                    studentRepo.save(student);
                    return student;
                })
                .collect(Collectors.toSet());
    }

    private Set<Internal> getInternalsFromRequest(List<InternalRequestDTO> internalRequests, Team team) {
        return internalRequests.stream()
                .peek(internalRequest -> validInternal(internalRequest, validator))
                .map(internalRequest -> {
                    Internal internal = modelMapper.map(internalRequest, Internal.class);
                    internal.setRole(internalRequest.getRole());
                    internal.setTeams(Collections.singleton(team));
                    internalRepo.save(internal);
                    return internal;
                })
                .collect(Collectors.toSet());
    }

    private String getStatus(int status) {
        return switch (status) {
            case 0 -> "Waiting";
            case 1 -> "Started";
            case 2 -> "Finished";
            default -> "Unknown";
        };
    }

    public static void validTeam(TeamRequestDTO teamRequest, Validator validator) {
        Set<ConstraintViolation<TeamRequestDTO>> violations = validator.validate(teamRequest);
        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<TeamRequestDTO> violation : violations) {
                errorMessages.add(violation.getPropertyPath() + ": " + violation.getMessage());
            }
            throw new DataIntegratyViolationException(errorMessages.toString());
        }
    }

    public static void validInternal(InternalRequestDTO internalRequest, Validator validator) {
        Set<ConstraintViolation<InternalRequestDTO>> violations = validator.validate(internalRequest);
        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<InternalRequestDTO> violation : violations) {
                errorMessages.add(violation.getPropertyPath() + ": " + violation.getMessage());
            }
            throw new DataIntegratyViolationException(errorMessages.toString());
        }
    }

    public static void validStudent(StudentRequestDTO studentRequest, Validator validator) {
        Set<ConstraintViolation<StudentRequestDTO>> violations = validator.validate(studentRequest);
        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<StudentRequestDTO> violation : violations) {
                errorMessages.add(violation.getPropertyPath() + ": " + violation.getMessage());
            }
            throw new DataIntegratyViolationException(errorMessages.toString());
        }
    }
}