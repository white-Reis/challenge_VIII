package Fabio.Reis.Scholarship.services.teamService.impl;

import Fabio.Reis.Scholarship.model.internalEntity.Internal;
import Fabio.Reis.Scholarship.model.studentEntity.Student;
import Fabio.Reis.Scholarship.model.teamEntity.Team;
import Fabio.Reis.Scholarship.repository.InternalRepo;
import Fabio.Reis.Scholarship.repository.StudentRepo;
import Fabio.Reis.Scholarship.repository.TeamRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    private TeamRepo teamRepo;
    private StudentRepo studentRepo;
    private InternalRepo internalRepo;

    public TeamService(TeamRepo teamRepo,StudentRepo studentRepo,InternalRepo internalRepo){
        this.teamRepo= teamRepo;
        this.studentRepo= studentRepo;
        this.internalRepo= internalRepo;
    }



    public Team createTeamWithStudents(Team teamRequest) {
        Team team = new Team();
        team.setName(teamRequest.getName());
        team.setStatus(0);

        List<Student> students = new ArrayList<>();
        for (Student studentRequest : teamRequest.getStudents()) {
            Student student = new Student();
            student.setName(studentRequest.getName());
            student.setLastName(studentRequest.getLastName());
            student.setEmail(studentRequest.getEmail());
            student.setTeam(team);
            students.add(student);
        }
        team.setStudents(students);

        List<Internal> coordinators = new ArrayList<>();
        for (Internal coordinatorRequest : teamRequest.getCoordinator()) {
            Internal coordinator = new Internal();
            coordinator.setName(coordinatorRequest.getName());
            coordinator.setLastName(coordinatorRequest.getLastName());
            coordinator.setEmail(coordinatorRequest.getEmail());
            coordinator.setRole("Coordinator");
            coordinator.setTeam(team);
            coordinators.add(coordinator);
        }
        team.setCoordinator(coordinators);

        List<Internal> scrumMasters = new ArrayList<>();
        for (Internal scrumMasterRequest : teamRequest.getScrumMaster()) {
            Internal scrumMaster = new Internal();
            scrumMaster.setName(scrumMasterRequest.getName());
            scrumMaster.setLastName(scrumMasterRequest.getLastName());
            scrumMaster.setEmail(scrumMasterRequest.getEmail());
            scrumMaster.setRole("Scrum Master");
            scrumMaster.setTeam(team);
            scrumMasters.add(scrumMaster);
        }
        team.setScrumMaster(scrumMasters);

        List<Internal> instructors = new ArrayList<>();
        for (Internal instructorRequest : teamRequest.getInstructors()) {
            Internal instructor = new Internal();
            instructor.setName(instructorRequest.getName());
            instructor.setLastName(instructorRequest.getLastName());
            instructor.setEmail(instructorRequest.getEmail());
            instructor.setRole("Instructor");
            instructor.setTeam(team);
            instructors.add(instructor);
        }
        team.setInstructors(instructors);

        return teamRepo.save(team);
    }



}
