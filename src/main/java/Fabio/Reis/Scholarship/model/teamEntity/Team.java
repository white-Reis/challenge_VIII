package Fabio.Reis.Scholarship.model.teamEntity;

import Fabio.Reis.Scholarship.model.internalEntity.Internal;
import Fabio.Reis.Scholarship.model.studentEntity.Student;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "class")

public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String learning;

    private int status;

    private LocalDate startDate;

    private LocalDate endDate;


    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST)
    private List<Internal> Coordinator;

    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST)
    private List<Internal> scrumMaster;

    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST)
    private List<Internal> Instructors;

    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST)
    private List<Student> students;

}
