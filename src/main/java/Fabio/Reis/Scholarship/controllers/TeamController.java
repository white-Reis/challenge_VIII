package Fabio.Reis.Scholarship.controllers;

import Fabio.Reis.Scholarship.model.Commons.IdsList;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamRequestDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamsToStatus;
import Fabio.Reis.Scholarship.services.teamService.TeamServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/classes")
public class TeamController {
    private final TeamServiceImpl teamServiceImpl;

    TeamController(TeamServiceImpl teamServiceImpl) {
        this.teamServiceImpl = teamServiceImpl;
    }

    @GetMapping
    ResponseEntity<TeamsToStatus> getAllClasses() {
        return teamServiceImpl.getClasses();
    }

    @GetMapping("{id}")
    ResponseEntity getClassById(@PathVariable Long id) {
        return teamServiceImpl.getClassById(id);
    }

    @GetMapping("/{id}/instructors")
    ResponseEntity<List<InternalDTO>> getClassInstructors(@PathVariable Long id) {
        return teamServiceImpl.getClassInstructors(id);
    }

    @GetMapping("/{id}/scrum-masters")
    ResponseEntity<List<InternalDTO>> getScrumMasters(@PathVariable Long id) {
        return teamServiceImpl.getScrumMasters(id);
    }

    @GetMapping("/{id}/coordinators")
    ResponseEntity<List<InternalDTO>> getCoordinators(@PathVariable Long id) {
        return teamServiceImpl.getCoordinators(id);
    }

    @GetMapping("/{id}/students")
    ResponseEntity<List<StudentDTO>> getStudents(@PathVariable Long id) {
        return teamServiceImpl.getStudents(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> updateClass(@PathVariable Long id, @RequestBody TeamRequestDTO teamRequest) {
        return teamServiceImpl.updateClass(id, teamRequest);
    }

    @PutMapping("/{id}/internals")
    ResponseEntity<Void> addInternals(@PathVariable Long id, @RequestBody IdsList internalsIds) {
        return teamServiceImpl.addInternalsByIds(id, internalsIds);
    }

    @PutMapping("/{id}/students")
    ResponseEntity<Void> addStudents(@PathVariable Long id, @RequestBody IdsList internalsIds) {
        return teamServiceImpl.addStudentByIds(id, internalsIds);
    }
    @DeleteMapping("/{id}/internals")
    ResponseEntity<Void> removeInternals(@PathVariable Long id, @RequestBody IdsList internalsIds) {
        return teamServiceImpl.removeInternalsByIds(id, internalsIds);
    }

    @DeleteMapping("/{id}/students")
    ResponseEntity<Void> removeStudents(@PathVariable Long id, @RequestBody IdsList internalsIds) {
        return teamServiceImpl.removeStudentByIds(id, internalsIds);
    }

    @PutMapping("/{id}/start")
    ResponseEntity<Void> startClass(@PathVariable Long id) {
        return teamServiceImpl.startClass(id);
    }

    @PutMapping("/{id}/finish")
    ResponseEntity<Void> finishClass(@PathVariable Long id) {
        return teamServiceImpl.finishClass(id);
    }

    @PostMapping()
    ResponseEntity<Void> createTeamWithStudents(@RequestBody TeamRequestDTO teamRequest) {
        return teamServiceImpl.createClassWithStudentsAndInternals(teamRequest);
    }

}
