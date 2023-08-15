package Fabio.Reis.Scholarship.controllers;

import Fabio.Reis.Scholarship.model.squadEntity.squadDTO.SquadDTO;
import Fabio.Reis.Scholarship.services.squadService.impl.SquadServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/squads")
public class SquadController {
    private final SquadServiceImpl squadServiceImpl;

    SquadController(SquadServiceImpl squadServiceImpl) {
        this.squadServiceImpl = squadServiceImpl;
    }

    @GetMapping
    ResponseEntity<List<SquadDTO>> getSquads() {
        return squadServiceImpl.getSquads();
    }

    @GetMapping("{id}")
    ResponseEntity<SquadDTO> getSquadById(@PathVariable Long id) {
        return squadServiceImpl.findById(id);
    }

}
