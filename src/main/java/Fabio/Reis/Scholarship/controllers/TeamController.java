package Fabio.Reis.Scholarship.controllers;

import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamRequestDTO;
import Fabio.Reis.Scholarship.services.teamService.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/classes")
public class TeamController {
    private final TeamService teamService;

    TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    ResponseEntity<List<TeamDTO>> getAllClasses() {
        return teamService.getClasses();
    }

    @GetMapping("{id}")
    ResponseEntity getClassById(@PathVariable Long id) {
        return teamService.getClassById(id);
    }

    @GetMapping("/{id}/instructors")
    ResponseEntity<List<InternalDTO>> getClassInstructors(@PathVariable Long id) {
        return teamService.getClassInstructors(id);
    }

    @GetMapping("/{id}/scrum-masters")
    ResponseEntity<List<InternalDTO>> getScrumMasters(@PathVariable Long id) {
        return teamService.getScrumMasters(id);
    }

    @GetMapping("/{id}/coordinators")
    ResponseEntity<List<InternalDTO>> getCoordinators(@PathVariable Long id) {
        return teamService.getCoordinators(id);
    }

    @GetMapping("/{id}/students")
    ResponseEntity<List<StudentDTO>> getStudents(@PathVariable Long id) {
        return teamService.getStudents(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> updateClass(@PathVariable Long id, @RequestBody TeamRequestDTO teamRequest) {
        return teamService.updateClass(id, teamRequest);
    }

    @PutMapping("/{id}/start")
    ResponseEntity<Void> startClass(@PathVariable Long id) {
        return teamService.startClass(id);
    }

    @PutMapping("/{id}/finish")
    ResponseEntity<Void> finishClass(@PathVariable Long id) {
        return teamService.finishClass(id);
    }

    @PostMapping()
    ResponseEntity<Void> createTeamWithStudents(@RequestBody TeamRequestDTO teamRequest) {
        return teamService.createClassWithStudentsAndInternals(teamRequest);
    }

}
