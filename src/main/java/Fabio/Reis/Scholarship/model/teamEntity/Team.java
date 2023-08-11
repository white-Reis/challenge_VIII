package Fabio.Reis.Scholarship.model.teamEntity;

import Fabio.Reis.Scholarship.model.internalEntity.Internal;
import Fabio.Reis.Scholarship.model.squadEntity.Squad;
import Fabio.Reis.Scholarship.model.studentEntity.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "class_students",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "class_internals",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "internal_id")
    )
    private Set<Internal> internals = new HashSet<>();

    @OneToMany(mappedBy = "team")
    private Set<Squad> squads = new HashSet<>();


}