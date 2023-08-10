package Fabio.Reis.Scholarship.services.studentService.impl.impl;

import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentRequestDTO;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;

public interface StudentService_i {
    ResponseEntity create(StudentRequestDTO studentRequest);

    ResponseEntity<Void> delete(Long internalId) throws ChangeSetPersister.NotFoundException;
}
