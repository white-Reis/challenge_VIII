package Fabio.Reis.Scholarship.services.squadService.impl;

import Fabio.Reis.Scholarship.model.squadEntity.squadDTO.SquadDTO;
import Fabio.Reis.Scholarship.model.squadEntity.Squad;
import Fabio.Reis.Scholarship.repository.SquadRepo;
import Fabio.Reis.Scholarship.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SquadServiceImpl implements SquadService {

    private final SquadRepo squadRepo;
    private final ModelMapper modelMapper;

    SquadServiceImpl(SquadRepo squadRepo, ModelMapper modelMapper) {
        this.squadRepo = squadRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<SquadDTO> findById(Long id) {
        Optional<Squad> squad = squadRepo.findById(id);
        if (squad.isPresent()) {
            SquadDTO squadDTO = modelMapper.map(squad.get(), SquadDTO.class);
            return ResponseEntity.status(HttpStatus.FOUND).body(squadDTO);
        } else {
            throw new ObjectNotFoundException("Squad not found");
        }
    }

    @Override
    public ResponseEntity<List<SquadDTO>> getSquads() {
        List<Squad> squads = squadRepo.findAll();

        List<SquadDTO> squadsDTOs = squads.stream()
                .map(squad -> modelMapper.map(squad, SquadDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(squadsDTOs);
    }

}

