package Fabio.Reis.Scholarship.model.teamEntity;

import Fabio.Reis.Scholarship.model.internalEntity.Internal;
import Fabio.Reis.Scholarship.model.studentEntity.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private List<Internal> Coordinators;

    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST)
    private List<Internal> scrumMasters;

    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST)
    private List<Internal> Instructors;

    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST)
    private List<Student> students;

}
