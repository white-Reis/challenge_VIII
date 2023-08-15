package Fabio.Reis.Scholarship.services.studentService.impl;

import Fabio.Reis.Scholarship.model.internalEntity.Internal;
import Fabio.Reis.Scholarship.model.squadEntity.Squad;
import Fabio.Reis.Scholarship.model.studentEntity.Student;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentRequestDTO;
import Fabio.Reis.Scholarship.model.teamEntity.Team;
import Fabio.Reis.Scholarship.repository.InternalRepo;
import Fabio.Reis.Scholarship.repository.SquadRepo;
import Fabio.Reis.Scholarship.repository.StudentRepo;
import Fabio.Reis.Scholarship.exceptions.DataIntegratyViolationException;
import Fabio.Reis.Scholarship.exceptions.ObjectNotFoundException;
import Fabio.Reis.Scholarship.repository.TeamRepo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepo studentRepo;
    private final InternalRepo internalRepo;
    private final TeamRepo teamRepo;
    private final SquadRepo squadRepo;
    private final ModelMapper modelMapper;

    private final Validator validator;

    public StudentServiceImpl(StudentRepo studentRepo, InternalRepo internalRepo, TeamRepo teamRepo, SquadRepo squadRepo, ModelMapper modelMapper, Validator validator) {
        this.studentRepo = studentRepo;
        this.internalRepo = internalRepo;
        this.teamRepo = teamRepo;
        this.squadRepo = squadRepo;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public ResponseEntity<StudentDTO> create(StudentRequestDTO studentRequest) {
        validStudent(studentRequest,validator);
        Optional<Student> existingStudentOptional = studentRepo.findByEmail(studentRequest.getEmail());
        Optional<Internal> internalOptional = internalRepo.findByEmail(studentRequest.getEmail());
        if (existingStudentOptional.isPresent()||internalOptional.isPresent()) {
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
    public ResponseEntity<Void> update(Long studentId, StudentRequestDTO studentRequest) {
        validStudent(studentRequest,validator);
        Optional<Student> existingStudentOptional = studentRepo.findById(studentId);
        if (existingStudentOptional.isEmpty()) {
            throw new ObjectNotFoundException("Student not found");
        }
        Optional<Student> existingEmailStudentOptional = studentRepo.findByEmail(studentRequest.getEmail());
        if (existingEmailStudentOptional.isPresent()) {
            throw new DataIntegratyViolationException("Student e-mail already registered");
        }

        Student Student = existingStudentOptional.get();

        Student.setName(studentRequest.getName());
        Student.setLastName(studentRequest.getLastName());
        Student.setEmail(studentRequest.getEmail());
        Student.setCourse(studentRequest.getCourse());
        Student.setLevel(studentRequest.getLevel());

        studentRepo.save(Student);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        Optional<Student> studentOptional = studentRepo.findById(id);
        if (!studentOptional.isPresent()) {
            throw new ObjectNotFoundException("Student not found");
        }

        for (Team team : studentOptional.get().getTeams()) {
            team.getStudents().remove(studentOptional.get());
            teamRepo.save(team);
        }
        for (Squad squad : studentOptional.get().getSquads()) {
            squad.getStudents().remove(studentOptional.get());
            squadRepo.save(squad);
        }
        studentRepo.delete(studentOptional.get());


        return ResponseEntity.noContent().build();
    }

    public static void validStudent(StudentRequestDTO studentRequest, Validator validator) {
        Set<ConstraintViolation<StudentRequestDTO>> violations = validator.validate(studentRequest);
        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<StudentRequestDTO> violation : violations) {
                errorMessages.add(violation.getPropertyPath() + ": " + violation.getMessage());
            }
            throw new DataIntegratyViolationException(errorMessages.toString());
        }
    }
}
