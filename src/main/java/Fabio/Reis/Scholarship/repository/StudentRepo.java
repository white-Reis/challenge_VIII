package Fabio.Reis.Scholarship.repository;

import Fabio.Reis.Scholarship.model.studentEntity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
}
