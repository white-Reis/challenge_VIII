package Fabio.Reis.Scholarship.controllers;

import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentRequestDTO;
import Fabio.Reis.Scholarship.services.studentService.impl.StudentService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/students")
public class StudentController {

    private final StudentService studentService;

    StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    ResponseEntity<StudentDTO> createStudent(@RequestBody StudentRequestDTO studentRequest) {
        return studentService.create(studentRequest);

    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteStudent(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return studentService.delete(id);

    }

    @PutMapping("/{id}")
    ResponseEntity<Void> updateStudent(@PathVariable Long id, @RequestBody StudentRequestDTO studentRequestDTO) throws ChangeSetPersister.NotFoundException {
        return studentService.update(id,studentRequestDTO);
    }

}


