package Fabio.Reis.Scholarship.controllers;


import Fabio.Reis.Scholarship.services.studentService.impl.StudentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class StudentController {

    private StudentService studentService;
    public  StudentController(StudentService studentService){
        this.studentService= studentService;
    }

}


