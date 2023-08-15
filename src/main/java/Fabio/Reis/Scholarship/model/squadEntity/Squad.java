package Fabio.Reis.Scholarship.model.squadEntity;

import Fabio.Reis.Scholarship.model.studentEntity.Student;
import Fabio.Reis.Scholarship.model.teamEntity.Team;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "squads")
public class Squad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "squad_students",
            joinColumns = @JoinColumn(name = "squad_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id")
    @JsonBackReference
    private Team team;

}
