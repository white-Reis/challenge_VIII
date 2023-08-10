package Fabio.Reis.Scholarship.controllers;



import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamRequestDTO;
import Fabio.Reis.Scholarship.services.teamService.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/classes")
public class TeamController {
    private TeamService teamService;
    public  TeamController(TeamService teamService){
        this.teamService = teamService;
    }
    @PostMapping()
    public ResponseEntity<Void> createTeamWithStudents(@RequestBody TeamRequestDTO teamRequest) {
        return teamService.createClassWithStudentsAndInternals(teamRequest);
    }
    @GetMapping("{id}")
    public ResponseEntity<TeamDTO> getClassById(@PathVariable Long id){
        return teamService.getClassById(id);
    }
}
