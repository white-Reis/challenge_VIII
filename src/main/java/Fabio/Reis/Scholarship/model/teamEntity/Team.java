package Fabio.Reis.Scholarship.model.teamEntity;

import Fabio.Reis.Scholarship.model.internalEntity.Internal;
import Fabio.Reis.Scholarship.model.studentEntity.Student;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "teams")
@JsonPropertyOrder({"id", "name", "lastName", "status", "coordinator", "scrumMaster", "instructors", "students"})
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private int status; // 0: waiting, 1: started, 2: finished

    @OneToMany(mappedBy = "team",cascade = CascadeType.PERSIST)
    private List<Internal> Coordinator;

    @OneToMany(mappedBy = "team",cascade = CascadeType.PERSIST)
    private List<Internal> scrumMaster;

    @OneToMany(mappedBy = "team",cascade = CascadeType.PERSIST)
    private List<Internal> Instructors;

    @OneToMany(mappedBy = "team",cascade = CascadeType.PERSIST)
    private List<Student> students;

}
