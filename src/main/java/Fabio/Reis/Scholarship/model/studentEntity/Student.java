package Fabio.Reis.Scholarship.model.studentEntity;

import Fabio.Reis.Scholarship.model.teamEntity.Team;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String course;

    private int level;

    @ManyToOne
    @JoinColumn(name = "class_id")
    @JsonBackReference
    private Team team;

}
