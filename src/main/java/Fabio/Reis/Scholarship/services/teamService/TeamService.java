package Fabio.Reis.Scholarship.services.teamService;

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
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeamService implements TeamService_i {

    private final TeamRepo teamRepo;
    private final StudentRepo studentRepo;
    private final InternalRepo internalRepo;

    private final SquadRepo squadRepo;
    private final ModelMapper modelMapper;

    public TeamService(TeamRepo teamRepo, StudentRepo studentRepo, InternalRepo internalRepo, ModelMapper modelMapper, SquadRepo squadRepo) {
        this.teamRepo = teamRepo;
        this.studentRepo = studentRepo;
        this.internalRepo = internalRepo;
        this.squadRepo = squadRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity getClassById(Long id) {
        Optional<Team> team = teamRepo.findById(id);
        if (team.isPresent()) {
            if (team.get().getStatus() != 0) {
                TeamStartedDTO teamStartedDTO = mapInternalsStartedDTO(team.get());
                return ResponseEntity.status(HttpStatus.FOUND).body(teamStartedDTO);
            } else {
                TeamDTO teamDTO = mapInternalsDTO(team.get());
                return ResponseEntity.status(HttpStatus.FOUND).body(teamDTO);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<List<TeamDTO>> getClasses() {
        List<Team> teams = teamRepo.findAll();
        List<TeamDTO> teamDTOs = new ArrayList<>();

        for (Team team : teams) {
            teamDTOs.add(mapInternalsDTO(team));
        }

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
            Team team = mapInternalsToTeam(teamOptional.get(), teamRequest);
            team.setName(teamRequest.getName());
            team.setLearning(teamRequest.getLearning());

            if (teamRequest.getStudents() != null) {
                addNewStudents(team, teamRequest.getStudents());
            }
            if (teamRequest.getInstructors() != null) {
                addNewInternals(team, teamRequest);
            }
            if (teamRequest.getCoordinators() != null) {
                addNewInternals(team, teamRequest);
            }
            if (teamRequest.getScrumMasters() != null) {
                addNewInternals(team, teamRequest);
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

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }


    private void mapAndSetStudents(Team team, List<StudentRequestDTO> studentsRequest) {
        Set<Student> students = new HashSet<>();

        for (StudentRequestDTO studentRequest : studentsRequest) {
            Student student = modelMapper.map(studentRequest, Student.class);
            student.getTeams().add(team);
            students.add(student);
        }

        team.setStudents(students);
    }

    private void mapAndSetInternals(Team team, List<InternalRequestDTO> internalsRequest, String role) {
        Set<Internal> internals = new HashSet<>();

        for (InternalRequestDTO internalRequest : internalsRequest) {
            Internal internal = modelMapper.map(internalRequest, Internal.class);
            internal.setRole(role);
            internals.add(internal);
        }
        Set<Internal> currentInternals = team.getInternals();
        internals.addAll(currentInternals);
        team.setInternals(internals);
    }

    private TeamDTO mapInternalsDTO(Team team) {
        TeamDTO teamDTO = new TeamDTO();

        teamDTO.setId(team.getId());
        teamDTO.setName(team.getName());
        teamDTO.setLearning(team.getLearning());
        teamDTO.setStatus(team.getStatus());

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

    private TeamStartedDTO mapInternalsStartedDTO(Team team) {
        TeamStartedDTO teamDTO = new TeamStartedDTO();

        teamDTO.setId(team.getId());
        teamDTO.setName(team.getName());
        teamDTO.setLearning(team.getLearning());
        teamDTO.setStatus(team.getStatus());

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


    private Team mapInternalsToTeam(Team team, TeamRequestDTO teamRequestDTO) {
        Set<Internal> internals = team.getInternals();

        if (teamRequestDTO.getCoordinators() != null) {
            for (InternalRequestDTO coordinatorRequest : teamRequestDTO.getCoordinators()) {
                Internal coordinator = modelMapper.map(coordinatorRequest, Internal.class);
                coordinator.setRole("Coordinator");
                internals.add(coordinator);
            }
        }

        if (teamRequestDTO.getScrumMasters() != null) {
            for (InternalRequestDTO scrumMasterRequest : teamRequestDTO.getScrumMasters()) {
                Internal scrumMaster = modelMapper.map(scrumMasterRequest, Internal.class);
                scrumMaster.setRole("Scrum Master");
                internals.add(scrumMaster);
            }
        }

        if (teamRequestDTO.getInstructors() != null) {
            for (InternalRequestDTO instructorRequest : teamRequestDTO.getInstructors()) {
                Internal instructor = modelMapper.map(instructorRequest, Internal.class);
                instructor.setRole("Instructor");
                internals.add(instructor);
            }
        }

        team.setInternals(internals);
        return team;
    }


    private void addNewStudents(Team team, List<StudentRequestDTO> studentsRequest) {
        for (StudentRequestDTO studentRequest : studentsRequest) {
            Student student = modelMapper.map(studentRequest, Student.class);
            student.getTeams().add(team);
            studentRepo.save(student);
        }
    }


    private void addNewInternals(Team team, TeamRequestDTO teamRequestDTO) {
        if (teamRequestDTO.getCoordinators() != null) {
            for (InternalRequestDTO internalRequest : teamRequestDTO.getCoordinators()) {
                Internal internal = modelMapper.map(internalRequest, Internal.class);
                internal.setRole("Coordinator");
                internal.setTeams(Collections.singleton(team));
                internalRepo.save(internal);
            }
        }

        if (teamRequestDTO.getScrumMasters() != null) {
            for (InternalRequestDTO internalRequest : teamRequestDTO.getScrumMasters()) {
                Internal internal = modelMapper.map(internalRequest, Internal.class);
                internal.setRole("Scrum Master");
                internal.setTeams(Collections.singleton(team));
                internalRepo.save(internal);
            }
        }

        if (teamRequestDTO.getInstructors() != null) {
            for (InternalRequestDTO internalRequest : teamRequestDTO.getInstructors()) {
                Internal internal = modelMapper.map(internalRequest, Internal.class);
                internal.setRole("Instructor");
                internal.setTeams(Collections.singleton(team));
                internalRepo.save(internal);
            }
        }
    }
}