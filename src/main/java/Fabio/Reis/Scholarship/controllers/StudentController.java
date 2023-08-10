package Fabio.Reis.Scholarship.controllers;

import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentRequestDTO;
import Fabio.Reis.Scholarship.services.studentService.impl.impl.StudentService_i;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/students")
public class StudentController {

    private StudentService_i studentService;

    public StudentController(StudentService_i studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentRequestDTO studentRequest) {
        ResponseEntity<StudentDTO> newStudent = studentService.create(studentRequest);
        return newStudent;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        ResponseEntity<Void> deletedStudent = studentService.delete(id);
        return deletedStudent;
    }


}

