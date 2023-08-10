package Fabio.Reis.Scholarship.services.studentService.impl.impl;

import Fabio.Reis.Scholarship.model.studentEntity.Student;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentRequestDTO;
import Fabio.Reis.Scholarship.repository.StudentRepo;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class StudentService implements StudentService_i {
    private final StudentRepo studentRepo;
    private final ModelMapper modelMapper;

    public StudentService(StudentRepo studentRepo, ModelMapper modelMapper) {
        this.studentRepo = studentRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<StudentDTO> create(StudentRequestDTO internalRequest) {
        Student student = modelMapper.map(internalRequest, Student.class);
        Student createdStudent = studentRepo.save(student);

        StudentDTO responseDto = modelMapper.map(createdStudent, StudentDTO.class);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);

    }

    @Override
    public ResponseEntity<Void> delete(Long studentId) throws ChangeSetPersister.NotFoundException {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        studentRepo.delete(student);

        return ResponseEntity.noContent().build();
    }
}
