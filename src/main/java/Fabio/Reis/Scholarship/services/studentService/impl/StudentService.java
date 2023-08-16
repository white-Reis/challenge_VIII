package Fabio.Reis.Scholarship.services.studentService.impl;

import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {
    ResponseEntity<StudentDTO> create(StudentRequestDTO studentRequest);

    ResponseEntity<Void> delete(Long internalId) ;

    ResponseEntity<Void> update(Long studentId, StudentRequestDTO studentRequestDTO);
    ResponseEntity<StudentDTO> getById(Long studentId) ;
    ResponseEntity<List<StudentDTO>> getAll();


}
