package Fabio.Reis.Scholarship.services.Internal.impl;

import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalRequestDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InternalService {
    ResponseEntity<InternalDTO> create(InternalRequestDTO internalRequest);

    ResponseEntity<Void> delete(Long internalId);
    ResponseEntity<Void> update(Long internalId,InternalRequestDTO internalRequestDTO);

    ResponseEntity<InternalDTO> getById(Long internalId);
    ResponseEntity<List<InternalDTO>> getAll();

}
