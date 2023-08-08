package Fabio.Reis.Scholarship.controllers;


import Fabio.Reis.Scholarship.model.teamEntity.Team;
import Fabio.Reis.Scholarship.services.teamService.impl.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class TeamController {
    private TeamService teamService;
    public  TeamController(TeamService teamService){
        this.teamService = teamService;
    }
    @PostMapping
    public ResponseEntity<Team> createTeamWithStudents(@RequestBody Team teamRequest) {
        Team createdTeam = teamService.createTeamWithStudents(teamRequest);
        return ResponseEntity.ok(createdTeam);
    }
}
