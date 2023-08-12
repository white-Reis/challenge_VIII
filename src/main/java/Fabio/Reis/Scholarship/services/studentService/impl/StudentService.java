package Fabio.Reis.Scholarship.services.studentService.impl;

import Fabio.Reis.Scholarship.model.studentEntity.Student;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentRequestDTO;
import Fabio.Reis.Scholarship.repository.StudentRepo;
import Fabio.Reis.Scholarship.services.exceptions.DataIntegratyViolationException;
import Fabio.Reis.Scholarship.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class StudentService implements StudentService_i {
    private final StudentRepo studentRepo;
    private final ModelMapper modelMapper;

    public StudentService(StudentRepo studentRepo, ModelMapper modelMapper) {
        this.studentRepo = studentRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<StudentDTO> create(StudentRequestDTO studentRequest) {
        Optional<Student> existingStudentOptional = studentRepo.findByEmail(studentRequest.getEmail());
        if (existingStudentOptional.isPresent()) {
            throw new DataIntegratyViolationException("Student e-mail already registered");
        }

        Student student = modelMapper.map(studentRequest, Student.class);
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
    public ResponseEntity<Void> update(Long studentId, StudentRequestDTO studentRequestDTO) {
        Optional<Student> existingStudentOptional = studentRepo.findById(studentId);
        if (existingStudentOptional.isEmpty()) {
            throw new ObjectNotFoundException("Student not found");
        }
        Optional<Student> existingEmailStudentOptional = studentRepo.findByEmail(studentRequestDTO.getEmail());
        if (existingEmailStudentOptional.isPresent()) {
            throw new DataIntegratyViolationException("Student e-mail already registered");
        }

        Student Student = existingStudentOptional.get();

        Student.setName(studentRequestDTO.getName());
        Student.setLastName(studentRequestDTO.getLastName());
        Student.setEmail(studentRequestDTO.getEmail());
        Student.setCourse(studentRequestDTO.getCourse());
        Student.setLevel(studentRequestDTO.getLevel());

        studentRepo.save(Student);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<Student> studentOptional = studentRepo.findById(id);
        if (studentOptional.isPresent()) {
            throw new ObjectNotFoundException("Student not found");
        }


        studentRepo.delete(studentOptional.get());

        return ResponseEntity.noContent().build();
    }
}
