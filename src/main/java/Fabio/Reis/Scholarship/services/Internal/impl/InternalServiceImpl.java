package Fabio.Reis.Scholarship.services.Internal.impl;

import Fabio.Reis.Scholarship.model.internalEntity.Internal;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalRequestDTO;
import Fabio.Reis.Scholarship.model.studentEntity.Student;
import Fabio.Reis.Scholarship.model.teamEntity.Team;
import Fabio.Reis.Scholarship.repository.InternalRepo;
import Fabio.Reis.Scholarship.exceptions.DataIntegratyViolationException;
import Fabio.Reis.Scholarship.exceptions.ObjectNotFoundException;
import Fabio.Reis.Scholarship.repository.StudentRepo;
import Fabio.Reis.Scholarship.repository.TeamRepo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class InternalServiceImpl implements InternalService {

    private final InternalRepo internalRepo;
    private final TeamRepo teamRepo;
    private final ModelMapper modelMapper;
    private final Validator validator;
    private final StudentRepo studentRepo;

    public InternalServiceImpl(InternalRepo internalRepo, TeamRepo teamRepo, ModelMapper modelMapper, Validator validator,
                               StudentRepo studentRepo) {
        this.internalRepo = internalRepo;
        this.teamRepo = teamRepo;
        this.modelMapper = modelMapper;
        this.validator = validator;

        this.studentRepo = studentRepo;
    }

    @Override
    public ResponseEntity<InternalDTO> create(InternalRequestDTO internalRequest) {
        validInternal(internalRequest, validator);
        Optional<Internal> internalOptional = internalRepo.findByEmail(internalRequest.getEmail());
        Optional<Student> studentOptional = studentRepo.findByEmail(internalRequest.getEmail());
        if (internalOptional.isPresent()||studentOptional.isPresent()) {
            throw new DataIntegratyViolationException("Internal already registered");
        }
        Internal internal = modelMapper.map(internalRequest, Internal.class);
        Internal createdInternal = internalRepo.save(internal);

        InternalDTO responseDto = modelMapper.map(createdInternal, InternalDTO.class);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @Override
    public ResponseEntity<Void> update(Long internalId, InternalRequestDTO internalRequest) {
        validInternal(internalRequest, validator);
        Optional<Internal> existinginternalOptional = internalRepo.findById(internalId);
        if (existinginternalOptional.isEmpty()) {
            throw new ObjectNotFoundException("Internal not found");
        }
        Optional<Internal> existingEmailInternalOptional = internalRepo.findByEmail(internalRequest.getEmail());
        if (existingEmailInternalOptional.isPresent()) {
            throw new ObjectNotFoundException("Internal e-mail already registered");
        }
        Internal internal = existinginternalOptional.get();
        internal.setName(internalRequest.getName());
        internal.setLastName(internalRequest.getLastName());
        internal.setEmail(internalRequest.getEmail());
        internal.setPosition(internalRequest.getPosition());
        internal.setRole(internalRequest.getRole());

        internalRepo.save(internal);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<InternalDTO> getById(Long internalId) {
        Optional<Internal> internalOptional = internalRepo.findById(internalId);
        if (internalOptional.isEmpty()) {
            throw new ObjectNotFoundException("Internal not found");
        }
        InternalDTO internalDTO = modelMapper.map(internalOptional.get(), InternalDTO.class);

        return ResponseEntity.ok(internalDTO);
    }

    @Override
    public ResponseEntity<List<InternalDTO>> getAll() {
        List<Internal> internals = internalRepo.findAll();
        List<InternalDTO> internalDTOs = new ArrayList<>();

        for (Internal internal : internals) {
            InternalDTO internalDTO = modelMapper.map(internal, InternalDTO.class);
            internalDTOs.add(internalDTO);
        }

        return ResponseEntity.ok(internalDTOs);
    }


    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<Internal> internalOptional = internalRepo.findById(id);
        if (!internalOptional.isPresent()) {
            throw new ObjectNotFoundException("Internal not found");
        }
        for (Team team : internalOptional.get().getTeams()) {
            team.getInternals().remove(internalOptional.get());
            teamRepo.save(team);
        }
        internalRepo.delete(internalOptional.get());
        return ResponseEntity.noContent().build();
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
}