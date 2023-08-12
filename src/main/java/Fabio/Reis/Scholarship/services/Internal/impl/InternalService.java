package Fabio.Reis.Scholarship.services.Internal.impl;

import Fabio.Reis.Scholarship.model.internalEntity.Internal;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalRequestDTO;
import Fabio.Reis.Scholarship.repository.InternalRepo;
import Fabio.Reis.Scholarship.services.exceptions.DataIntegratyViolationException;
import Fabio.Reis.Scholarship.services.exceptions.ObjectNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class InternalService implements InternalService_i {

    private final InternalRepo internalRepo;
    private final ModelMapper modelMapper;
    private final Validator validator;

    public InternalService(InternalRepo internalRepo, ModelMapper modelMapper, Validator validator) {
        this.internalRepo = internalRepo;
        this.modelMapper = modelMapper;
        this.validator = validator;

    }

    @Override
    public ResponseEntity<InternalDTO> create(InternalRequestDTO internalRequest) {
        Set<ConstraintViolation<InternalRequestDTO>> violations = validator.validate(internalRequest);
        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<InternalRequestDTO> violation : violations) {
                errorMessages.add(violation.getPropertyPath() + ": " + violation.getMessage());
            }
            throw new DataIntegratyViolationException(errorMessages.toString());
        }
        Optional<Internal> internalOptional = internalRepo.findByEmail(internalRequest.getEmail());
        if (internalOptional.isPresent()) {
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
    public ResponseEntity<Void> update(Long internalId, InternalRequestDTO internalRequest) throws ChangeSetPersister.NotFoundException {
        Set<ConstraintViolation<InternalRequestDTO>> violations = validator.validate(internalRequest);
        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<InternalRequestDTO> violation : violations) {
                errorMessages.add(violation.getPropertyPath() + ": " + violation.getMessage());
            }
            throw new DataIntegratyViolationException(errorMessages.toString());
        }
        Optional<Internal> existinginternalOptional = internalRepo.findById(internalId);
        if (existinginternalOptional.isEmpty()) {
            throw new ObjectNotFoundException("Internal not found");
        }
        Optional<Internal> existingEmailInternalOptional = internalRepo.findByEmail(internalRequest.getEmail());
        if (existingEmailInternalOptional.isEmpty()) {
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
    public ResponseEntity<Void> delete(Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Internal> internalOptional = internalRepo.findById(id);
        if (internalOptional.isPresent()) {
            throw new ObjectNotFoundException("Internal not found");
        }
        internalRepo.delete(internalOptional.get());

        return ResponseEntity.noContent().build();
    }
}

