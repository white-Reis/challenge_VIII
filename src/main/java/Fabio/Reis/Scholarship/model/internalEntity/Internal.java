package Fabio.Reis.Scholarship.model.internalEntity;

import Fabio.Reis.Scholarship.model.teamEntity.Team;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "internals")
public class Internal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String position;

    private String role;

    @ManyToMany(mappedBy = "internals", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Team> teams = new HashSet<>();
}
