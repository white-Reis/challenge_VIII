package Fabio.Reis.Scholarship.model.internalEntity;

import Fabio.Reis.Scholarship.model.teamEntity.Team;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "internals")
public class Internal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String position;

    private String role;

    @ManyToOne
    @JoinColumn(name = "class_id")
    @JsonBackReference
    private Team team;
}