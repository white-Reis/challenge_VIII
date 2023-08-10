package Fabio.Reis.Scholarship.services.Internal.impl;

import Fabio.Reis.Scholarship.model.internalEntity.Internal;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalRequestDTO;
import Fabio.Reis.Scholarship.repository.InternalRepo;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class InternalService implements InternalService_i {

    private final InternalRepo internalRepo;
    private final ModelMapper modelMapper;

    public InternalService(InternalRepo internalRepo, ModelMapper modelMapper) {
        this.internalRepo = internalRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<InternalDTO> create(InternalRequestDTO internalRequest) {
        Internal internal = modelMapper.map(internalRequest, Internal.class);
        Internal createdInternal = internalRepo.save(internal);

        InternalDTO responseDto = modelMapper.map(createdInternal, InternalDTO.class);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }

    @Override
    public ResponseEntity<Void> delete(Long internalId) throws ChangeSetPersister.NotFoundException {
        Internal internal = internalRepo.findById(internalId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        internalRepo.delete(internal);

        return ResponseEntity.noContent().build();
    }

}
