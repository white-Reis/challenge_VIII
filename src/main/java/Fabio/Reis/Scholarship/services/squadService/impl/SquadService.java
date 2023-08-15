package Fabio.Reis.Scholarship.services.squadService.impl;

import Fabio.Reis.Scholarship.model.squadEntity.squadDTO.SquadDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SquadService {
    ResponseEntity<SquadDTO> findById(Long id);

    ResponseEntity<List<SquadDTO>> getSquads();
}
