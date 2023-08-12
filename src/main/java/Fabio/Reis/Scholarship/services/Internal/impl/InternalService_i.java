package Fabio.Reis.Scholarship.services.Internal.impl;

import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalRequestDTO;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;

public interface InternalService_i {
    ResponseEntity<InternalDTO> create(InternalRequestDTO internalRequest);

    ResponseEntity<Void> delete(Long internalId) throws ChangeSetPersister.NotFoundException;
    ResponseEntity<Void> update(Long internalId,InternalRequestDTO internalRequestDTO) throws ChangeSetPersister.NotFoundException;
}
