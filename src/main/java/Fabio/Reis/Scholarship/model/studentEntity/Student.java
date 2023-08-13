package Fabio.Reis.Scholarship.model.studentEntity;

import Fabio.Reis.Scholarship.model.squadEntity.Squad;
import Fabio.Reis.Scholarship.model.teamEntity.Team;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;


    private String lastName;

    @Column(unique = true)
    private String email;


    private String course;


    private int level;

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Team> teams = new HashSet<>();

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Squad> squads = new ArrayList<>();

    public void addSquad(Squad squad) {
        this.squads.add(squad);
    }
}
